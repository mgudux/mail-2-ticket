package com.mgudux.mail2ticket.domain.entities;

import lombok.Getter;

@Getter
public enum Department {
    SALES("Sales"),
    LEGAL("Legal"),
    TECH("Tech"),
    ACCOUNTING("Accounting"),
    HR("Human Resources"),
    UNKNOWN("Unknown");

    private final String displayName;

    Department(String displayName) {
        this.displayName = displayName;
    }

    public static Department fromString(String value) {
        for (Department dept : Department.values()) {
            if (dept.name().equalsIgnoreCase(value)) {
                return dept;
            }
        }
        return UNKNOWN;
    }

}
