package com.ojas.hiring.config;
//
//import java.util.Arrays;
//
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
//import org.springframework.security.web.firewall.RequestRejectedHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
@Configuration
@EnableWebMvc
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// http.cors(); //to enable remote data access
//		//http.cors().and().csrf().disable(); // to enable remote data access & updates
//		 
//		//to support cors use the following
//		http.csrf().disable().cors().configurationSource(corsConfigurationSource());
//
//		// for printing using iframe
//		http.headers().frameOptions().disable();// for different server
//		// http.headers().frameOptions().sameOrigin();// for same server
//		
//		// you can name every provided filter or any specified that is included in the filter chain
//	    http.addFilterAfter(new SessionValidationFilter(), BasicAuthenticationFilter.class);
//	}
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
//
//	@Bean
//	RequestRejectedHandler requestRejectedHandler() {
//		return new HttpStatusRequestRejectedHandler();
//	}
//
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*");
    }
}


