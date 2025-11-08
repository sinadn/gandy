package com.example.gandy;

import com.example.gandy.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseCookie;

import static org.apache.coyote.http11.Constants.A;


@SpringBootApplication
public class GandyApplication extends JwtUtils {

	int s = 0;
	public static void main(String[] args) {
		SpringApplication.run(GandyApplication.class, args);
	ObjectMapper objectMapper =
			new ObjectMapper().registerModule(new JavaTimeModule())
					.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}





}
