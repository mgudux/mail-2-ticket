package com.mgudux.mail2ticket.domain.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgudux.mail2ticket.domain.entities.Department;
import com.mgudux.mail2ticket.domain.entities.Sentiment;


// We parse the response from the AI analysis (JSON) into this java object.
public record AiEmlAnalysis(
        @JsonProperty("extractedFirstName") String extractedFirstName,
        @JsonProperty("extractedLastName") String extractedLastName,
        @JsonProperty("extractedTicketTitle") String extractedTicketTitle,
        @JsonProperty("extractedAiSummary") String extractedAiSummary,
        @JsonProperty("extractedDepartment") Department extractedDepartment,
        @JsonProperty("extractedSentiment") Sentiment extractedSentiment,
        @JsonProperty("hasUnanalyzedContent") boolean hasUnanalyzedContent
) {}