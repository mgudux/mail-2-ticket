package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.services.MultimodalAiService;
import org.springframework.stereotype.Service;

@Service
public class MultimodalAiServiceImpl implements MultimodalAiService {
    @Override
    public AiEmlAnalysis analyze(ParsedMail parsedMail) {
        return null;
    }
}
