package com.mgudux.mail2ticket.domain.internal;

public record AttachmentData(
        String filename,
        byte[] data,
        String mimeType
) {

}
