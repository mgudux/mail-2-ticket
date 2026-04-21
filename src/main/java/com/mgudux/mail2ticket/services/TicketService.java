package com.mgudux.mail2ticket.services;


import com.mgudux.mail2ticket.domain.entities.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    List<Ticket> listTicket();
    Ticket createTicket(Ticket ticket);
    void deleteTicket(UUID id);

    Optional<Ticket> getTicket(UUID id);
    Ticket updateTicket(UUID id, Ticket ticket);

}
