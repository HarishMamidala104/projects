package com.wellness.config;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.wellness.utils.ImageFile;
import com.wellness.utils.ImageNameGenerator;

@Configuration
public class LoginServiceConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return  new RestTemplate();
	}
	
	@Bean
	public ImageNameGenerator imageNameGenerator() {
		return new ImageNameGenerator();
	}
	
	@Bean
	public ImageFile imageFile() {
		return new ImageFile();
	}
}
