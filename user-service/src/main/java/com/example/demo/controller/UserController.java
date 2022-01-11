package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@GetMapping("/info")
	public String info(@Value("${server.port}") String port) {
		return "User 서비스의 기본 동작 Port: {"+port+"}";
	}
	
	@GetMapping("/auth")
    public String auth(@RequestHeader(value = "token") String token) {
        return "token is " + token;
    }
	@GetMapping("/config")
    public String string(@Value("${message.owner}") String messageOwner,
                         @Value("${message.content}") String messageContent) {
        return "Configuration File's Message Owner: " + messageOwner + "\n"+ "Configuration File's Message Content: " + messageContent;
    }
}
