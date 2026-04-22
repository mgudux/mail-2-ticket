package com.mgudux.mail2ticket.domain.dto;

import com.mgudux.mail2ticket.domain.entities.ProcessingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EmlFileDto {

    public record Request(
            @NotNull(message = "File upload is required") MultipartFile file
    ) {}

    public record Update(
            @NotBlank(message = "Please enter a valid Proccessing Status") String processingStatus,
            String errorMessage

    ) {}

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
            List<String> attachmentNames,
            String senderEmail,
            List<String> receiverEmail,
            List<String> carbonCopy,
            String rawEmailKey,
            ProcessingStatus processingStatus,
            LocalDateTime sent,
            String errorMessage,
            TicketDto.Summary ticket,
            UUID customerId,
            String customerName
    ) {}
}
