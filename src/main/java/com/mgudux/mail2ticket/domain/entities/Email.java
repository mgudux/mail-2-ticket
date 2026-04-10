package com.mgudux.mail2ticket.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @jakarta.validation.constraints.Email
    @Column(name = "sender_email", nullable = false)
    private String senderEmail;

    @NotBlank
    @jakarta.validation.constraints.Email
    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "raw_content")
    private String rawContent;

    @Column(name = "has_attachments")
    private boolean hasAttachments = false;

    @Column(name = "attachment_ocr")
    private String attachmentOCR;

    // Was this email successfully extracted into a Ticket?
    @Enumerated(EnumType.STRING)
    @Column(name = "processing_status", nullable = false)
    private ProcessingStatus processingStatus = ProcessingStatus.MANUAL_CHECK_REQUIRED;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "upload_batch_id")
    private String uploadBatchId; // Which upload generated this email?

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @OneToMany(mappedBy = "email", cascade = CascadeType.DETACH)
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return hasAttachments == email.hasAttachments && Objects.equals(id, email.id) && Objects.equals(senderEmail, email.senderEmail) && Objects.equals(receiverEmail, email.receiverEmail) && Objects.equals(subject, email.subject) && Objects.equals(body, email.body) && Objects.equals(rawContent, email.rawContent) && Objects.equals(attachmentOCR, email.attachmentOCR) && processingStatus == email.processingStatus && Objects.equals(errorMessage, email.errorMessage) && Objects.equals(uploadBatchId, email.uploadBatchId) && Objects.equals(created, email.created) && Objects.equals(updated, email.updated) && Objects.equals(tickets, email.tickets) && Objects.equals(customer, email.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderEmail, receiverEmail, subject, body, rawContent, hasAttachments, attachmentOCR, processingStatus, errorMessage, uploadBatchId, created, updated, tickets, customer);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", rawContent='" + rawContent + '\'' +
                ", hasAttachments=" + hasAttachments +
                ", attachmentOCR='" + attachmentOCR + '\'' +
                ", processingStatus=" + processingStatus +
                ", errorMessage='" + errorMessage + '\'' +
                ", uploadBatchId='" + uploadBatchId + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", tickets=" + tickets +
                ", customer=" + customer +
                '}';
    }
}