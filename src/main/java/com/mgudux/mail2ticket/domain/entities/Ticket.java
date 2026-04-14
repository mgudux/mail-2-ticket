package com.mgudux.mail2ticket.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "ticket_number", unique = true)
    private String ticketNumber;

    @Column(name = "ai_summary")
    private String aiSummary;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false)
    private TicketStatus ticketStatus = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false)
    private Department department = Department.UNKNOWN;

    @Enumerated(EnumType.STRING)
    @Column(name = "sentiment", nullable = false)
    private Sentiment sentiment = Sentiment.UNKNOWN;

    // Ticket ready to use or needs human review
    @Enumerated(EnumType.STRING)
    @Column(name = "processing_status", nullable = false)
    private ProcessingStatus processingStatus = ProcessingStatus.MANUAL_CHECK_REQUIRED;

    @Column(name = "error_message")
    private String errorMessage;


    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_id")
    private Email email;

    @PrePersist
    protected void onCreate() {
        if (this.ticketNumber == null) {
            this.ticketNumber = "TKT-" + this.id.toString().substring(0, 8).toUpperCase();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(ticketNumber, ticket.ticketNumber) && Objects.equals(aiSummary, ticket.aiSummary) && ticketStatus == ticket.ticketStatus && department == ticket.department && sentiment == ticket.sentiment && processingStatus == ticket.processingStatus && Objects.equals(errorMessage, ticket.errorMessage) && Objects.equals(created, ticket.created) && Objects.equals(updated, ticket.updated);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", aiSummary='" + aiSummary + '\'' +
                ", ticketStatus=" + ticketStatus +
                ", department=" + department +
                ", sentiment=" + sentiment +
                ", processingStatus=" + processingStatus +
                ", errorMessage='" + errorMessage + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}