package com.milan.SR57_OWP.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;

public class InitServletContextInitializer implements ServletContextInitializer {

	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	System.out.println("Initializing context for ServletContextInitializer...");

    	System.out.println("Success ServletContextInitializer!");
    }
	
}
