package com.codecool.guestbook.model;

import java.time.LocalDate;

public class Message {

    private final LocalDate DATE;
    private final String NAME;
    private final String MESSAGE;

    public Message (LocalDate DATE, String NAME, String MESSAGE) {
        this.DATE = DATE;
        this.NAME = NAME;
        this.MESSAGE = MESSAGE;
    }

    public LocalDate getDATE() {
        return DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}