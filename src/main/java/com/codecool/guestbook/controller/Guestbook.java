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
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            renderGuestbook(httpExchange);
        }

        if (method.equals("POST")) {
            handlePOST(httpExchange);
        }

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

    private void renderGuestbook(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/guestbook.twig");
        JtwigModel model = JtwigModel.newModel();
        create(model);
        String response = template.render(model);
        sendResponse(httpExchange, response);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        writeToOutput(httpExchange, response);
    }

    private Map<String, String> getPOSTInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String data = bufferedReader.readLine();
        return parse(data);
    }

    private void handlePOST(HttpExchange httpExchange) throws  IOException {
        Map<String, String> inputs = getPOSTInputs(httpExchange);
        String name = inputs.get("name");
        String message = inputs.get("message");
        messageService.createMessage(name, message);
        redirectToGuestbook(httpExchange);
    }

    private void redirectToGuestbook(HttpExchange httpExchange) throws IOException {
        String response = "";
        httpExchange.getResponseHeaders().set("Location", "/guestbook");
        httpExchange.sendResponseHeaders(302, response.getBytes().length);
        writeToOutput(httpExchange, response);
    }

    private void writeToOutput(HttpExchange httpExchange, String response) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}