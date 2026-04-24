package com.mgudux.mail2ticket.domain.internal;

import com.mgudux.mail2ticket.domain.entities.Department;
import com.mgudux.mail2ticket.domain.entities.Sentiment;


// We parse the response from the AI analysis (JSON) into this java object.
public record AiEmlAnalysis(

        // Customer
        String extractedFirstName,
        String extractedLastName,

        // Ticket
        String extractedTicketTitle,
        String extractedAiSummary,
        Department extractedDepartment,
        Sentiment extractedSentiment,
        // in case the AI can't analyze some content, we will set PROCESSING_STATUS to PARTIAL_SUCCESS
        boolean hasUnanalyzedContent
) {
}
