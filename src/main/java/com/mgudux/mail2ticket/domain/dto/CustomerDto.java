package com.mgudux.mail2ticket.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;



public class CustomerDto {

    public record Request(
            @NotBlank(message = "First name is required") String firstName,
            @NotBlank(message = "Last name is required") String lastName,
            @NotBlank(message = "Email is required") @Email String userEmail
    ) {}

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
