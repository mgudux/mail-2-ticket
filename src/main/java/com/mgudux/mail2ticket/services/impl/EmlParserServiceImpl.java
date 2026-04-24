package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.config.AttachmentProperties;
import com.mgudux.mail2ticket.domain.internal.AttachmentData;
import com.mgudux.mail2ticket.domain.internal.ParsedMail;
import com.mgudux.mail2ticket.exception.ValidationException;
import com.mgudux.mail2ticket.repositories.EmlFileRepository;
import com.mgudux.mail2ticket.services.EmlParserService;
import io.micrometer.common.util.StringUtils;
import org.jsoup.Jsoup;
import org.simplejavamail.api.email.AttachmentResource;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.Recipient;
import org.simplejavamail.converter.EmailConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@EnableConfigurationProperties
@Service
public class EmlParserServiceImpl implements EmlParserService {

    private final AttachmentProperties attachmentProperties;
    private final EmlFileRepository emlFileRepository;

    public EmlParserServiceImpl(AttachmentProperties attachmentProperties, EmlFileRepository emlFileRepository) {
        this.attachmentProperties = attachmentProperties;
        this.emlFileRepository = emlFileRepository;
    }

    @Override
    public ParsedMail parse(MultipartFile file) {
        Email email = parseRawEmail(file);
        validateEmail(email);
        return mapToParsedMail(email);
    }

    private Email parseRawEmail(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return EmailConverter.emlToEmail(inputStream);
        } catch (Exception e) {
            throw new ValidationException("Failed to process the uploaded EML file.", e);
        }
    }

    private void validateEmail(Email email) {
        boolean hasNoContent = StringUtils.isBlank(email.getSubject()) &&
                        StringUtils.isBlank(email.getPlainText()) &&
                        StringUtils.isBlank(email.getHTMLText()) &&
                email.getAttachments().isEmpty();
        if (hasNoContent) {
            throw new ValidationException("Cannot create email without a subject, body or attachment");
        }

        boolean hasNoSender = email.getFromRecipient() == null
                || StringUtils.isBlank(email.getFromRecipient().getAddress());
        if (hasNoSender) {
            throw new ValidationException("Cannot process email without a valid sender address");
        }

        boolean alreadyUploaded = emlFileRepository.existsByMessageId(email.getId());
        if (alreadyUploaded) {
            throw new ValidationException("Email has already been analyzed!");
        }

    }

    private ParsedMail mapToParsedMail(Email email) {
        return new ParsedMail(
                email.getId(),
                email.getSubject(),
                extractBody(email),
                email.getFromRecipient().getAddress(),
                email.getFromRecipient().getName(),
                extractAddresses(email.getRecipients()),
                extractAddresses(email.getCcRecipients()),
                extractAttachmentNames(email.getAttachments()),
                extractAttachmentData(email.getAttachments()),
                convertSentTime(email.getSentDate())
        );
    }

    private String extractBody(Email email) {
        String emlText = "";

        if (StringUtils.isNotBlank(email.getPlainText())) {
            emlText += email.getPlainText();
        }
        if (StringUtils.isNotBlank(email.getHTMLText())) {
            emlText += Jsoup.parse(email.getHTMLText()).text();
        }

        return emlText;
    }


    private List<String> extractAddresses(List<Recipient> recipients) {
        return recipients.stream()
                .map(Recipient::getAddress)
                .filter(StringUtils::isNotBlank)
                .toList();
    }

    private List<String> extractAttachmentNames(List<AttachmentResource> attachments) {
        return attachments.stream()
                .map(AttachmentResource::getName)
                .filter(StringUtils::isNotBlank)
                .toList();
    }


    private List<AttachmentData> extractAttachmentData(List<AttachmentResource> attachments) {
        return attachments.stream()
                .filter(a -> StringUtils.isNotBlank(a.getName())
                        && attachmentProperties.allowedMimeTypes().contains(a.getDataSource().getContentType()))
                .map(a -> {
                    try {
                        String filename = a.getName();
                        byte[] bytes = a.getDataSource().getInputStream().readAllBytes();
                        String mimeType = a.getDataSource().getContentType();
                        return new AttachmentData(filename, bytes, mimeType);
                    } catch (IOException e) {
                        throw new ValidationException("Failed to extract attachment " + a.getName(), e);
                    }
                }
                ).toList();
    }

    private LocalDateTime convertSentTime(Date date) {
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return LocalDateTime.now();
    }
}
