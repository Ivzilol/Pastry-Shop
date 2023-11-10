package com.example.pastry.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
@Component
public class MessageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        System.out.println("METHOD type:" + request.getRequestURI());
        String requestURI = request.getRequestURI();
        return Objects.equals(requestURI, "/message/{room}");
    }
}
