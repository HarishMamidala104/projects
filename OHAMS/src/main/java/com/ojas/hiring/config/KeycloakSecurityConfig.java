package com.ojas.hiring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile("keycloak")
public class KeycloakSecurityConfig {

	@Autowired
	SessionValidationFilter sessionValidationFilter;

	private final JwtAuthConverter jwtAuthConverter;

	@Autowired
	public KeycloakSecurityConfig(JwtAuthConverter jwtAuthConverter) {
		this.jwtAuthConverter = jwtAuthConverter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll().antMatchers("/api/registration", "/api/login", "/api/forgotPassword").permitAll()
				.antMatchers("/api/getAllRRFDetails", "/api/postCandidateDetails", "/api/getAllTagDetails",
						"/api/createInterview", "/api/getCandidateDataByInyerviewId", "/api/updateInterviewById",
						"/api/getAllInterviewFeedbacks", "/api/getAllInterviews", "/api/createInterviewFeedback",
						"/api/getAssignedRRFDetails", "/api/getTagDetailsByEmployeeId",
						"/api/getInterviewListByEmployeeId", "/api/getData", "/api/getInteviewerDetails",
						"/api/getVendorDetails", "/api/getAllCustomers", "/api/updateInterview",
						"/api/getDataByCustomer", "/api/getAllCustomers", "/api/checkByEmailId", "/api/updateCustomer",
						"/api/updateVendor", "/api/updateTechnology", "/api/getDataByCustomerToDonut",
						"/api/getAllInformationToBar", "/api/getBDMDetails", "/api/getAllTagDetailsBYRrfId",
						"/api/getAllLocationDetails")
				.hasAnyAuthority("TAG_EXECUTIVE", "ADMIN", "USER", "TAG_LEAD")
				.antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

				.antMatchers("/api/postRRF", "/api/v2/postRRF", "/api/updateRRF", "/api/v2/updateRRF",
						"/api/getCandidateById", "/api/v2/postRRF", "/api/getExperienceData",
						"/api/getAllRRFValuesForCandidates", "api/getCandidateDetails", "/api/getAllPrimarySkills",
						"/api/getOwnerDetails", "/api/getAllLocationDetails", "/api/downloadFile", "api/updateRRF",
						"api/details")
				.hasAnyAuthority("TAG_LEAD", "ADMIN")

				.antMatchers("/api/assignRRFData", "/api/updateCandidateDetails").hasAuthority("TAG_LEAD")
				.antMatchers("/api/getAllInterviewsByUserId").hasAuthority("USER").anyRequest().authenticated().and()
				.oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
						.addFilterBefore(sessionValidationFilter, UsernamePasswordAuthenticationFilter.class))
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
