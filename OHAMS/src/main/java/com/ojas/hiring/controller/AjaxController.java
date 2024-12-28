package com.ojas.hiring.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.serviceImpl.RRFServiceImpl;

@RestController
@RequestMapping("/api")
public class AjaxController {

	private static final Logger logger = LogManager.getLogger(AjaxController.class);
	@Autowired
	private EntityManager entityManager;

	@RequestMapping("/")
	public String showHomePage() {
		return "index"; // search.html page name to open it
	}

	
	@GetMapping("/generic/{tableName}/{columnName}")
	public @ResponseBody String genericDataPublisher(@PathVariable("tableName") String tableName,
			@PathVariable("columnName") String columnName) {

		return genericPublisher(tableName, columnName);

	}

	public String genericPublisher(String tableName, String columnName) {

		String query = "select distinct(" + columnName + ") from " + tableName + " where " + columnName
				+ " is not null";
		logger.info("/hiring/api/generic --> SQL : " + query);
		List resultList = entityManager.createNativeQuery(query).getResultList();
		JSONObject obj = new JSONObject();
		obj.put("totalRecords", resultList.size());
		for (int i = 0; i < resultList.size(); i++) {
			obj.put("param" + (i + 1), resultList.get(i));
		}

		return obj.toString();

	}

}
