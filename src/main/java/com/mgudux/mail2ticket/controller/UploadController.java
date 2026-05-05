package com.mgudux.mail2ticket.controller;


import com.mgudux.mail2ticket.domain.internal.UploadResponse;
import com.mgudux.mail2ticket.services.EmailProcessingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final EmailProcessingService emailProcessingService;

    public UploadController(EmailProcessingService emailProcessingService) {
        this.emailProcessingService = emailProcessingService;
    }

    @PostMapping
    public UploadResponse process(@RequestParam MultipartFile file) {
        return emailProcessingService.process(file);
    }
}
