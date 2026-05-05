package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.domain.dto.CustomerDto;
import com.mgudux.mail2ticket.domain.entities.Customer;
import com.mgudux.mail2ticket.exception.ConflictException;
import com.mgudux.mail2ticket.mapper.CustomerMapper;
import com.mgudux.mail2ticket.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerServiceImpl customerService;

    CustomerDto.Detail mockDetail = mock(CustomerDto.Detail.class);
    UUID id = UUID.randomUUID();

    @Test
    void createCustomer_shouldThrowConflict_whenEmailAlreadyExists() {
        when(customerRepository.existsByUserEmail("test@test.com")).thenReturn(true);

        assertThrows(ConflictException.class, () ->
                customerService.createCustomer(new CustomerDto.Request("Max", "Mustermann", "test@test.com"))
        );
    }

    @Test
    void createCustomer_shouldSaveCustomer_whenEmailDoesNotExist() {
        when(customerRepository.existsByUserEmail("test@test.com")).thenReturn(false);
        when(customerRepository.save(any())).thenReturn(new Customer());
        when(customerMapper.toSummary(any())).thenReturn(
                new CustomerDto.Summary(id, "Max", "Mustermann", "test@test.com"));
        CustomerDto.Request request = new CustomerDto.Request("Max", "Mustermann", "test@test.com");
        CustomerDto.Summary result = customerService.createCustomer(request);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(request.userEmail(), result.userEmail());

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void updateCustomer_shouldUpdateCustomer_whenEmailDoesNotExistAndIdExists() {
        when(customerRepository.findById(id))
                .thenReturn(Optional.of(new Customer()));
        when(customerRepository.existsByUserEmailAndIdNot("Test@Test.com", id))
                .thenReturn(false);
        when(customerRepository.save(any())).thenReturn(new Customer());
        when(customerMapper.toDetail(any())).thenReturn(mockDetail);

        assertDoesNotThrow(() -> customerService.updateCustomer(
                id,
                new CustomerDto.Request("Max", "Muster", "Test@test.com")
                ));
    }
}