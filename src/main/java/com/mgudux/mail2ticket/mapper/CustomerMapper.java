package com.mgudux.mail2ticket.mapper;

import com.mgudux.mail2ticket.domain.dto.CustomerDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.integration.ai.dto.AiEmlAnalysis;

public interface CustomerMapper {

    CustomerDto.Summary toSummary(Customer customer);
    CustomerDto.Detail toDetail(Customer customer);
    Customer fromAiAnalysis(AiEmlAnalysis aiEmlAnalysis);
}
