package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.entities.Ticket;
import com.mgudux.mail2ticket.services.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public List<Ticket> listTicket() {
        return List.of();
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        return null;
    }

    @Override
    public void deleteTicket(UUID id) {

    }

    @Override
    public Optional<Ticket> getTicket(UUID id) {
        return Optional.empty();
    }

    @Override
    public Ticket updateTicket(UUID id, Ticket ticket) {
        return null;
    }
}
