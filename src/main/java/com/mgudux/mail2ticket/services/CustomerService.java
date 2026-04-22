package com.mgudux.mail2ticket.services;

import com.mgudux.mail2ticket.domain.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDto.Summary> listCustomers();
    CustomerDto.Summary createCustomer(CustomerDto.Request request);
    void deleteCustomer(UUID id);
    CustomerDto.Detail getCustomerById(UUID id);
    CustomerDto.Detail getCustomerByEmail(String userEmail);
    CustomerDto.Detail updateCustomer(UUID id, CustomerDto.Request request);
}
