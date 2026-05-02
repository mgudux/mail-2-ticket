package com.mgudux.mail2ticket.services;

import com.mgudux.mail2ticket.domain.internal.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface EmailProcessingService {
    UploadResponse process(MultipartFile file);
}
