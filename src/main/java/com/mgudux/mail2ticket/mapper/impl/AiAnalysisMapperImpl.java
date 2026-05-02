package com.mgudux.mail2ticket.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgudux.mail2ticket.domain.entities.Department;
import com.mgudux.mail2ticket.domain.entities.Sentiment;
import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;
import com.mgudux.mail2ticket.domain.internal.AttachmentData;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.exception.ConflictException;
import com.mgudux.mail2ticket.mapper.AiAnalysisMapper;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.content.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AiAnalysisMapperImpl implements AiAnalysisMapper {

    private final ObjectMapper objectMapper;

    public AiAnalysisMapperImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public SystemMessage buildSystemMessage() {
        return new SystemMessage(buildPrompt());
    }

    @Override
    public UserMessage buildMultimodalRequest(ParsedMail parsedMail) {
        String text = buildText(parsedMail);
        List<Media> media = buildAttachments(parsedMail.attachments());

        return UserMessage.builder()
                .text(text)
                .media(media)
                .build();
    }

    @Override
    public AiEmlAnalysis parseAiResponse(String jsonResponse) {
        try {
            String cleanedJson = jsonResponse
                    .replace("```json\n", "")
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(cleanedJson, AiEmlAnalysis.class);
        } catch (JsonProcessingException e) {
            throw new ConflictException("Failed to map AI JSON output to AiEmlAnalysis record. Raw output: " + jsonResponse, e);
        }
    }


    private String buildPrompt() {
        String allowedDepartments = Arrays.stream(Department.values())
                .map(Department::name)
                .collect(Collectors.joining(", ", "[", "]"));

        String allowedSentiments = Arrays.stream(Sentiment.values())
                .map(sentiment -> String.format("%s %s", sentiment.name(), sentiment.getKeywords()))
                .collect(Collectors.joining(", ", "[", "]"));

        return String.format("""
            You are an automated customer support email analyzer.
            Your task is to analyze the provided parsed EML email data and extract specific information to populate a support ticket system.

            You must respond ONLY with a valid JSON object. Do not include any explanations, introductory text, or markdown code blocks.

            The JSON object must contain exactly the following keys with the specified data types:

            - "extractedFirstName": (string) The sender's first name, inferred from the senderName or senderEmail fields.
            - "extractedLastName": (string) The sender's last name, inferred from the senderName or senderEmail fields.
            - "extractedTicketTitle": (string) A concise 5-10 word title summarizing the core issue based on the subject and body.
            - "extractedAiSummary": (string) A comprehensive summary paragraph of the email's content and the customer's request.
            - "extractedDepartment": (string) The department best suited to handle the request. MUST be exactly one of: %s.
            - "extractedSentiment": (string) The emotional tone of the email. Choose the sentiment name from this list (keywords are provided in brackets to guide your choice): %s.
            - "hasUnanalyzedContent": (boolean) Set to true if the email partially contains content (e.g., complex attachments, unsupported languages, or garbled text) that cannot be fully analyzed. Otherwise, set to false.
            
            Example JSON response:
            {
              "extractedFirstName": "Jane",
              "extractedLastName": "Doe",
              "extractedTicketTitle": "Unable to access billing portal",
              "extractedAiSummary": "The customer is reporting an issue accessing the billing portal after resetting their password. They need immediate assistance to download their monthly invoice.",
              "extractedDepartment": "ACCOUNTING",
              "extractedSentiment": "FRUSTRATED",
              "hasUnanalyzedContent": false
            }
            """, allowedDepartments, allowedSentiments);
    }

    private String buildText(ParsedMail parsedMail) {
        return String.format("""
        Beginning of Email Metadata
        Sent Time: %s
        Sender: %s <%s>
        Receivers: %s
        CC: %s
        Subject: %s
        Attached File Names: %s
        End of Email Metadata
    
        Beginning of Email Body
        %s
        End of Email Body
        """,
                parsedMail.sentTime(),
                parsedMail.senderName() != null ? parsedMail.senderName() : "Unknown",
                parsedMail.senderEmail(),
                String.join(", ", parsedMail.receivers()),
                String.join(", ", parsedMail.carbonCopies()),
                parsedMail.subject(),
                parsedMail.attachmentNames(),
                parsedMail.body()
        );
    }

    private List<Media> buildAttachments(List<AttachmentData> attachments) {
        if (attachments == null) return List.of();

        return attachments.stream()
                .map(a -> new Media(
                        MimeTypeUtils.parseMimeType(a.mimeType()),
                        new ByteArrayResource(a.data())
                ))
                .toList();
    }
}
