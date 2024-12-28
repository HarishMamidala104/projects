package com.ojas.hiring.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ojas.hiring.entity.Roles;

import lombok.Data;

@Data
public class KeycloakUser {

	private String id;
	
	private Timestamp createdTimestamp;
	
	private String username;
	
	private Boolean enabled;
	
	private Boolean totp;
	
	private Boolean emailVerified;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private List<String> disableableCredentialTypes;
	
	private List<String> requiredActions;
	
	private Integer notBefore;
	
	private Access access;
	
	private String role;
}
