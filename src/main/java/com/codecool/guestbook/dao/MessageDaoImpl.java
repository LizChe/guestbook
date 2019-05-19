package com.codecool.guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.time.OffsetDateTime;

import com.codecool.guestbook.model.Message;

public class MessageDaoImpl implements MessageDao {

    @Override
    public void create(Message message) throws DaoException {

        String query = "INSERT INTO messages "
                + "(date, name, message) "
                + "VALUES(?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, message.getDate());
            preparedStatement.setString(2, message.getName());
            preparedStatement.setString(3, message.getMessage());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Failed to create a message.\n" + e);
        }
    }

    @Override
    public List<Message> getMessages() throws DaoException {

        List<Message> messages;
        String query = "SELECT date, name, message FROM messages "
                + "ORDER BY id";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            messages = getMessagesFrom(preparedStatement);

        } catch (SQLException e) {
            throw new DaoException("Failed to receive messages.\n" + e);
        }
        return messages;
    }

    private List<Message> getMessagesFrom(PreparedStatement preparedStatement) throws DaoException {

        Message message;
        List<Message> messages = new ArrayList<>();

        OffsetDateTime date;
        String name;
        String userMessage;

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                date = resultSet.getObject("date", OffsetDateTime.class);
                name = resultSet.getString("name");
                userMessage = resultSet.getString("message");

                message = new Message(date, name, userMessage);
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to populate list of messages.\n" + e);
        }
        return messages;
    }
}