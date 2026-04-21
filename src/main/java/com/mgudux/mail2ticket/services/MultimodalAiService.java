package com.mgudux.mail2ticket.services;

import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;

public interface MultimodalAiService {
    AiEmlAnalysis analyze(ParsedMail parsedMail);
}
