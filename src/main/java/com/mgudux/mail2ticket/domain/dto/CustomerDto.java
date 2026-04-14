package com.mgudux.mail2ticket.domain.dto;

import com.mgudux.mail2ticket.domain.entities.ProcessingStatus;

import java.util.List;
import java.util.UUID;



public class CustomerDto {

    public record Summary(
            UUID id,
            String firstName,
            String lastName,
            ProcessingStatus userEmail
    ) {}

    public record Detail(
            UUID id,
            String firstName,
            String lastName,
            String userEmail,
            List<EmailDto.Summary> emails,
            Double emailProgress,
            // total emails minus emails where ProcessingStatus is MANUAL_CHECK_REQUIRED
            List<TicketDto.Summary> tickets,
            Double ticketProgress
            // total tickets minus tickets where ticketStatus is OPEN
    ) {}
}
