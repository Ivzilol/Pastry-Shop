package com.example.pastry.shop.service;

import com.example.pastry.shop.events.UserTopClientEvent;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserTopClientService {

   void UserTopClient(UserTopClientEvent event) throws MessagingException, UnsupportedEncodingException;
}
