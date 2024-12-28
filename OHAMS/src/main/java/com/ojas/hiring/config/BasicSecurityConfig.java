package com.ojas.hiring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ojas.hiring.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Profile("basic")
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	

	// Inmemory Authentication

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// Normal inmemory authentication
////		http.authorizeRequests().antMatchers("/hiring/api/**").permitAll().anyRequest().authenticated().
////		and().formLogin().and().httpBasic().and().logout();

//
//import javax.activation.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	// Inmemory Authentication
//
////	@Override
////	protected void configure(HttpSecurity http) throws Exception {
////		// Normal inmemory authentication
//////		http.authorizeRequests().antMatchers("/hiring/api/**").permitAll().anyRequest().authenticated().
//////		and().formLogin().and().httpBasic().and().logout();
////
////		// if i need role based inmemory authentication
////		http.authorizeRequests().antMatchers("/getAllCandidates").hasRole("USER").antMatchers("/hiring/api/**").hasRole("ADMIN")
////		.anyRequest().authenticated().and().formLogin().and().logout().and().csrf().disable();
////	}
////
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
////	}
////
////	private PasswordEncoder passwordEncoder() {
////		return NoOpPasswordEncoder.getInstance();
////	}
////
////	// Configure the api according to the roles in inmemory Authentication
////	@Bean
////	public InMemoryUserDetailsManager userDetailsService() {
////		UserDetails user = User.withUsername("user").password("Ojas@1525").roles("USER").build();
////		UserDetails admin = User.withUsername("admin").password("Ojas@1525").roles("ADMIN").build();
////		return new InMemoryUserDetailsManager(user, admin);
////	}
//
//	// JDBC Authentication
//

//	@Autowired
//	javax.sql.DataSource dataSource;
//
////	@Autowired
////	public void configuration(AuthenticationManagerBuilder auth) throws Exception {
////		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
////				.usersByUsernameQuery("select username, password, enabled from user where username=?")
////				.authoritiesByUsernameQuery("select username, role from user where username=?");
////	}
//	
////	@Autowired
////	public void configuration(AuthenticationManagerBuilder auth) throws Exception {
////		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
////				.usersByUsernameQuery("select user_name, password,enabled from user_dtls where user_name=?")
////				.authoritiesByUsernameQuery(
////	                    "SELECT user_name, 'ROLE_USER' FROM user_dtls WHERE user_name=?");
////				
////	}
//
//	public void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//        .authorizeRequests()
//            .antMatchers("/hiring/registration").permitAll() // Permit access to this URL
//            .anyRequest().authenticated()
//        .and()
//        .formLogin()
//            .permitAll()
//        .and()
//        .httpBasic()
//        .and()
//        .logout()
//            .permitAll();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//}
//	// Configure the api according to the roles in inmemory Authentication
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.withUsername("user").password("Ojas@1525").roles("USER").build();
//		UserDetails admin = User.withUsername("admin").password("Ojas@1525").roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(user, admin);
//	}

	// JDBC Authentication

//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource)		
//				.usersByUsernameQuery("select user_name, password, enabled from user_dtls where employee_id=?")
//				.authoritiesByUsernameQuery("select user_name, role from user where employee_id=?")
//				.passwordEncoder(passwordEncoder());
//	}

//	@Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails ramesh = User.builder()
//                .username("ramesh")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(ramesh, admin);
//	}

//	@Bean
//	public AuthenticationManager configuration(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
//				.usersByUsernameQuery("select * from user_dtls where employee_id=?")
//				.authoritiesByUsernameQuery("SELECT user_name, role FROM user_dtls WHERE employee_id=?");
//		return auth.build();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;

	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	// autherization
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//				.antMatchers("/registration", "/login").permitAll()
//				.antMatchers("/api/getAllRRFDetails", "/api/postCandidateDetails", "/api/getAllTagDetails",
//						"/api/createInterview","/api/getCandidateDataByInyerviewId","/api/updateInterviewById","/api/getStatus",
//						"/api/getAllInterviewFeedbacks","/api/getAllInterviews","/api/createInterviewFeedback").hasAnyAuthority("ADMIN","TAG_USER")
////				.antMatchers("/api/getAllInterviewFeedbacks","/api/getAllInterviews","/api/createInterviewFeedback").hasAuthority("TAG_USER")
//				.antMatchers("/api/postRRF","/api/getAllInterviewFeedbacks","/api/getAllInterviews","/api/createInterviewFeedback").hasAnyAuthority("ADMIN")
//				.anyRequest().authenticated().and().httpBasic().and().formLogin();
//	}
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.antMatchers("/api/registration", "/api/login","/api/forgotPassword").permitAll()
		.antMatchers("/api/getAllRRFDetails", "/api/postCandidateDetails", "/api/getAllTagDetails",
				"/api/createInterview","/api/getCandidateDataByInyerviewId","/api/updateInterviewById","/api/getAllInterviewFeedbacks",
				"/api/getAllInterviews","/api/createInterviewFeedback","/api/getAssignedRRFDetails",
				"/api/getTagDetailsByEmployeeId","/api/getInterviewListByEmployeeId","/api/getData","api/downloadFile",
				"/api/getInteviewerDetails","/api/getVendorDetails","/api/getAllCustomers",
				"/api/getDataByCustomer","/api/getAllCustomers","/api/checkByEmailId","/api/getAllTagDetailsBYRrfId" ,"/api/getAllInterviewsData","/api/getAllRRFValuesForCandidates")
		.hasAnyAuthority("TAG_EXECUTIVE","ADMIN","USER","TAG_LEAD").antMatchers("/api/postRRF","/api/updateRRF").hasAnyAuthority("TAG_LEAD","ADMIN")
		.antMatchers("/api/assignRRFData","/api/updateCandidateDetails").hasAuthority("TAG_LEAD")
		.antMatchers("/api/getAllInterviewsByUserId").hasAuthority("USER")
		.antMatchers("/api/**").hasAuthority("ADMIN")
		.anyRequest().authenticated().and().httpBasic().and().formLogin().disable();
	}
	
	
	

//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/getAllCandidates").hasRole("ADMIN").antMatchers("/registration", "/login").
//		permitAll().and().formLogin().and().csrf().disable();
//		
//	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return (CorsConfigurationSource) source;
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}