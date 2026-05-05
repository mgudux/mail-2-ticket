package com.mgudux.mail2ticket.services;


import com.mgudux.mail2ticket.domain.dto.EmlFileDto;
import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.domain.entities.ProcessingStatus;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;

import java.util.List;
import java.util.UUID;

public interface EmlFileService {
    EmlFileDto.Detail getEmlFile(UUID id);

    // No DTO because this is a internal method, it is only used by the pipeline
    EmlFile createEmlFile(ParsedMail parsedMail);
    void deleteEmlFile(UUID id);
    List<EmlFileDto.Summary> listEmlFile();
    EmlFileDto.Detail updateEmlFile(UUID id, EmlFileDto.Update update);
    void updateProcessingStatus(EmlFile emlFile, ProcessingStatus status);
}
