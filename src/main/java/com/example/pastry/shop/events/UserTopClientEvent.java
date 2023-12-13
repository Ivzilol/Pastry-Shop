package com.example.pastry.shop.events;

import org.springframework.context.ApplicationEvent;

public class UserTopClientEvent extends ApplicationEvent {

    private final String userEmail;

    public UserTopClientEvent(Object source, String userEmail) {
        super(source);
        this.userEmail = userEmail;
    }


    public String getUserEmail() {
        return userEmail;
    }
}
