package com.wellness.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDetailsDTO {
	
	private String name;

	private String mobile;

	private String email;

	private int age;

	private String gender;

	private String addressLineOne;

	private String addressLineTwo;

	private int pincode;
	
	private String addressType;

}
