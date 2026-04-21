package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.services.EmlFileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmlFileServiceImpl implements EmlFileService {
    @Override
    public Optional<EmlFile> getEmlFile(UUID id) {
        return Optional.empty();
    }

    @Override
    public EmlFile createEmlFile(EmlFile emlFile) {
        return null;
    }

    @Override
    public void deleteEmlFile(UUID id) {

    }

    @Override
    public List<EmlFile> listEmlFile() {
        return List.of();
    }

    @Override
    public EmlFile updateEmlFile(UUID id, EmlFile emlFile) {
        return null;
    }
}
