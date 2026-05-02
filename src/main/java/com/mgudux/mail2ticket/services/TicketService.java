package com.mgudux.mail2ticket.services;


import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.domain.entities.Ticket;
import com.mgudux.mail2ticket.domain.internal.AiEmlAnalysis;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    List<TicketDto.Summary> listTicket();
    TicketDto.Summary createTicket(TicketDto.Request request);
    // No DTO as parameter because Pipeline is only used for internal objects between services
    TicketDto.Summary createTicketPipeline(AiEmlAnalysis aiEmlAnalysis, Customer customer, EmlFile emlFile);
    void deleteTicket(UUID id);

    TicketDto.Detail getTicket(UUID id);
    TicketDto.Detail updateTicket(UUID id, TicketDto.Request request);

}
