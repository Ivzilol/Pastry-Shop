package com.example.pastry.shop.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(@NotNull MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/api");
        registry.enableSimpleBroker("/chat", "/users");
        registry.setApplicationDestinationPrefixes("/user");
    }

    @Override
    public void registerStompEndpoints(@NotNull StompEndpointRegistry registry) {
       registry.addEndpoint("/chat").setAllowedOriginPatterns("*").withSockJS();
    }
}
