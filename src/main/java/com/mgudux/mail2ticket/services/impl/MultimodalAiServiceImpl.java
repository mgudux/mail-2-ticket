package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.mapper.AiAnalysisMapper;
import com.mgudux.mail2ticket.services.MultimodalAiService;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatModel;

import java.util.List;

@Service
public class MultimodalAiServiceImpl implements MultimodalAiService {

    private final AiAnalysisMapper aiAnalysisMapper;
    private final ChatModel chatModel;

    public MultimodalAiServiceImpl(AiAnalysisMapper aiAnalysisMapper, ChatModel chatModel) {
        this.aiAnalysisMapper = aiAnalysisMapper;
        this.chatModel = chatModel;
    }


    @Override
    public AiEmlAnalysis analyze(ParsedMail parsedMail) {
        SystemMessage systemMessage = aiAnalysisMapper.buildSystemMessage();
        UserMessage userMessage = aiAnalysisMapper.buildMultimodalRequest(parsedMail);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatModel.call(prompt);
        String jsonString = response.getResult().getOutput().getText();

        return aiAnalysisMapper.parseAiResponse(jsonString);
    }
}
