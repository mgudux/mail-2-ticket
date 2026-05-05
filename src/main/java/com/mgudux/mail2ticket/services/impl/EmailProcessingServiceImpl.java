package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.dto.CustomerDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.domain.entities.Ticket;
import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.domain.internal.UploadResponse;
import com.mgudux.mail2ticket.services.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// Orchestrates services to create all three entities and return their IDs
@Service
public class EmailProcessingServiceImpl implements EmailProcessingService {

    private final EmlParserService emlParserService;
    private final MultimodalAiService multimodalAiService;
    private final CustomerService customerService;
    private final EmlFileService emlFileService;
    private final TicketService ticketService;

    public EmailProcessingServiceImpl(EmlParserService emlParserService, MultimodalAiService multimodalAiService, CustomerService customerService, EmlFileService emlFileService, TicketService ticketService) {
        this.emlParserService = emlParserService;
        this.multimodalAiService = multimodalAiService;
        this.customerService = customerService;
        this.emlFileService = emlFileService;
        this.ticketService = ticketService;
    }


    @Override
    @Transactional
    public UploadResponse process(MultipartFile file) {

        ParsedMail parsedMail = emlParserService.parse(file);

        AiEmlAnalysis aiEmlAnalysis = multimodalAiService.analyze(parsedMail);

        Customer customer = customerService.findOrCreateByEmail(
                new CustomerDto.Request(
                        aiEmlAnalysis.extractedFirstName(),
                        aiEmlAnalysis.extractedLastName(),
                        parsedMail.senderEmail()
                )
        );

        EmlFile emlFile = emlFileService.createEmlFile(parsedMail);

        Ticket ticket = ticketService.createTicketPipeline(aiEmlAnalysis, customer, emlFile);
        emlFileService.updateProcessingStatus(emlFile, ticket.getProcessingStatus());

        return new UploadResponse(
                customer.getId(),
                emlFile.getId(),
                ticket.getId()
        );
    }
}
