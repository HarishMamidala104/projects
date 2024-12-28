package com.ojas.hiring;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;

@SpringBootApplication
@ComponentScan(basePackages = "com.ojas.hiring")
public class OhamsApplication {

	public static void main(String[] args) {
		// System.setProperty("server.servlet.context-path", "/hiring");
		SpringApplication.run(OhamsApplication.class, args);
		
		// generateJSONData();
		// generateJSONDataOpt();
	}

	private static void generateJSONDataOpt() {
		JSONObject obj = new JSONObject();
		JSONObject data = new JSONObject();
		obj.put("totalRecords", 6);
		obj.put("chartType", "bar");
		obj.put("chartTitle", "Some-Title");

		for (int i = 0; i < 3; i++) {
			data.put("K-" + (i + 1), i);
		}
		obj.put("values", data);

		String jsonString = obj.toString();
		System.out.println(jsonString);
	}

	private static void generateJSONData() {
		// TODO Auto-generated method stub
		RRF rrf = new RRF();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(rrf);
			// System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Candidate can = new Candidate();
		try {
			String json = ow.writeValueAsString(can);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    

}