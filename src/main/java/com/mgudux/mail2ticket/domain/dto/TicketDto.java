package com.mgudux.mail2ticket.domain.dto;

import com.mgudux.mail2ticket.domain.entities.*;

import java.util.UUID;

public class TicketDto {

    public record Summary(
            String ticketNumber,
            Department department,
            TicketStatus ticketStatus,
            ProcessingStatus processingStatus
    ) {}

    public record Detail(
            UUID id,
            String ticketNumber,
            String aiSummary,
            Sentiment sentiment,
            Department department,
            TicketStatus ticketStatus,
            ProcessingStatus processingStatus,
            String errorMessage,
            UUID customerId,
            String customerName,
            UUID emailId,
            String emailSubject
            ) {}
}
