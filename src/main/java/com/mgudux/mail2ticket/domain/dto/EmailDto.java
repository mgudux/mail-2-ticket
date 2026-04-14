package com.mgudux.mail2ticket.domain.dto;

import com.mgudux.mail2ticket.domain.dto.CustomerDto;
import com.mgudux.mail2ticket.domain.dto.TicketDto;
import com.mgudux.mail2ticket.domain.entities.ProcessingStatus;

import java.util.List;
import java.util.UUID;

public class EmailDto {

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
            // we give the raw .EML in case the support teams wants to verify source
            String rawEmailS3Key,
            List<String> attachmentS3Keys,
            ProcessingStatus processingStatus,
            // for easier manual check, perhaps it was an email directed to someone else?
            String errorMessage,
            String uploadBatchId,
            TicketDto.Summary ticket,
            CustomerDto.Summary customer
    ) {}
}
