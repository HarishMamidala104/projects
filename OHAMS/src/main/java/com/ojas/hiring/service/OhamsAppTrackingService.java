package com.ojas.hiring.service;



public interface OhamsAppTrackingService {
	public void logUserActivity( String activity, String action, String module, int link, String usename);
}
