package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.ChatMessages;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatroom")
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatMessages receiveMessage(@Payload ChatMessages chatMessages){
        return chatMessages;
    }

    @MessageMapping("/private-message")
    public ChatMessages receivePrivateMessage(@Payload ChatMessages chatMessages){
        simpMessagingTemplate.convertAndSendToUser(chatMessages.getReceiverName(), "/private", chatMessages);
        return chatMessages;
    }
}
