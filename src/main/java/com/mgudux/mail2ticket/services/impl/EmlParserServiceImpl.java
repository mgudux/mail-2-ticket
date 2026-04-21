package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.services.EmlParserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmlParserServiceImpl implements EmlParserService {
    @Override
    public ParsedMail parse(MultipartFile file) {
        return null;
    }
}
