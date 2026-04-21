package com.mgudux.mail2ticket.domain.internal;

import java.util.List;

public record ParsedMail(
        String subject,
        String body,
        String senderEmail,
        List<String> receiverEmail,
        List<String> cc,
        byte[] rawBytes,
        List<AttachmentData> attachments
) {
}
