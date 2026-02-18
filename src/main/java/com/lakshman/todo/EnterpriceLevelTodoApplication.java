package com.lakshman.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EnterpriceLevelTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriceLevelTodoApplication.class, args);
	}
}