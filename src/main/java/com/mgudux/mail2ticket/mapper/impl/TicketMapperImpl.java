package com.mgudux.mail2ticket.mapper.impl;

import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.domain.entities.Ticket;
import com.mgudux.mail2ticket.integration.ai.dto.AiEmlAnalysis;
import com.mgudux.mail2ticket.mapper.TicketMapper;
import org.springframework.stereotype.Component;

@Component
public class TicketMapperImpl implements TicketMapper {

    @Override
    public TicketDto.Summary toSummary(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return new TicketDto.Summary(
                ticket.getTicketNumber(),
                ticket.getDepartment(),
                ticket.getTicketStatus(),
                ticket.getProcessingStatus()
        );
    }

    @Override
    public TicketDto.Detail toDetail(Ticket ticket) {

        if (ticket == null) {
            return null;
        }
        Customer customer = ticket.getCustomer();
        EmlFile emlFile = ticket.getEmail();
        return new TicketDto.Detail(
                ticket.getId(),
                ticket.getTicketNumber(),
                ticket.getAiSummary(),
                ticket.getSentiment(),
                ticket.getDepartment(),
                ticket.getTicketStatus(),
                ticket.getProcessingStatus(),
                ticket.getErrorMessage(),
                customer != null ? customer.getId() : null,
                customer != null ? customer.getFirstName() + " " + customer.getLastName() : "Unknown",
                emlFile != null ? emlFile.getId() : null,
                emlFile != null ? emlFile.getSubject() : "No Subject"
        );
    }

    @Override
    public Ticket fromAiAnalysis(AiEmlAnalysis aiEmlAnalysis) {
        if (aiEmlAnalysis == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setAiSummary(aiEmlAnalysis.extractedAiSummary());
        ticket.setDepartment(aiEmlAnalysis.extractedDepartment());
        ticket.setSentiment(aiEmlAnalysis.extractedSentiment());
        return ticket;
    }
}
