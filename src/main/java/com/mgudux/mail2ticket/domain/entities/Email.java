package com.mgudux.mail2ticket.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
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

    @Column(name = "raw_email_s3key")
    private String rawEmailS3Key;

    @Column(name = "attachment_s3_keys")
    @ElementCollection List<String> attachmentS3Keys;

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

    @OneToOne(mappedBy = "email", cascade = CascadeType.DETACH)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return attachmentS3Keys == email.attachmentS3Keys && Objects.equals(id, email.id) && Objects.equals(senderEmail, email.senderEmail) && Objects.equals(receiverEmail, email.receiverEmail) && Objects.equals(subject, email.subject) && Objects.equals(body, email.body) && Objects.equals(rawEmailS3Key, email.rawEmailS3Key) && Objects.equals(attachmentOCR, email.attachmentOCR) && processingStatus == email.processingStatus && Objects.equals(errorMessage, email.errorMessage) && Objects.equals(uploadBatchId, email.uploadBatchId) && Objects.equals(created, email.created) && Objects.equals(updated, email.updated);
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
                ", attachmentS3Keys=" + attachmentS3Keys +
                ", attachmentOCR='" + attachmentOCR + '\'' +
                ", processingStatus=" + processingStatus +
                ", errorMessage='" + errorMessage + '\'' +
                ", uploadBatchId='" + uploadBatchId + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}