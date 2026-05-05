package com.mgudux.mail2ticket.controller;

import com.mgudux.mail2ticket.domain.dto.CustomerDto;
import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.services.CustomerService;
import com.mgudux.mail2ticket.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final TicketService ticketService;
    private final CustomerService customerService;

    public CustomerController(TicketService ticketService, CustomerService customerService) {
        this.ticketService = ticketService;
        this.customerService = customerService;
    }

    // List tickets of one customer
    @GetMapping(path = "/{customerId}/tickets")
    public List<TicketDto.Summary> listCustomerTickets(@PathVariable UUID customerId) {
        return ticketService.listCustomerTickets(customerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<CustomerDto.Summary> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(path = "/{id}")
    public CustomerDto.Detail getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping(path = "/{id}")
    public CustomerDto.Detail updateCustomer(@PathVariable UUID id, @Valid @RequestBody CustomerDto.Request request) {
        return customerService.updateCustomer(id, request);
    }
}
