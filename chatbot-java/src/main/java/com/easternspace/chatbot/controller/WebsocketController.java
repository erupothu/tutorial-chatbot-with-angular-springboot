package com.easternspace.chatbot.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.easternspace.chatbot.model.Message;
import com.easternspace.chatbot.repository.MessageRepository;

@RestController
public class WebsocketController {

	@Value("${stomp.topic}")
	private String stompTopic;
	
	@Autowired
	private SimpUserRegistry simpUserRegistry;

	@Autowired
	private MessageRepository msgRepo;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public static List<String> topics;

	@GetMapping(value = "user/{userId}")
	public List<Message> getusersById(@PathVariable String userId) {
		List<Message> emp_messages = new ArrayList<Message>();
		emp_messages.addAll(msgRepo.findMessageByToUserId(userId));
		emp_messages.addAll(msgRepo.findMessageByFromUserId(userId));
		return emp_messages;
	}

	@GetMapping(value = "today-messages/{userId}")
	public List<Message> allusersMessages(@PathVariable String userId) {
		List<Message> emp_messages = new ArrayList<Message>();
		emp_messages.addAll(msgRepo.findMessageByToUserId(userId));
		emp_messages.addAll(msgRepo.findMessageByFromUserId(userId));
		return emp_messages;
	}

	@GetMapping(value = "today-users-messages")
	public List<Message> todayAllusersMessages() {
		List<Message> messages = null;

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String myDate = simpleDateFormat.format(new Date());
		Date date = new Date();
		try {
			date = simpleDateFormat.parse(myDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messages = msgRepo.findMessageByDate(date);
		return messages;
	}
	
	@MessageMapping("")
	@SendToUser("/user_topic")
	public String reply(@Payload String message,
	   Principal user) {
	 return  "Hello " + message;
	}
	
	@GetMapping(value = "connections")
	public int getNumberOfSessions(Principal principalUser) {
	    return simpUserRegistry.getUserCount();
	}
	
	@MessageMapping("/chat-add-user")
    public Message addUser(Message chatMessage) throws Exception  {
        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getEmpName());
        System.out.println(chatMessage.getEmpName());
        messagingTemplate.convertAndSend(stompTopic, chatMessage);
        return chatMessage;
    }

	@MessageMapping("/user")
	public Message userMessage(Message message) throws Exception {
//		Thread.sleep(1000); // simulated delay
		
		stompTopic = message.getFromUserId();
		List<Message> fromUserMessages = new ArrayList<Message>();
		fromUserMessages.addAll(msgRepo.findMessageByToUserId(message.getFromUserId()));
		fromUserMessages.addAll(msgRepo.findMessageByFromUserId(message.getFromUserId()));
		Collections.sort(fromUserMessages, new Comparator<Message>() {
			public int compare(Message u1, Message u2) {
				return u2.getDate().compareTo(u1.getDate());
			}
		});
		messagingTemplate.convertAndSend(stompTopic, fromUserMessages);
		
		stompTopic = message.getToUserId();
		List<Message> toUserMessages = new ArrayList<Message>();
		toUserMessages.addAll(msgRepo.findMessageByToUserId(message.getToUserId()));
		toUserMessages.addAll(msgRepo.findMessageByFromUserId(message.getToUserId()));
		Collections.sort(toUserMessages, new Comparator<Message>() {
			public int compare(Message u1, Message u2) {
				return u2.getDate().compareTo(u1.getDate());
			}
		});
		messagingTemplate.convertAndSend(stompTopic, toUserMessages);

		return message;
	}
	
	@GetMapping(value = "my-testing")
	@MessageMapping("/testing")
	public String userMessage(String message) throws Exception {
		String response = "welcomet to websocket";
		messagingTemplate.convertAndSend(stompTopic, message);
		return response;
	}
	
	@GetMapping(value = "connect-user/{user}")
	public void connectUser(@PathVariable String user) throws Exception {
		topics.add(user);
	}
	
	@GetMapping(value = "get-connect-user/{user}")
	public ResponseEntity<?> getConnectUser() throws Exception {
		
		return ResponseEntity.ok(topics);
	}
	
	

}
