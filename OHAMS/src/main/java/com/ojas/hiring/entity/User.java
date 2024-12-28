package com.ojas.hiring.entity;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_dtls")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "firstName")
	private String given_name;
	
	@Column(name = "lastName")
	private String lastName;

	@Column(name = "email")
	private String emailaddress;

	@Column(name = "password")
	private String password;

	@Transient
	private String confirmPassword;

	private long employeeId;
	
	private int visibility;
	
	
	@Transient
	private String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Transient
	private String refreshToken;
	
	@Column(name = "keycloak_id")
	private String keycloakId;
	
	@Transient
	private String Roles;
	

	// Secondary

//	@JsonIgnore
//	@Column(name = "enabled")
//	private boolean enabled;

	public String getKeycloakId() {
		return keycloakId;
	}

	public void setKeycloakId(String keycloakId) {
		this.keycloakId = keycloakId;
	}

	@Column(name = "role")
	private String role;

	@Column(name = "gender")
	private String gender;

	@Column(name = "phoneNumber")
	private String phoneNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

//	public boolean isEnabled() {
//		return enabled;
//	}
//
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}

	public String getRoles() {
		return Roles;
	}

	public void setRoles(String roles) {
		Roles = roles;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public User(int id, String userName, String given_name, String lastName, String emailaddress, String password,
			String confirmPassword, long employeeId, int visibility, String token, String refreshToken,
			String keycloakId, String roles, String role, String gender, String phoneNumber) {
		super();
		this.id = id;
		this.userName = userName;
		this.given_name = given_name;
		this.lastName = lastName;
		this.emailaddress = emailaddress;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.employeeId = employeeId;
		this.visibility = visibility;
		this.token = token;
		this.refreshToken = refreshToken;
		this.keycloakId = keycloakId;
		Roles = roles;
		this.role = role;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
	}

	public User() {
		super();
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String firstName) {
		this.given_name = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", firstName=" + given_name + ", lastName=" + lastName
				+ ", emailaddress=" + emailaddress + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ ", employeeId=" + employeeId + ", visibility=" + visibility + ", token=" + token + ", refreshToken="
				+ refreshToken + ", keycloakId=" + keycloakId + ", Roles=" + Roles + ", role=" + role + ", gender="
				+ gender + ", phoneNumber=" + phoneNumber + "]";
	}
	
}