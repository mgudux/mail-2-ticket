package com.mgudux.mail2ticket.mapper;

import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.domain.entities.Ticket;
import com.mgudux.mail2ticket.integration.ai.dto.AiEmlAnalysis;

public interface TicketMapper {

    TicketDto.Summary toSummary(Ticket ticket);
    TicketDto.Detail toDetail(Ticket ticket);
    Ticket fromAiAnalysis(AiEmlAnalysis aiEmlAnalysis);
}
