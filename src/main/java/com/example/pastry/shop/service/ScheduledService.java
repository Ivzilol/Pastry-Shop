package com.example.pastry.shop.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduledService {
    @Scheduled()
    void deleteFinishedChat();
}
