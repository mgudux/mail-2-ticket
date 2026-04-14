package com.mgudux.mail2ticket.mapper.impl;

import com.mgudux.mail2ticket.domain.dto.EmlFileDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.domain.entities.EmlFile;
import com.mgudux.mail2ticket.mapper.EmlFileMapper;
import com.mgudux.mail2ticket.mapper.TicketMapper;
import org.springframework.stereotype.Component;

@Component
public class EmlFileMapperImpl implements EmlFileMapper {

    private final TicketMapper ticketMapper;

    public EmlFileMapperImpl(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }


    @Override
    public EmlFileDto.Summary toSummary(EmlFile emlFile) {
        if (emlFile == null) {
            return null;
        }
        return new EmlFileDto.Summary(
                emlFile.getId(),
                emlFile.getSubject(),
                emlFile.getSenderEmail(),
                emlFile.getProcessingStatus()
        );
    }

    @Override
    public EmlFileDto.Detail toDetail(EmlFile emlFile) {
        if (emlFile == null) {
            return null;
        }
        Customer customer = emlFile.getCustomer();
        return new EmlFileDto.Detail(
                emlFile.getId(),
                emlFile.getSubject(),
                emlFile.getBody(),
                emlFile.getSenderEmail(),
                emlFile.getReceiverEmail(),
                emlFile.getRawEmailS3Key(),
                emlFile.getProcessingStatus(),
                emlFile.getErrorMessage(),
                emlFile.getUploadBatchId(),
                ticketMapper.toSummary(emlFile.getTicket()),
                customer != null ? customer.getId() : null,
                customer != null ?
                        customer.getFirstName() + " " + customer.getLastName()
                        : "Unknown"

        );
    }
}
