package com.example.pastry.shop.service;

import com.example.pastry.shop.controllers.ChatController;
import com.example.pastry.shop.model.entity.ChatMessages;
import com.example.pastry.shop.repository.ChatMessagesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScheduledService {

    private final ChatMessagesRepository chatMessagesRepository;

    public ScheduledService(ChatMessagesRepository chatMessagesRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteFinishedChat() {
        Set<ChatMessages> chatMessages = this.chatMessagesRepository.deleteFinishedChat();
        this.chatMessagesRepository.deleteAll(chatMessages);
    }
}
