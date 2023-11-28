package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.MonitoringController;
import com.example.pastry.shop.service.MonitoringService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
public class MonitoringControllerImpl implements MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringControllerImpl(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }


    @Override
    public double getProductSearchCount() {
        return monitoringService.getProductSearchCount();
    }
}
