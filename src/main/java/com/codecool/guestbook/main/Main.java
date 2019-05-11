package com.codecool.guestbook.main;

import java.io.IOException;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import com.codecool.guestbook.controller.Guestbook;
import com.codecool.guestbook.controller.Static;

public class Main {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/guestbook", new Guestbook());
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}
