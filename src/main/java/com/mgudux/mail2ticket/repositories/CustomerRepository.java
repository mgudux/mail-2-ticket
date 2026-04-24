package com.mgudux.mail2ticket.repositories;

import com.mgudux.mail2ticket.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);

    // if user wants to change userEmail, check if that Email is already used by someone else
    boolean existsByUserEmailAndIdNot(String userEmail, UUID id);
}