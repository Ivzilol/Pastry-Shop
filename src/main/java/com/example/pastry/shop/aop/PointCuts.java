package com.example.pastry.shop.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {


    @Pointcut("execution(* com.example.pastry.shop.service.impl.OrderServiceImpl.findByUsersId(..))")
    public void orderProcessing(){}


}
