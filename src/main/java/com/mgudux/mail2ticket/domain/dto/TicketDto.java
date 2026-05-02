package com.mgudux.mail2ticket.domain.dto;

import com.mgudux.mail2ticket.domain.entities.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TicketDto {

    public record Request(
            @NotBlank(message = "Ticket Title is required") String ticketTitle,
            @NotBlank(message = "Ticket Summary is required") String aiSummary,
            @NotNull(message = "Ticket Status is required") TicketStatus ticketStatus,
            @NotNull(message = "Ticket Department is required") Department department,
            @NotNull(message = "Ticket Sentiment is required") Sentiment sentiment

            ) {}

    public record Summary(
            String ticketTitle,
            String ticketNumber,
            Department department,
            TicketStatus ticketStatus,
            ProcessingStatus processingStatus
    ) {}

    public record Detail(
            UUID id,
            String ticketTitle,
            String ticketNumber,
            String aiSummary,
            Sentiment sentiment,
            Department department,
            TicketStatus ticketStatus,
            ProcessingStatus processingStatus,
            String errorMessage,
            String excelKey,
            UUID customerId,
            String customerName,
            UUID emailId,
            String emailSubject
            ) {}
}
