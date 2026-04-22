package com.mgudux.mail2ticket.domain.internal;

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
        List<AttachmentData> attachments,
        LocalDateTime sentTime
) {}