package com.ojas.hiring.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.service.LogoutService;

@Service
public class LogoutServiceImpl implements LogoutService {

	@Autowired
	private KeycloakServiceImpl keycloakService;

	@Override
	public boolean logout(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		if (keycloakService.isTokenExpired(token)) {
			return true; // Token is already expired, consider it as logged out
		}

		return true;
	}

}
