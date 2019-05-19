package com.codecool.guestbook.service;

import java.util.List;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.codecool.guestbook.dao.DaoException;
import com.codecool.guestbook.dao.MessageDao;
import com.codecool.guestbook.dao.MessageDaoImpl;

import com.codecool.guestbook.model.Message;

import com.codecool.guestbook.view.View;

public class MessageService {

    private MessageDao messageDao;
    private List<Message> messages;
    private View view;

    public MessageService() {
        messageDao = new MessageDaoImpl();
        view = new View();
    }

    public List<Message> getMessages() {
        try {
            messages = messageDao.getMessages();
            view.printSuccess("Messages have been successfully retrieved.");
        } catch (DaoException e) {
            view.printError(e.getMessage());
        }
        return messages;
    }

    public void createMessage(String name, String userMessage) {
        Message message;
        OffsetDateTime date = OffsetDateTime.now(ZoneOffset.UTC);

        message = new Message(date, name, userMessage);

        try {
            messageDao.create(message);
            view.printSuccess("Message has been successfully created.");
        } catch (DaoException e) {
            view.printError(e.getMessage());
        }
    }
}