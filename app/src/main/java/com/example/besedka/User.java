package com.example.besedka;

public class User {
    private String name;
    private Integer online;

    public User(String name, Integer online) {
        this.name = name;
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public Integer getOnline() {
        return online;
    }

}
