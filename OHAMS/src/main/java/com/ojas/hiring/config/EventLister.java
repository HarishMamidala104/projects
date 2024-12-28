package com.ojas.hiring.config;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EventLister {
	@EventListener
	public void handleUserLogin(AuthenticationSuccessEvent event) {
	    String username = event.getAuthentication().getName();
	 //   SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println("User logged in: " + username);
	}
	
	@EventListener
    public void handleUserLogout(LogoutSuccessEvent event) {
        String username = event.getAuthentication().getName();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
        // Log the logout event
        System.out.println("User logged out: " + username);
    }
}
