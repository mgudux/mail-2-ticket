package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.domain.entities.ProcessingStatus;
import com.mgudux.mail2ticket.domain.entities.Ticket;
import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;
import com.mgudux.mail2ticket.exception.ResourceNotFoundException;
import com.mgudux.mail2ticket.exception.ValidationException;
import com.mgudux.mail2ticket.mapper.TicketMapper;
import com.mgudux.mail2ticket.repositories.TicketRepository;
import com.mgudux.mail2ticket.services.TicketService;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {


    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private static final String ID_NOT_FOUND = "No Ticket with this ID exists";

    public TicketServiceImpl(TicketMapper ticketMapper, TicketRepository ticketRepository) {
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<TicketDto.Summary> listTicket() {
        return ticketRepository.findAll().stream().map(ticketMapper::toSummary).toList();
    }

    @Override
    public TicketDto.Summary createTicket(TicketDto.Request request) {

        Ticket ticket = Ticket.builder()
                .ticketTitle(request.ticketTitle())
                .aiSummary(request.aiSummary())
                .ticketStatus(request.ticketStatus())
                .department(request.department())
                .sentiment(request.sentiment())
                .build();
        return ticketMapper.toSummary(ticketRepository.save(ticket));
    }

    @Override
    public TicketDto.Summary createTicketPipeline(AiEmlAnalysis aiEmlAnalysis, Customer customer, EmlFile emlFile) {

        Ticket ticket = Ticket.builder()
                .ticketTitle(aiEmlAnalysis.extractedTicketTitle())
                .aiSummary(aiEmlAnalysis.extractedAiSummary())
                .department(aiEmlAnalysis.extractedDepartment())
                .sentiment(aiEmlAnalysis.extractedSentiment())
                .processingStatus(aiEmlAnalysis.hasUnanalyzedContent() ?
                        ProcessingStatus.PARTIAL_SUCCESS : ProcessingStatus.SUCCESS)
                .customer(customer)
                .email(emlFile)
                .build();

        return ticketMapper.toSummary(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicket(UUID id) {
        if (id == null) {
            throw new ValidationException("ID must not be null");
        }
        Ticket ticket = ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(ID_NOT_FOUND));
        ticketRepository.delete(ticket);
    }

    @Override
    public TicketDto.Detail getTicket(UUID id) {
        if (id == null) {
            throw new ValidationException("ID must not be null");
        }
        return ticketRepository.findById(id).map(ticketMapper::toDetail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ID_NOT_FOUND));

    }

    @Override
    public TicketDto.Detail updateTicket(UUID id, TicketDto.Request request) {
        if (id == null) {
            throw new ValidationException("ID must not be null");
        }
        Ticket oldTicket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ID_NOT_FOUND));

        oldTicket.setTicketTitle(request.ticketTitle());
        oldTicket.setAiSummary(request.aiSummary());
        oldTicket.setTicketStatus(request.ticketStatus());
        oldTicket.setDepartment(request.department());
        oldTicket.setSentiment(request.sentiment());

        return ticketMapper.toDetail(ticketRepository.save(oldTicket));
    }
}
