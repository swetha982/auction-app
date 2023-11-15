package com.bidding.userapp.websocket;


import com.bidding.clientApp.websocket.MyStompSessionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

@Configuration
@EnableWebSocketMessageBroker
public class StompClientConfig {

    private static String URL = "ws://localhost:8082/test2";

    @Bean
    public static WebSocketClient webSocketClient() {
        System.out.println("Started WS");
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new StringMessageConverter());
        // stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(URL, sessionHandler);

        new Scanner(System.in).nextLine(); // Don't close immediately.
        return client;
    }


}

