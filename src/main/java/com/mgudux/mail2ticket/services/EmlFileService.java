package com.mgudux.mail2ticket.services;


import com.mgudux.mail2ticket.domain.entities.EmlFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmlFileService {
    Optional<EmlFile> getEmlFile(UUID id);
    EmlFile createEmlFile(EmlFile emlFile);
    void deleteEmlFile(UUID id);
    List<EmlFile> listEmlFile();
    EmlFile updateEmlFile(UUID id, EmlFile emlFile);
}
