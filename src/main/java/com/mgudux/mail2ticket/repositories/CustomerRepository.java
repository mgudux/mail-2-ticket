package com.mgudux.mail2ticket.repositories;

import com.mgudux.mail2ticket.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // Find existing Customer by Names + Email, if yes returns the customer
    Optional<Customer> findByFirstNameAndLastNameAndUserEmail(String firstName, String lastName, String userEmail);

    // Fallback, only checks if Email already exists, if yes it returns the customer
    Optional<Customer> findByUserEmail(String userEmail);
}