package com.ojas.hiring.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LocationsDTO {

	private int id;
	@Pattern(regexp = "^[A-Za-z]+(\\s[A-Z][a-zA-Z]+)?$", message = "Location must start with an alphabetic character and can have at most two words, with the second word starting with an uppercase letter. No leading spaces are allowed.")
	@NotEmpty(message = "Location should not be empty")
	@NotNull(message = "Location should not be null")
	private String location;

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
