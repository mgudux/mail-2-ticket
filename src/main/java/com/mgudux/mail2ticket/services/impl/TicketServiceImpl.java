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
import com.mgudux.mail2ticket.repositories.CustomerRepository;
import com.mgudux.mail2ticket.repositories.TicketRepository;
import com.mgudux.mail2ticket.services.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {


    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private static final String ID_NOT_FOUND = "No Ticket with this ID exists";

    public TicketServiceImpl(TicketMapper ticketMapper, TicketRepository ticketRepository, CustomerRepository customerRepository) {
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<TicketDto.Summary> listTickets() {
        return ticketRepository.findAll().stream().map(ticketMapper::toSummary).toList();
    }

    @Override
    public List<TicketDto.Summary> listCustomerTickets(UUID customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        return ticketRepository.findByCustomerId(customerId)
                .stream()
                .map(ticketMapper::toSummary)
                .toList();
    }

    @Override
    public TicketDto.Detail createTicket(TicketDto.Request request) {

        Ticket ticket = Ticket.builder()
                .ticketTitle(request.ticketTitle())
                .aiSummary(request.aiSummary())
                .ticketStatus(request.ticketStatus())
                .ticketNumber("TKT-" + System.currentTimeMillis())
                .department(request.department())
                .sentiment(request.sentiment())
                .build();
        return ticketMapper.toDetail(ticketRepository.save(ticket));
    }

    @Override
    public Ticket createTicketPipeline(AiEmlAnalysis aiEmlAnalysis, Customer customer, EmlFile emlFile) {

        Ticket ticket = ticketMapper.fromAiAnalysis(aiEmlAnalysis);
        ticket.setCustomer(customer);
        ticket.setEmail(emlFile);
        ticket.setTicketNumber("TKT-" + System.currentTimeMillis());
        ticket.setProcessingStatus(aiEmlAnalysis.hasUnanalyzedContent() ?
                ProcessingStatus.PARTIAL_SUCCESS : ProcessingStatus.SUCCESS);

        return ticketRepository.save(ticket);
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

    @Transactional
    @Override
    public TicketDto.Detail updateTicket(UUID id, TicketDto.Request request) {
        if (id == null) {
            throw new ValidationException("ID must not be null");
        }
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ID_NOT_FOUND));

        ticketMapper.updateTicketFromRequest(request, existingTicket);

        return ticketMapper.toDetail(ticketRepository.save(existingTicket));
    }
}
