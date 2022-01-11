package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@GetMapping("/user/info")
	public String info(@Value("${server.port}") String port) {
		return "User ������ �⺻ ���� Port: {"+port+"}";
	}
}
