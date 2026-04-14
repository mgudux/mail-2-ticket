package com.mgudux.mail2ticket.domain.dto;

import java.util.List;
import java.util.UUID;



public class CustomerDto {

    public record Summary(
            UUID id,
            String firstName,
            String lastName,
            String userEmail
    ) {}

    public record Detail(
            UUID id,
            String firstName,
            String lastName,
            String userEmail,
            List<EmlFileDto.Summary> emails,
            Double emailProgress,
            // total emails minus emails where ProcessingStatus is MANUAL_CHECK_REQUIRED
            List<TicketDto.Summary> tickets,
            Double ticketProgress
            // total tickets minus tickets where ticketStatus is OPEN
    ) {}
}
