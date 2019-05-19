package com.codecool.guestbook.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private final OffsetDateTime date;
    private final String name;
    private final String message;

    public Message (OffsetDateTime date, String name, String message) {
        this.date = date;
        this.name = name;
        this.message = message;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public String getFormattedDate() {
        String format = "dd/MM/yy E hh:mm a";
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}