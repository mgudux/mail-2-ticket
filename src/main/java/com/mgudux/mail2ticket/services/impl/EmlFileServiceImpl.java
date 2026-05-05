package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.dto.EmlFileDto;
import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.domain.entities.ProcessingStatus;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.exception.ResourceNotFoundException;
import com.mgudux.mail2ticket.exception.ValidationException;
import com.mgudux.mail2ticket.mapper.EmlFileMapper;
import com.mgudux.mail2ticket.repositories.EmlFileRepository;
import com.mgudux.mail2ticket.services.EmlFileService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmlFileServiceImpl implements EmlFileService {

    private final EmlFileMapper emlFileMapper;
    private final EmlFileRepository emlFileRepository;

    public EmlFileServiceImpl(EmlFileMapper emlFileMapper, EmlFileRepository emlFileRepository) {
        this.emlFileMapper = emlFileMapper;
        this.emlFileRepository = emlFileRepository;
    }

    @Override
    public EmlFileDto.Detail getEmlFile(UUID id) {
        if (id == null) {
            throw new ValidationException("The ID cannot be null.");
        }
        EmlFile emlFile = emlFileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No EML File with this ID exists")
        );

        return emlFileMapper.toDetail(emlFile);
    }

    @Override
    public EmlFile createEmlFile(ParsedMail parsedMail) {
        EmlFile emlFile = buildEmlFile(parsedMail);
        return emlFileRepository.save(emlFile);
    }

    private EmlFile buildEmlFile(ParsedMail mail) {
        EmlFile emlFile = new EmlFile();
        emlFile.setMessageId(mail.messageId());
        emlFile.setSenderEmail(mail.senderEmail());
        emlFile.setSenderName(mail.senderName());
        emlFile.setReceiverEmail(mail.receivers());
        emlFile.setCarbonCopy(mail.carbonCopies());
        emlFile.setSubject(mail.subject());
        emlFile.setBody(mail.body());
        emlFile.setAttachmentNames(mail.attachmentNames());
        emlFile.setRawEmailKey("No Key");
        emlFile.setSent(mail.sentTime());
        return emlFile;
    }

    @Override
    public void deleteEmlFile(UUID id) {
        emlFileRepository.deleteById(id);
    }

    @Override
    public List<EmlFileDto.Summary> listEmlFile() {
        return emlFileRepository.findAll().stream().map(emlFileMapper::toSummary).toList();
    }

    @Transactional
    @Override
    public EmlFileDto.Detail updateEmlFile(UUID id, EmlFileDto.Update update) {
        EmlFile existingEmlFile = emlFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Given ID does not exist!"));
        existingEmlFile.setProcessingStatus(ProcessingStatus.fromString(update.processingStatus()));
        existingEmlFile.setErrorMessage(update.errorMessage());
        return emlFileMapper.toDetail(emlFileRepository.save(existingEmlFile));
    }

    @Override
    public void updateProcessingStatus(EmlFile emlFile, ProcessingStatus status) {
        emlFile.setProcessingStatus(status);
        emlFileRepository.save(emlFile);
    }

}
