package com.wellness.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellness.entities.PresentAddress;
import com.wellness.entities.Roles;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDetailsDTO {

	private Long userId;
	
	@Pattern(regexp = "^[a-zA-Z]*$", message = " name must not contain numbers or special characters")
	private String name;

	private String email;

	private String mobile;

	private int age;

	private String gender;

	@JsonIgnore
	private List<SubscriberDetailsDTO> subscriptionDetails = new ArrayList<SubscriberDetailsDTO>();

	private PresentAddress presentAddress;
	
	private Roles roles;

}
