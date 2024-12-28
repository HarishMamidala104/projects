package com.ojas.hiring.controller;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ojas.hiring.entity.HireTable;
import com.ojas.hiring.repo.HireTableRepo;
import com.ojas.hiring.serviceImpl.HireTableServiceImpl;

/*@Author RavindranathGV*/

@RestController
@RequestMapping("/api")
public class HireTableController {
	private static final Logger logger = LogManager.getLogger(HireTableController.class);
	@Autowired
	private HireTableServiceImpl hireTableServiceImpl;
	@Autowired
	private HireTableRepo hireTableRepo;
	@Autowired
	private Validator validator;

	@GetMapping("/getAllHiresInfo")
	public List<HireTable> getAllHiresInfo() {
		return hireTableServiceImpl.getAllInterviewDetails();
	}

	@GetMapping(value = "/getHiresRoundsInfo/{id}/{hireId}")
	public Object getHiresRoundsInfo(@PathVariable("id") String id, @PathVariable("hireId") String hireId) {
		ResponseEntity<Object> getHireDetails = hireTableServiceImpl.findInterviewRounds(id, hireId);
		return new ResponseEntity<Object>(getHireDetails, HttpStatus.OK).getBody();
	}

	@GetMapping(value = "/getHiresInfoById/{hireId}")
	public Object getHiresInfoById(@PathVariable("hireId") String hireId) {
		ResponseEntity<Object> getHireDetails = hireTableServiceImpl.findByInterviewByHireId(hireId);
		return new ResponseEntity<Object>(getHireDetails, HttpStatus.OK).getBody();
	}

	@GetMapping(value = "/getHiresInfoByToken/{id}")
	public Object getHiresInfoByToken(@PathVariable("id") String id) {
		ResponseEntity<Object> getHireDetails = hireTableServiceImpl.findByInterviewByToken(id);
		return new ResponseEntity<Object>(getHireDetails, HttpStatus.OK).getBody();
	}

	@GetMapping(value = "/getHiresInfoByTokenOpt/{id}")
	public Object getHiresInfoByTokenOpt(@PathVariable("id") String id) {
		System.out.println("hello");
		return hireTableServiceImpl.findByInterviewByTokenOpt(id).getBody();
	}

	@PostMapping(path = "/publishHireeDetails")
	public @ResponseBody String publishHireeDetails(@RequestBody HireTable hireTable) {
		String returnMessages = "";
		Set<ConstraintViolation<HireTable>> violations = validator.validate(hireTable);
		for (ConstraintViolation<HireTable> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			// Add JSR-303 errors to BindingResult
			// This allows Spring to display them in view via a FieldError
			returnMessages += message + "<br/>";
			// result.addError(new FieldError("employee", propertyPath, "Invalid " +
			// propertyPath + "(" + message + ")"));
		}

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		hireTable.setPublishedDate(date);
		hireTable.setVisibility(1);
		String jsonString = "";
		if (returnMessages.trim().length() == 0) {
			HireTable save = hireTableRepo.saveAndFlush(hireTable);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(hireTable);
		} else {
			JSONObject obj = new JSONObject();
			JSONArray arr = new JSONArray();

			for (int i = 0; i < 1; i++) {
				obj.put("id", "NULL");
				obj.put("error", returnMessages);
				obj.put("status", "Bad Request");

				arr.put(obj);

				// obj = new JSONObject();
			}

			// jsonString = arr.toString();
			jsonString = obj.toString();
		}

		return jsonString;
	}
}