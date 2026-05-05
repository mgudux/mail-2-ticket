package com.mgudux.mail2ticket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.attachments")
public record AttachmentProperties(List<String> allowedMimeTypes) {
}
