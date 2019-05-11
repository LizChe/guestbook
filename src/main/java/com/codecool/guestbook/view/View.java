package com.codecool.guestbook.view;

public class View {

    private final String RESET = "\u001B[0m";

    private void printText(String text) {
        System.out.println(text);
    }

    public void printError(String text) {
        final String RED = "\u001B[31m";
        printText(RED + text + RESET);
    }

    public void printSuccess(String text) {
        final String GREEN = "\u001B[32m";
        printText(GREEN + text + RESET);
    }
}