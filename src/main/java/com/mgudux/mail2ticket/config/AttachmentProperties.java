package com.mgudux.mail2ticket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.attachments")
public record AttachmentProperties(List<String> allowedMimeTypes) {
}
