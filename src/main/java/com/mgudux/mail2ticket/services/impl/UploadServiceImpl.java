package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.domain.internal.UploadResponse;
import com.mgudux.mail2ticket.services.UploadService;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public UploadResponse upload(ParsedMail parsedMail) {
        return null;
    }
}
