package com.project.moviesbattleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MoviesBattleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesBattleApiApplication.class, args);
	}

}
