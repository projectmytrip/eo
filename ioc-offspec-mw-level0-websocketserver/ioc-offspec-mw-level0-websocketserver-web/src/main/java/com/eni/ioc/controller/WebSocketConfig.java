package com.eni.ioc.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.eni.ioc.server.MyMessageHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
 
    @Bean
    public WebSocketHandler myMessageHandler() {
        return new MyMessageHandler();
    }
 
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myMessageHandler(), "/websocket").setAllowedOrigins("*");
    }
    


 
}
