package com.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.news.util.LoggerUtil;

@SpringBootApplication
public class NewspaperSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewspaperSystemApplication.class, args);
		LoggerUtil.logInfo("running");
	}
	

}
