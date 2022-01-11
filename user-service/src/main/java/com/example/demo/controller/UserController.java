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
	
	@GetMapping("/config/database")
    public String database(@Value("${spring.datasource.driver}") String driver,
                           @Value("${spring.datasource.url}") String url,
                           @Value("${spring.datasource.username}") String username,
                           @Value("${spring.datasource.password}") String password,
                           @Value("${jwt.token.key}") String tokenKey) {
        return "driver: " + driver + "\n"
                + "url: " + url + "\n"
                + "username: " + username + "\n"
                + "password: " + password + "\n\n"
                + "token key: " + tokenKey;
    }
}
