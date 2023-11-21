package com.example.pastry.shop.aop;

import com.example.pastry.shop.service.OrderService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrderProcessingAspect {

   private final OrderService orderService;

    public OrderProcessingAspect(OrderService orderService) {
        this.orderService = orderService;
    }

    @Before("PointCuts.orderProcessing() && args(id)")
    public void findByKeyOrder(Long id) {
        orderService.getOrdersByKey(id);
    }
}
