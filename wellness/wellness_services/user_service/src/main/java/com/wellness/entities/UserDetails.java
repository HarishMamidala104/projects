package com.wellness.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "WELLNESS_USER_DETAILS")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long userId;

	@NotEmpty
	@Size(min = 5, max = 20, message = "First Name must be between 5 and 30 characters long")
	@Pattern(regexp = "^[a-zA-Z]*$", message = " name must not contain numbers or special characters")
	private String name;

	@Size(min = 3, max = 20, message = "City name must be between 3 and 30 characters long")
	@Pattern(regexp = "^[a-zA-Z]*$", message = " name must not contain numbers or special characters")
	@Transient
	private String city;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9.]+@ojas-it[.]com", message = "Format should be xyz@ojas-it.com")
	private String email;

	@NotEmpty
	@Size(min = 10, max = 12, message = "not a valid mobile number")
	@Pattern(regexp = "(0|91)?[6-9][0-9]{9}", message = "enter valid mobile number")
	private String mobile;

	@JsonIgnore
	private String password;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date creationDate = new Date();

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	private Date updateDate = new Date();


	@JsonIgnore
	private int age;

	@JsonIgnore
	private String gender;
	
	@JsonIgnore
	private String image;
	
	@JsonIgnore
	private String otp;
	
	@JsonIgnore
	private long roleId;
	
	@JsonIgnore
	private String designation;
	
}
