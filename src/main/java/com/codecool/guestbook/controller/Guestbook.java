package com.codecool.guestbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.nio.charset.StandardCharsets;

import java.net.URLDecoder;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.codecool.guestbook.model.Message;
import com.codecool.guestbook.service.MessageService;

public class Guestbook implements HttpHandler {

    private MessageService messageService;

    public Guestbook() {
        messageService = new MessageService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/guestbook.twig");
        JtwigModel model = JtwigModel.newModel();
        String method = httpExchange.getRequestMethod();
        String response = "";

        if (method.equals("GET")) {
            create(model);
            response = template.render(model);
        }

        if (method.equals("POST")) {
            InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String data = bufferedReader.readLine();
            Map inputs = parse(data);

            String name = inputs.get("name").toString();
            String message = inputs.get("message").toString();
            messageService.createMessage(name, message);

            create(model);
            response = template.render(model);
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private static Map<String, String> parse(String data) throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<>();
        String[] pairs = data.split("&");
        String[] keyValue;
        String value;

        for (String pair : pairs) {
            keyValue = pair.split("=");
            value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private void create(JtwigModel model) {
        List<Message> messages = messageService.getMessages();
        model.with("messages", messages);
    }
}