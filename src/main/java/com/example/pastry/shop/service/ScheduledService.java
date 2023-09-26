package com.example.pastry.shop.service;

import com.example.pastry.shop.repository.ChatMessagesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    private final ChatMessagesRepository chatMessagesRepository;

    public ScheduledService(ChatMessagesRepository chatMessagesRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
    }

    @Scheduled(cron = "0 0 11 * * ?")
    public void deleteFinishedChat() {
        this.chatMessagesRepository.deleteFinishedChat();
    }
}
