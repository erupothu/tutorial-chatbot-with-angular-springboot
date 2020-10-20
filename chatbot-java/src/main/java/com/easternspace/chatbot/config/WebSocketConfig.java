package com.easternspace.chatbot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.easternspace.chatbot.model.Message;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
	}

	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("");
		config.setApplicationDestinationPrefixes("/app");
	}
	
	@EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }
	
	@EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            System.out.println("User Disconnected : " + username);

            Message chatMessage = new Message();
            chatMessage.setFromUserId("123");
            chatMessage.setEmpName(username);

            messagingTemplate.convertAndSend("user_topic", chatMessage);
        }
    }
}
