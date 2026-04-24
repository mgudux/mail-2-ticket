package com.mgudux.mail2ticket.mapper;

import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;


public interface AiAnalysisMapper {


    SystemMessage buildSystemMessage();
    UserMessage buildMultimodalRequest(ParsedMail parsedMail);
    AiEmlAnalysis parseAiResponse(String jsonResponse);

}
