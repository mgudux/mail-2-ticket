package com.mgudux.mail2ticket.services;

import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.domain.internal.UploadResponse;

public interface UploadService {
    UploadResponse upload(ParsedMail parsedMail);
}
