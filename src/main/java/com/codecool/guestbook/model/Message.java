package com.codecool.guestbook.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private final OffsetDateTime DATE;
    private final String NAME;
    private final String MESSAGE;

    public Message (OffsetDateTime DATE, String NAME, String MESSAGE) {
        this.DATE = DATE;
        this.NAME = NAME;
        this.MESSAGE = MESSAGE;
    }

    public OffsetDateTime getDATE() {
        return DATE;
    }

    public String getFormattedDate() {
        String format = "dd/MM/yy E hh:mm a";
        return DATE.format(DateTimeFormatter.ofPattern(format));
    }

    public String getNAME() {
        return NAME;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}