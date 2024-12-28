package com.ojas.hiring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ojas.hiring.serviceImpl.KeycloakServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class OhmasSchedular {
	
	@Autowired
	KeycloakServiceImpl keycloakService;

	@Scheduled(cron = "0 * * * * *")
	public void getKeycloakUsers() {
		log.info("Scheduled Job[getKeycloakUsers] has been started............");
		keycloakService.loadKeycloakUsers();
		log.info("Scheduled Job[getKeycloakUsers] has been ended.");
	}
}
