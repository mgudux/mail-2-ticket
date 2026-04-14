package com.mgudux.mail2ticket.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
public class EmlFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotBlank
    @Email
    @Column(name = "sender_email", nullable = false)
    private String senderEmail;

    @NotBlank
    @Email
    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "raw_email_s3key")
    private String rawEmailS3Key;

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

    @OneToOne(mappedBy = "email", cascade = CascadeType.DETACH)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmlFile emlFile = (EmlFile) o;
        return Objects.equals(id, emlFile.id) && Objects.equals(senderEmail, emlFile.senderEmail) && Objects.equals(receiverEmail, emlFile.receiverEmail) && Objects.equals(subject, emlFile.subject) && Objects.equals(body, emlFile.body) && Objects.equals(rawEmailS3Key, emlFile.rawEmailS3Key) && processingStatus == emlFile.processingStatus && Objects.equals(errorMessage, emlFile.errorMessage) && Objects.equals(uploadBatchId, emlFile.uploadBatchId) && Objects.equals(created, emlFile.created) && Objects.equals(updated, emlFile.updated) && Objects.equals(ticket, emlFile.ticket) && Objects.equals(customer, emlFile.customer);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", rawContent='" + rawEmailS3Key + '\'' +
                ", processingStatus=" + processingStatus +
                ", errorMessage='" + errorMessage + '\'' +
                ", uploadBatchId='" + uploadBatchId + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}