package com.mgudux.mail2ticket.domain.entities;

import lombok.Getter;

@Getter
public enum TicketStatus {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed");

    private final String status;

    TicketStatus(String status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return (this == RESOLVED || this == CLOSED);
    }
}
