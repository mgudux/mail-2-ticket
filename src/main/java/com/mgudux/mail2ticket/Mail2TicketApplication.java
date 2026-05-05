package com.mgudux.mail2ticket;

import com.mgudux.mail2ticket.config.AttachmentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AttachmentProperties.class)
public class Mail2TicketApplication {

	static void main(String[] args) {
		SpringApplication.run(Mail2TicketApplication.class, args);
	}

}
