package com.mgudux.mail2ticket.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules();

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
            You are an automated customer support email analyzer for a business support ticket system.
            Analyze the provided email and extract structured information.
        
            You must respond ONLY with a valid JSON object. No explanations, no markdown, no code blocks.
        
            IMPORTANT: Always make your best judgment. UNKNOWN or fallback values are a LAST RESORT only when
            the information is completely impossible to determine. Never default to them out of uncertainty — commit to the most fitting option.
        
            FIELD INSTRUCTIONS:
        
            "extractedFirstName": Infer from senderName first, then senderEmail. Only "Unknown" if truly undetectable.
            "extractedLastName": Same rules as firstName.
            "extractedTicketTitle": A specific, concise 5-10 word title capturing the exact issue. Never generic.
            "extractedAiSummary": A clear paragraph summarizing the problem and what the customer is requesting.
            "extractedDepartment": MUST be exactly one of: %s. Use UNKNOWN only if the email is completely nonsensical.
            "extractedSentiment": MUST be exactly one of: %s. Pick the closest match — never leave ambiguous.
            "hasUnanalyzedContent": true ONLY if attachments are unreadable, text is garbled, or language is unsupported. Otherwise false.
        
            Example:
            {
              "extractedFirstName": "Jane",
              "extractedLastName": "Doe",
              "extractedTicketTitle": "Unable to access billing portal after password reset",
              "extractedAiSummary": "The customer reports being locked out of the billing portal after resetting their password. They urgently need to download their monthly invoice and request immediate assistance.",
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
