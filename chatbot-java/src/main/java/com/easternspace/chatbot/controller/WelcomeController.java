package com.easternspace.chatbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	
	@GetMapping("welcome")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("welcome to Chatbot App");
	}

}
