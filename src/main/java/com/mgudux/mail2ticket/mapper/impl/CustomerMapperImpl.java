package com.mgudux.mail2ticket.mapper.impl;


import com.mgudux.mail2ticket.domain.dto.CustomerDto;
import com.mgudux.mail2ticket.domain.entities.*;
import com.mgudux.mail2ticket.integration.ai.dto.AiEmlAnalysis;
import com.mgudux.mail2ticket.mapper.CustomerMapper;
import com.mgudux.mail2ticket.mapper.EmlFileMapper;
import com.mgudux.mail2ticket.mapper.TicketMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapperImpl implements CustomerMapper {

    private final EmlFileMapper emlFileMapper;
    private final TicketMapper ticketMapper;

    public CustomerMapperImpl(EmlFileMapper emlFileMapper, TicketMapper ticketMapper) {
        this.emlFileMapper = emlFileMapper;
        this.ticketMapper = ticketMapper;
    }


    @Override
    public CustomerDto.Summary toSummary(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDto.Summary(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getUserEmail()
        );
    }
    @Override
    public CustomerDto.Detail toDetail(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerDto.Detail(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getUserEmail(),
                customer.getEmails().stream().map(emlFileMapper::toSummary).toList(),
                calculateEmailProgress(customer.getEmails()),
                customer.getTickets().stream().map(ticketMapper::toSummary).toList(),
                calculateTicketProgress(customer.getTickets())
        );
    }

    @Override
    public Customer fromAiAnalysis(AiEmlAnalysis aiEmlAnalysis) {
        if (aiEmlAnalysis == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setFirstName(aiEmlAnalysis.extractedFirstName());
        customer.setLastName(aiEmlAnalysis.extractedLastName());
        return customer;
    }

    public Double calculateEmailProgress(List<EmlFile> customerEmlFiles) {
        if (customerEmlFiles == null || customerEmlFiles.isEmpty()) {
            return (double)0;
        }
        long completedEmlFiles = customerEmlFiles.stream()
                .filter(emlFile ->
                        emlFile.getProcessingStatus().isProcessed())
                .count();
        return (double)completedEmlFiles / customerEmlFiles.size();
    }

    public Double calculateTicketProgress(List<Ticket> customerTickets) {
        if (customerTickets == null || customerTickets.isEmpty()) {
            return (double)0;
        }
        long completedTickets = customerTickets.stream().
                filter(ticket -> (ticket.getTicketStatus().isCompleted()))
                        .count();
        return (double)completedTickets / customerTickets.size();
    }
}
