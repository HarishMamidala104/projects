package com.ojas.hiring.dto;

import lombok.Data;

@Data
public class KeycloakRole {

	private String id;

	private String name;

	private String description;

	private Boolean composite;

	private Boolean clientRole;

	private String containerId;
}
