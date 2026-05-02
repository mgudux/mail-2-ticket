package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.dto.CustomerDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.exception.ConflictException;
import com.mgudux.mail2ticket.exception.ResourceNotFoundException;
import com.mgudux.mail2ticket.exception.ValidationException;
import com.mgudux.mail2ticket.mapper.CustomerMapper;
import com.mgudux.mail2ticket.repositories.CustomerRepository;
import com.mgudux.mail2ticket.services.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerDto.Summary> listCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toSummary).toList();
    }

    @Override
    public CustomerDto.Summary createCustomer(CustomerDto.Request request) {
        if (customerRepository.existsByUserEmail(request.userEmail())) {
            throw new ConflictException("A customer with this email address already exists!");
        }

        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setUserEmail(request.userEmail());

        return customerMapper.toSummary(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(UUID id) {
        if (id == null) {
            throw new ValidationException("Customer ID cannot be null.");
        }
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer ID does not exist.");
        }
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto.Detail getCustomerById(UUID id) {
        if (id == null) {
            throw new ValidationException("Customer ID cannot be null.");
        }
        return customerRepository.findById(id).map(customerMapper::toDetail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No Customer exists with this ID!"));
    }

    @Override
    public CustomerDto.Detail getCustomerByEmail(String userEmail) {
        if (userEmail == null || userEmail.isBlank()) {
            throw new ValidationException("Customer Email cannot be empty!");
        }
        return customerRepository.findByUserEmail(userEmail)
                .map(customerMapper::toDetail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No customer with this email address exists!"));
    }

    @Transactional
    @Override
    public CustomerDto.Detail updateCustomer(UUID id, CustomerDto.Request request) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No customer with this ID exists!"));

        if (customerRepository.existsByUserEmailAndIdNot(request.userEmail(), id)) {
            throw new ConflictException("Email address is already used by another Customer!");
        }
        existingCustomer.setFirstName(request.firstName());
        existingCustomer.setLastName(request.lastName());
        existingCustomer.setUserEmail(request.userEmail());

        Customer saved = customerRepository.save(existingCustomer);
        return customerMapper.toDetail(saved);
    }
}
