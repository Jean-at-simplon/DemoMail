package com.demomail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@SpringBootApplication
public class DemoMailApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(DemoMailApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoMailApplication.class, args);
		
		logger.info("##### Application démarée ######");
	}
		
	
		
		
	
}
