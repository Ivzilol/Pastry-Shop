package com.example.pastry.shop.service;

import com.example.pastry.shop.events.UserTopClientEvent;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


public interface UserTopClientService {
    @EventListener(UserTopClientEvent.class)
    void UserTopClient(UserTopClientEvent event) throws MessagingException, UnsupportedEncodingException;
}
