package com.mgudux.mail2ticket.controller;

import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/tickets")
public class TicketController {


    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketDto.Summary> listTickets() {
        return ticketService.listTickets();
    }

    // GET one Ticket with details
    @GetMapping(path = "/{id}")
    public TicketDto.Detail getTicket(@PathVariable UUID id) {
        return ticketService.getTicket(id);
    }

    @PostMapping
    public TicketDto.Detail createTicket(@RequestBody @Valid TicketDto.Request request) {
        return ticketService.createTicket(request);
    }

    @PutMapping(path = "/{id}")
    public TicketDto.Detail updateTicket(
            @PathVariable UUID id,
            @Valid @RequestBody TicketDto.Request request
    ) {
        return ticketService.updateTicket(id, request);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
