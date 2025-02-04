package com.scaffold.ticketSystem.enums;

public enum Role {
    ADMIN,
    USER,
    QA;

    public String toString() {
        return this.name();
    }
}
