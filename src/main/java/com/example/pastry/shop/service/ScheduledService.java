package com.example.pastry.shop.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

public interface ScheduledService {
    @Scheduled()
    void deleteFinishedChat();
}
