package com.mgudux.mail2ticket.repositories;

import com.mgudux.mail2ticket.domain.entities.EmlFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmlFileRepository extends JpaRepository<EmlFile, UUID> {
    boolean existsByMessageId(String messageId);
}
