package com.mgudux.mail2ticket.services;


import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.domain.entities.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    List<TicketDto.Summary> listTicket();
    TicketDto.Summary createTicket(TicketDto.Request request);
    void deleteTicket(UUID id);

    TicketDto.Detail getTicket(UUID id);
    TicketDto.Detail updateTicket(UUID id, TicketDto.Request request);

}
