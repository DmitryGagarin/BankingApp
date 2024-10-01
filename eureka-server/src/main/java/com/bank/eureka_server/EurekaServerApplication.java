package com.bank.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


// ./mvnw package
// docker build -t <prefix/image_name> #bank/eureka-server-container .
// docker run -p 8761:8761 <prefix/image_name> #bank/eureka-server-container

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
