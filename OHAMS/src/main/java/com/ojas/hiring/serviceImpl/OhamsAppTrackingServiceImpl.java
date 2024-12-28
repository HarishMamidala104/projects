package com.ojas.hiring.serviceImpl;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.UserDTO;
import com.ojas.hiring.entity.OhamsAppTracking;
import com.ojas.hiring.repo.OhamsAppTrackingRepository;
import com.ojas.hiring.service.OhamsAppTrackingService;
import com.ojas.hiring.utils.JwtUtils;

@Service
public class OhamsAppTrackingServiceImpl implements OhamsAppTrackingService {

	@Autowired
	private OhamsAppTrackingRepository ohamsAppTrackingRepository;

	@Override
	public void logUserActivity(String activity, String action, String module, int link, String username) {

		// Print the extracted values
		System.out.println("Preferred Username: " + username);

		OhamsAppTracking tracking = new OhamsAppTracking();
		tracking.setUserId(username);
		System.out.println(tracking.toString());
		tracking.setActivity(activity); // e.g., "Login"
		tracking.setAction(action); // e.g., "Success" or "Failure"
		tracking.setModule(module); // e.g., "Authentication"
		tracking.setLink(link);
		tracking.setDate(new Date(new java.util.Date().getTime()));
		System.out.println(tracking.toString());
		ohamsAppTrackingRepository.save(tracking);

	}

}
