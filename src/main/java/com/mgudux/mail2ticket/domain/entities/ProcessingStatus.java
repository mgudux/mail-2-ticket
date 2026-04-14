package com.mgudux.mail2ticket.domain.entities;

import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
public enum ProcessingStatus {
    SUCCESS("Success", "Extraction and analysis success!"),
    PARTIAL_SUCCESS("Partial Success", "Partial success but invalid data has been skipped"),
    MANUAL_CHECK_REQUIRED("Manual Check Required", "Requires manual check");

    private final String displayName;
    private final String message;

    ProcessingStatus(String displayName, String message) {
        this.displayName = displayName;
        this.message = message;
    }

    public boolean isProcessed() {
        return this == SUCCESS || this == PARTIAL_SUCCESS;
    }

    public static ProcessingStatus fromString(String value) {
        return stream(ProcessingStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(MANUAL_CHECK_REQUIRED);
    }
}