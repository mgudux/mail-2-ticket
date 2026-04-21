package com.mgudux.mail2ticket.services;

import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import org.springframework.web.multipart.MultipartFile;

public interface EmlParserService {
    ParsedMail parse(MultipartFile file);
}
