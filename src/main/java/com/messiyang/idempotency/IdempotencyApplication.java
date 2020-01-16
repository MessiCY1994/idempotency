package com.messiyang.idempotency;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@MapperScan("com.messiyang.idempotency.mapper")
@SpringBootApplication
@EnableWebMvc
@Configuration
public class IdempotencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdempotencyApplication.class, args);
	}

}
