package com.ojas.hiring.dto;

import com.ojas.hiring.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {

    private int id;
    private String userName;
    private String given_name;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private long employeeId;
    private int visibility;
    private String token;
    private String refreshToken;
    private String keycloakId;
    private String roles;
    private String role;
    private String gender;
    private String phoneNumber;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", given_name='" + given_name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", employeeId=" + employeeId +
                ", visibility=" + visibility +
                ", token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", keycloakId='" + keycloakId + '\'' +
                ", roles='" + roles + '\'' +
                ", role='" + role + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public User toUserEntity() {
        User userEntity = new User();
        userEntity.setId(this.id);
        userEntity.setUserName(this.userName);
        userEntity.setGiven_name(this.given_name);
        userEntity.setLastName(this.lastName);
        userEntity.setEmailaddress(this.email);
        userEntity.setPassword(this.password);
        userEntity.setEmployeeId(this.employeeId);
        userEntity.setVisibility(this.visibility);
        userEntity.setToken(this.token);
        userEntity.setRefreshToken(this.refreshToken);
        userEntity.setKeycloakId(this.keycloakId);
        userEntity.setRoles(this.roles);
        userEntity.setRole(this.role);
        userEntity.setGender(this.gender);
        userEntity.setPhoneNumber(this.phoneNumber);
        // Set other fields as needed

        return userEntity;
    }
}