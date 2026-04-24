package com.mgudux.mail2ticket.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

public record ParsedMail(
        String messageId,
        String subject,
        String body,
        String senderEmail,
        String senderName,
        List<String> receivers,
        List<String> carbonCopies,
        List<String> attachmentNames,
        @JsonIgnore List<AttachmentData> attachments,
        LocalDateTime sentTime
) {}