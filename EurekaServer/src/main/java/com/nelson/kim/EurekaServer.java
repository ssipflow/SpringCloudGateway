package com.nelson.kim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.Scheduled;

import com.nelson.kim.service.RegisterExecutor;

/**
 * Eureka Server
 * @author Nelson Kim
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {
	
	/**
	 * Service Register Using Thread
	 */
	@Autowired
	private RegisterExecutor registerExecutor;
	
	@Scheduled(fixedDelay = 1000*20, initialDelay = 1000)
	public void serviceRegister() {
		try {
			registerExecutor.registerExecute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaServer.class, args);
	}
}
