package com.mgudux.mail2ticket.repositories;

import com.mgudux.mail2ticket.domain.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    // unique sequence of numbers incrementing upwards as identifier for tickets
    @Query(value = "SELECT NEXTVAL('ticket-seq')", nativeQuery = true)
    Long nextTicketNumber();

    List<Ticket> findByCustomerId(UUID customerId);
}
