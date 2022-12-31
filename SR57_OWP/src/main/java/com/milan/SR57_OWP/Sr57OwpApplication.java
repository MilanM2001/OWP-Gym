package com.milan.SR57_OWP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Sr57OwpApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Sr57OwpApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Sr57OwpApplication.class, args);
	}
}
