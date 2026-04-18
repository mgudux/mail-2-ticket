package com.mgudux.mail2ticket.domain.dto;

import com.mgudux.mail2ticket.domain.entities.ProcessingStatus;

import java.util.UUID;

public class EmlFileDto {

    public record Summary(
            UUID id,
            String subject,
            String senderEmail,
            ProcessingStatus processingStatus
    ) {}

    public record Detail(
            UUID id,
            String subject,
            String body,
            String senderEmail,
            String receiverEmail,
            String rawEmailKey,
            ProcessingStatus processingStatus,
            String errorMessage,
            String uploadBatchId,
            TicketDto.Summary ticket,
            UUID customerId,
            String customerName
    ) {}
}
