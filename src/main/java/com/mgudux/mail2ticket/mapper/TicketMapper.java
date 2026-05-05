package com.mgudux.mail2ticket.mapper;

import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.domain.entities.Ticket;
import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;

public interface TicketMapper {

    TicketDto.Summary toSummary(Ticket ticket);
    TicketDto.Detail toDetail(Ticket ticket);
    Ticket fromAiAnalysis(AiEmlAnalysis aiEmlAnalysis);
    void updateTicketFromRequest(TicketDto.Request request, Ticket existingTicket);
}
