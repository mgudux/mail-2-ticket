package com.mgudux.mail2ticket.services;


import com.mgudux.mail2ticket.domain.dto.EmlFileDto;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;

import java.util.List;
import java.util.UUID;

public interface EmlFileService {
    EmlFileDto.Detail getEmlFile(UUID id);
    EmlFileDto.Summary createEmlFile(ParsedMail parsedMail);
    void deleteEmlFile(UUID id);
    List<EmlFileDto.Summary> listEmlFile();
    EmlFileDto.Detail updateEmlFile(UUID id, EmlFileDto.Update update);
}
