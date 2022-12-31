package com.milan.SR57_OWP.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class SecondConfiguration implements WebMvcConfigurer {

	@Bean(name = {"appMemory"},
			initMethod = "init", destroyMethod = "destroy")
	public ApplicationMemory getApplicationMemory() {
		return new ApplicationMemory();
	}
	
	public class ApplicationMemory extends HashMap {
		
		@Override
		public String toString() {
			return "ApplicationMemory" + this.hashCode();
		}
		
		public void init() {
			System.out.println("init method called");
		}
		
		public void destroy() {
			System.out.println("destroy method called");
		}
		
	}
	
	@Bean(name = {"messageSource"})
	public ResourceBundleMessageSource messageSource() {
		
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
       	
        source.setBasenames("messages/messages");
        
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding("UTF-8");
        
        source.setDefaultLocale(Locale.ENGLISH);
        return source;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.forLanguageTag("en"));
	    return slr;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("locale");
	    return lci;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
    public ConnectionManager connectionManager() {
		return new ConnectionManager();
	}
	
	public final class ConnectionManager {
		
		@Value("${spring.datasource.driverClassName}")
		private String driverClassName;
		
		@Value("${spring.datasource.url}")
		private String url; 
		
		@Value("${spring.datasource.username}")
		private String username; 
		
		@Value("${spring.datasource.password}")
		private String password; 
		
		private ConnectionManager() {
			super();
		}
		
		private Connection conn = null;	
	    
		public Connection getConnection() {
			if (conn == null) {

				try {
					Class.forName(driverClassName);

					conn = DriverManager.getConnection(
							url, username, password);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return conn;
		}
		
		public void closeConnection(){
			try {
				if(conn != null)
					conn.close();
					conn=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
