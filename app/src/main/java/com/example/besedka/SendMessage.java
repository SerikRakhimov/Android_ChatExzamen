package com.example.besedka;

public class SendMessage {
    private String name, text;

    public SendMessage(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getUserName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
