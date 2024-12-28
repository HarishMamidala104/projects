package com.ojas.hiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "location")
@Component
public class Locations {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Pattern(regexp = "^[A-Za-z]+(\\s[A-Z][a-zA-Z]+)?$", message = "Location must start with an alphabetic character and can have at most two words, with the second word starting with an uppercase letter. No leading spaces are allowed.")
	@NotEmpty(message = "Location should not be empty")
	@NotNull(message = "Location should not be null")
	private String location;

	private int visibility;

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
		if (location != null && !location.isEmpty()) {
			String[] words = location.trim().split("\\s+");
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
			}
			this.location = String.join(" ", words);
		} else {
			this.location = location;
		}
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
}