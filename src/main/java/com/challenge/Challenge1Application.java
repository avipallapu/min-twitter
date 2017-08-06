package com.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.challenge.controllers.LoginController;

@SpringBootApplication
public class Challenge1Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Challenge1Application.class, args);
		
		context.getBean(LoginController.class).fillData(); 
	}
}
