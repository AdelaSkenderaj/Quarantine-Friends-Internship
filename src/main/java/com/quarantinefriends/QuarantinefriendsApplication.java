package com.quarantinefriends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class QuarantinefriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuarantinefriendsApplication.class, args);
	}

}
