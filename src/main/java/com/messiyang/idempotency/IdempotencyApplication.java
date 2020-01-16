package com.messiyang.idempotency;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.messiyang.idempotency.mapper")
@SpringBootApplication
public class IdempotencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdempotencyApplication.class, args);
	}

}
