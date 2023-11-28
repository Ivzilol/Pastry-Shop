package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.service.MonitoringService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final Counter productSearch;

    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        productSearch = Counter
                .builder("product_search_cnt")
                .description("How many products search we have?")
                .register(meterRegistry);
    }

    @Override
    public void logProductSearch() {
        productSearch.increment();
    }

    @Override
    public double getProductSearchCount() {
        return productSearch.count();
    }
}
