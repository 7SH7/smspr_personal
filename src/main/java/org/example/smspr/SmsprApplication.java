package org.example.smspr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // 생성, 수정 시간 자동화
@SpringBootApplication
public class SmsprApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsprApplication.class, args);
	}
;}
