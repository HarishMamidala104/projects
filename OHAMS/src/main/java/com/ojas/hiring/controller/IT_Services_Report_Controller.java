package com.ojas.hiring.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.persistence.EntityManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class IT_Services_Report_Controller {

	@Autowired
	private EntityManager entityManager;
	@Value("${app.itstorage}")
	private String itStorage;
	@Value("${app.rootstorage}")
	private String appRootStorage;

	@RequestMapping(value = "/createITServicesReport", method = RequestMethod.POST)
	public @ResponseBody String createITServicesReport(@RequestParam("file") MultipartFile[] excelFile)
			throws Exception, IOException {

		System.out.println("Checking...");

		String jsonString = "";

		java.util.Date d = new java.util.Date();
		String s = new SimpleDateFormat("dd_HH_mm_ss_").format(d).replaceAll("-", "_").replaceAll(":", "_");
		String dirPostFixName = new SimpleDateFormat("MMM_yyyy").format(d).replaceAll("-", "_").replaceAll(":", "_");
		// process file

		String tableName = dirPostFixName;
		String fileName = itStorage + dirPostFixName;
		File dir = new File(appRootStorage, fileName);
		System.out.println(dir.getAbsolutePath());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		for (int i = 0; i < excelFile.length; i++) {
			long bytes = excelFile[i].getSize();
			double kilobytes = (bytes / 1024);
			double megabytes = (kilobytes / 1024);

			String mimeType = excelFile[i].getContentType();

			File f3 = new File(dir, s + excelFile[i].getOriginalFilename());
			fileName += "/" + f3.getName();

			/*
			 * if mime type ends with mp4 it will allow declared size normal files also
			 * allow the declared size
			 */

			excelFile[i].transferTo(f3);
			System.out.println(f3.getAbsolutePath() + "---KB-----" + kilobytes + "-------------"
					+ excelFile[i].getOriginalFilename());

			// return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message");
			JSONObject obj = new JSONObject();
			JSONArray arr = new JSONArray();

			obj.put("id", "NULL");
			obj.put("msg", "Successfully Uploaded");
			obj.put("status", "Request Allowed");

			arr.put(obj);

			obj = new JSONObject();

			jsonString = obj.toString();
		}

		return jsonString;

	}
}
