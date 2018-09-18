package com.test.MessageStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.message.controller"})  
@EnableJpaRepositories("com.message.repository")
public class MessageStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageStoreApplication.class, args);
	}
}
