package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.internal.UploadResponse;
import com.mgudux.mail2ticket.services.EmailProcessingService;
import org.springframework.web.multipart.MultipartFile;

// Orchestrates services to create all three entities and return their IDs
public class EmailProcessingServiceImpl implements EmailProcessingService {
    @Override
    public UploadResponse process(MultipartFile file) {
        return null;
    }
}
