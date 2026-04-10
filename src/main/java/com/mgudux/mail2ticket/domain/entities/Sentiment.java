package com.mgudux.mail2ticket.domain.entities;

import lombok.Getter;

import java.util.List;

import static java.util.Arrays.stream;

@Getter
public enum Sentiment {
    THREATENING(100, List.of("lawyer", "lawsuit", "sue", "court", "law", "legal")),
    ANGRY(90, List.of("unacceptable", "outrageous", "angry", "complaint", "terrible", "mad")),
    FRUSTRATED(80, List.of("again", "frustrated", "not working", "disappointed", "stuck", "failing", "annoyed")),
    NEUTRAL(50, List.of("question", "info", "address", "change", "update", "inquiry")),
    SATISFIED(40, List.of("thanks", "resolved", "works", "good", "fine", "appreciate")),
    POSITIVE(30, List.of("great", "awesome", "perfect", "excellent", "love", "amazing")),
    UNKNOWN(0, List.of());

    private final int priority;
    private final List<String> keywords;

    Sentiment(int priority, List<String> keywords) {
        this.priority = priority;
        this.keywords = keywords;
    }

    public static Sentiment fromString(String value) {
        return stream(Sentiment.values())
                .filter(sentiment -> sentiment.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(UNKNOWN);
    }

    // Fallback method if AI fails
    public static Sentiment guessFromTextFallback(String emailBody) {
        if (emailBody == null || emailBody.isBlank()) return UNKNOWN;
        String lowercaseEmail = emailBody.toLowerCase();

        return stream(Sentiment.values())
                .filter(sentiment -> sentiment.keywords.stream()
                        .anyMatch(keyword -> lowercaseEmail.contains(keyword)))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
