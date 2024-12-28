package com.wellness.subscription.payloads;

import lombok.Data;

@Data
public class UserDetailsDTO {
	private Long userId;
	private String name;
	private String city;
	private String email;
	private String mobile;
}
