package com.codecool.guestbook.dao;

import java.util.List;

import com.codecool.guestbook.model.Message;

public interface MessageDao {

    public void create(Message message) throws DaoException;

    public List<Message> getMessages() throws DaoException;
}