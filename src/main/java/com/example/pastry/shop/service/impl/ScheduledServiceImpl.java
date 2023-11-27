package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.model.entity.ChatMessages;
import com.example.pastry.shop.repository.ChatMessagesRepository;
import com.example.pastry.shop.service.ScheduledService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScheduledServiceImpl implements ScheduledService {

    private final ChatMessagesRepository chatMessagesRepository;

    private static final String HOUR = "0 0 0 * * ?";

    public ScheduledServiceImpl(ChatMessagesRepository chatMessagesRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
    }

    @Override
    @Scheduled(cron = HOUR)
    public void deleteFinishedChat() {
        Set<ChatMessages> chatMessages = this.chatMessagesRepository.deleteFinishedChat();
        this.chatMessagesRepository.deleteAll(chatMessages);
    }
}
