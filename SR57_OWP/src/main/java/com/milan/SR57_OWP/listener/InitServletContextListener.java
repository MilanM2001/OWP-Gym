package com.milan.SR57_OWP.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event)  {
    	System.out.println("Updating Context ServletContextListener...");
    	
    	System.out.println("Success ServletContextListener!");
    }
    
	public void contextDestroyed(ServletContextEvent event)  { 
    	System.out.println("Deleting Context ServletContextListener...");
    		
    	System.out.println("Success ServletContextListener!");
    }
	
}
