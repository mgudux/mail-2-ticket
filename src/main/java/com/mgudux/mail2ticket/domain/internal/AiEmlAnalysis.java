package com.mgudux.mail2ticket.domain.internal;

import com.mgudux.mail2ticket.domain.entities.Department;
import com.mgudux.mail2ticket.domain.entities.Sentiment;

public record AiEmlAnalysis(

        // Customer
        String extractedFirstName,
        String extractedLastName,

        // Ticket
        String extractedAiSummary,
        Department extractedDepartment,
        Sentiment extractedSentiment,
        boolean hadUnsupportedAttachments
) {
}
