package com.products.takehome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TakehomeApplication {

	public static void main(String[] args) {
		// spring app.run does the following
		// 1) It picks an ApplicationContext
		// 2) It loads configs from application.properties, 
		// 		fires startup events
		// 3) It builds the application context. That is,
		// 		the container holding every bean. 
		// - The controller, repo, and datasource will live here.
		// 4) Starts an embedded web server (tomcat by default).
		// - app listens on 8080 without needing to install a server
		SpringApplication.run(TakehomeApplication.class, args);
	}

}
