package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.ChatMessages;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.ChatMessagesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {

    private final ChatMessagesRepository chatMessagesRepository;

    public ChatService(ChatMessagesRepository chatMessagesRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
    }


    public Optional<ChatMessages> findByUserId(Users users) {
        return this.chatMessagesRepository.findBySendBy(users);
    }
}
