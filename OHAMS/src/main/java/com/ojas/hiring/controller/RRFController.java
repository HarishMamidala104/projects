package com.ojas.hiring.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.ojas.hiring.exceptions.RrfIdNotFound;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ojas.hiring.Exception.BadRequestException;
import com.ojas.hiring.Exception.InternalServerException;
import com.ojas.hiring.Exception.RestBaseException;
import com.ojas.hiring.ExceptionConstants.ExceptionConstants;
import com.ojas.hiring.entity.AnalyticalDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.entity.Uploads;
import com.ojas.hiring.exceptions.NoRecordFoundException;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.CustomersRepo;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.repo.UploadsRepo;
import com.ojas.hiring.service.RRFService;
import com.ojas.hiring.serviceImpl.RRFServiceImpl;

@RestController
@RequestMapping("/api")
public class RRFController {

	private static final Logger logger = LogManager.getLogger(RRFController.class);

	private final RRFServiceImpl rrfServiceImpl;
	private final RRFRepo rrfRepo;
	private final CustomersRepo customersRepo;
	private final RRFService rrfService;
	private final UploadsRepo uploadsRepo;
	private final Validator validator;
	private final CandidateRepo candidateRepo;
	private final ObjectMapper mapper;

	@Autowired
	public RRFController(RRFServiceImpl rrfServiceImpl, RRFRepo rrfRepo, CustomersRepo customersRepo,
			RRFService rrfService, UploadsRepo uploadsRepo, Validator validator, EntityManager entityManager,
			CandidateRepo candidateRepo,ObjectMapper mapper) {
		this.rrfServiceImpl = rrfServiceImpl;
		this.rrfRepo = rrfRepo;
		this.customersRepo = customersRepo;
		this.rrfService = rrfService;
		this.uploadsRepo = uploadsRepo;
		this.validator = validator;
		this.candidateRepo = candidateRepo;
		this.mapper = mapper;
	}

//	@PostMapping("/postRRFDetails")
//	public RRF postRRF(@RequestBody RRF rrf) {
//		RRF save = rrfRepo.save(rrf);
//		return save;
//
//	}

	@GetMapping("/aggregate-data")
	public ResponseEntity<?> getAggregatedInterviewData(@RequestParam(required = false) String technology,
			@RequestParam(required = false, name = "customerName") String customer,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		logger.debug("Start of getAggregatedInterviewData");
		try {
			List<AnalyticalDto> responseList = rrfServiceImpl.fetchAggregatedInterviewData(technology, customer,
					startDate, endDate);

			if (CollectionUtils.isEmpty(responseList)) {
				logger.debug("End of getAggregatedInterviewData");
				return new ResponseEntity<>("No data found for the specified criteria.", HttpStatus.NOT_FOUND);
			}
			logger.debug("End of getAggregatedInterviewData");
			return new ResponseEntity<>(responseList, HttpStatus.OK);
		} catch (RestBaseException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(ExceptionConstants.GET_AGGREGATED_INTERVIEW_DATA, "Exception occured at getAggregatedInterviewData", "Error at getAggregatedInterviewData");
		}
	}

	@PostMapping(value = "/postRRF")
	public String postRRFDetails(@RequestParam Map<String, String> parameters,
			@RequestParam(value = "rrqDoc", required = false) MultipartFile[] rrqDoc, HttpServletRequest request) {
		logger.debug("start of postRRFDetails  <======> /hiring/api/postRRF --> Processing FormData and Uploaded Files");

		String jsonString = null;

		try {
			RRF rrf = new RRF();
			rrf.setIpAddress(request.getRemoteAddr());
			rrf = rrfServiceImpl.inputParameters(parameters);
			if (ObjectUtils.isEmpty(rrf)) {
				throw new BadRequestException(ExceptionConstants.POST_RRF_DETAILS, "RRF data is null/Empty", "RRF object creation failed.");//need to pass args
			}

			logger.debug("/hiring/api/postRRF --> RRF Data is: {}", mapper.convertValue(rrf, JsonNode.class));

			// Process the RRF and handle file uploads
			jsonString = rrfServiceImpl.processRrfAndFiles(rrf, rrqDoc);

		} catch (RestBaseException e) {
			throw e;
		} catch (Exception e) {
			logger.error("/hiring/api/postRRF --> Error processing RRF details: ", e);
			jsonString = new JSONObject().put("status", "Error processing RRF: " + e.getMessage()).toString();
		}
		logger.debug("End of postRRFDetails");
		logger.debug("/hiring/api/postRRF --> Result: {}", mapper.convertValue(jsonString, JsonNode.class));
		return jsonString;
	}

	@GetMapping("/showMoreData")
	public ResponseEntity getMorePSAData(@RequestParam("id") long id) {
		Optional<RRF> byId = rrfRepo.findById(id);
		if (byId.isPresent()) {
			String experience = byId.get().getExperience();
			String[] split = experience.split("-");
			int[] results = new int[2];
			int minExp = Integer.parseInt(split[0].trim());
			int maxExp = Integer.parseInt(split[1].trim());
			String technology = byId.get().getPrimarySkills();
			List<Object[]> rmgDetails = rrfRepo.getRMGDetailsForShowMore(minExp, maxExp, technology);
			List<Integer> candidatesByRRFId = candidateRepo.getCandidatesByRRFId(id);
			List<Map<String, String>> resultList = new ArrayList<>();
			for (Object[] objects : rmgDetails) {
				Map<String, String> map = new LinkedHashMap();
				int candidateId = (int) objects[0];
				if (!candidatesByRRFId.contains(candidateId)) {
					map.put("id", (objects[0].toString()));
					map.put("name", objects[1].toString());
					map.put("technology", objects[2].toString());
					map.put("experience", objects[3].toString());
					resultList.add(map);
				}
			}

			return new ResponseEntity(resultList, HttpStatus.OK);
		} else {
			throw new RrfIdNotFound("Invalid RRFID");
		}

	}

	@RequestMapping(value = "/updateRRF", method = RequestMethod.PUT)
	public @ResponseBody String updateRRFDetails(@RequestParam Map<String, String> parameters,
			@RequestParam("id") String rrf_id, @RequestParam(value = "rrqDoc", required = false) MultipartFile[] rrqDoc,
			HttpServletRequest request) throws Exception, IOException {

		logger.info("/hiring/api/updateRRF --> Processing FormData and Uploaded Files");

		String id = null;
		String createdBy = null;
		double minExp = 0.0;
		double maxExp = 0.0;
		String location = null;
		double budget = 0.0;
		String interviewModes = null;
		String primarySkills = null;
		String secondarySkills = null;
		String jobDescription = null;
		String jobLevel = null;

		int openPositions = 0;
		int closedPositions = 0;
		String customerName = null;
		String customerDetails = null;
		String hiringType = null;
		String hiringObjective = null;
		String jobTitle = null;
		Uploads[] attachments = null;
		Date publishedOn = null;
		int visibility = 1;
		String jobType = null;
		String priority = null;
		String modeOfWork = null;
		String title = null;
		String ownerOfRequirement = null;
		String city = null;
		String country = null;
		String state = null;
		int totalPositions = 0;
		String experience = null;
		String requirementName = null;
		String requirementStatus = null;
		RRF byId = null;
		if (rrf_id != null) {
			try {
				long parseLong = Long.parseLong(rrf_id);
				byId = rrfRepo.getByRRFDataById(parseLong);
				if (byId == null) {
					throw new RrfIdNotFound("RRF ID not found: " + rrf_id);
				}
			} catch (NumberFormatException ex) {
				throw new RrfIdNotFound("Invalid RRF ID format");
			}
		}

//		RRF byId = rrfRepo.getById(Long.parseLong(id));
//		byId.set

//		Optional<RRF> findById = rrfRepo.findById(Long.parseLong(rrf_id));
//		if(findById.isPresent()) {
//			byId = findById.get();

		for (Map.Entry<String, String> pair : parameters.entrySet()) {
			System.out.println(String.format("Key (name) is: %s, Value is : %s", pair.getKey(), pair.getValue()));
			if (pair.getKey().equals("id")) {
				try {
					id = pair.getValue();
					continue;
				} catch (Exception e) {
					id = null;
					continue;
				}
			}
			if (pair.getKey().equals("createdBy")) {
				try {
					createdBy = pair.getValue();
					byId.setCreatedBy(createdBy);
					continue;
				} catch (Exception e) {
					createdBy = null;
					continue;
				}
			}
			if (pair.getKey().equals("minExp")) {
				try {
					minExp = Double.parseDouble(pair.getValue());
					byId.setMinExp(maxExp);
					continue;
				} catch (Exception e) {
					minExp = 0.0;
					continue;
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			if (pair.getKey().equals("publishedOn")) {
				try {
					publishedOn = sdf.parse(pair.getValue());
					System.out.println("published" + publishedOn);
					byId.setPublishedOn(publishedOn);
					System.out.println("The published on property : this from the DB " + byId.getPublishedOn());
					continue;
				} catch (Exception e) {
					publishedOn = byId.getPublishedOn();
					System.out.println("the updated date is " + byId.getPublishedOn() + "this is updated database");
					continue;
				}
			}
			if (pair.getKey().equals("maxExp")) {
				try {
					maxExp = Double.parseDouble(pair.getValue());
					byId.setMaxExp(maxExp);
					continue;
				} catch (Exception e) {
					maxExp = 0.0;
					continue;
				}
			}
			if (pair.getKey().equals("budget")) {
				try {
					budget = Double.parseDouble(pair.getValue());
					byId.setBudget(budget);
					continue;
				} catch (Exception e) {
					budget = 0.0;
					continue;
				}
			}
			if (pair.getKey().equals("location")) {
				try {
					location = (pair.getValue());
					byId.setLocation(location);
					continue;
				} catch (Exception e) {
					location = null;
					continue;
				}
			}
//			if (pair.getKey().equals("interviewModes")) {
//				try {
//					interviewModes = (pair.getValue());
//					byId.setInterviewModes(interviewModes);
//					continue;
//				} catch (Exception e) {
//					interviewModes = null;
//					continue;
//				}
//			}
			if (pair.getKey().equals("primarySkills")) {
				try {
					primarySkills = (pair.getValue());
					byId.setPrimarySkills(primarySkills);
					continue;
				} catch (Exception e) {
					primarySkills = null;
					continue;
				}
			}
			if (pair.getKey().equals("secondarySkills")) {
				try {
					secondarySkills = (pair.getValue());
					byId.setSecondarySkills(secondarySkills);
					continue;
				} catch (Exception e) {
					secondarySkills = null;
					continue;
				}
			}
			if (pair.getKey().equals("jobLevel")) {
				try {
					jobLevel = (pair.getValue());
					byId.setJobLevel(jobLevel);
					continue;
				} catch (Exception e) {
					jobDescription = null;
					continue;
				}
			}
			if (pair.getKey().equals("jobDescription")) {
				try {
					jobDescription = (pair.getValue());
					byId.setJobDescription(jobDescription);
					continue;
				} catch (Exception e) {
					jobDescription = null;
					continue;
				}
			}
			if (pair.getKey().equals("requirementName")) {
				try {
					requirementName = (pair.getValue());
					byId.setRequirementName(requirementName);
					continue;
				} catch (Exception e) {
					requirementName = "Not-Specified";
					continue;

				}
			}
			if (pair.getKey().equals("customerName")) {
				try {
					customerName = (pair.getValue());
					byId.setCustomerName(customerName);
					continue;
				} catch (Exception e) {
					customerName = "Not-Specified";
					continue;
				}
			}
//			if (pair.getKey().equals("customerDetails")) {
//				try {
//					customerDetails = (pair.getValue());
//					byId.setCusto   (customerDetails);
//					continue;
//				} catch (Exception e) {
//					customerDetails = "Not-Specified";
//					continue;
//				}
//			}
			if (pair.getKey().equals("hiringType")) {
				try {
					hiringType = (pair.getValue());
					byId.setHiringType(hiringType);
					continue;
				} catch (Exception e) {
					hiringType = "Not-Specified";
					continue;
				}
			}
			if (pair.getKey().equals("experience")) {
				try {
					experience = (pair.getValue());
					byId.setExperience(experience);
					continue;
				} catch (Exception e) {
					hiringObjective = "Not-Specified";
					continue;
				}
			}
			if (pair.getKey().equals("jobTitle")) {
				try {
					jobTitle = (pair.getValue());
					byId.setJobTitle(jobTitle);
					continue;
				} catch (Exception e) {
					jobTitle = "Not-Specified";
					continue;
				}
			}
//			if (pair.getKey().equals("openPositions")) {
//				try {
//					openPositions = Integer.parseInt(pair.getValue());
//					byId.setOpenPositions(openPositions);
//					continue;
//				} catch (Exception e) {
//					openPositions = 0;
//					continue;
//				}
//			}
//			if (pair.getKey().equals("closedPositions")) {
//				try {
//					closedPositions = Integer.parseInt(pair.getValue());
//					byId.setClosedPositions(closedPositions);
//					continue;
//				} catch (Exception e) {
//					closedPositions = 0;
//					continue;
//				}
//			}
			if (pair.getKey().equals("openPositions")) {
				try {
					openPositions = Integer.parseInt(pair.getValue());
					byId.setOpenPositions(openPositions);
					continue;
				} catch (Exception e) {
					openPositions = 0;
					continue;
				}
			}

			// Continue handling other keys...

			if (pair.getKey().equals("totalPositions")) {
				try {
					totalPositions = Integer.parseInt(pair.getValue());
					byId.setTotalPositions(totalPositions);
					// If closedPositions is 0, set openPositions to totalPositions
					if (closedPositions == 0) {
						byId.setOpenPositions(totalPositions);
					}
					continue;
				} catch (Exception e) {
					totalPositions = 0;
					continue;
				}
			}

			if (pair.getKey().equals("closedPositions")) {
				try {
					closedPositions = Integer.parseInt(pair.getValue());
					byId.setClosedPositions(closedPositions);
					// Adjust openPositions based on closedPositions and totalPositions

					byId.setOpenPositions(totalPositions - closedPositions);

					continue;
				} catch (Exception e) {
					closedPositions = 0;
					continue;
				}
			}

			if (pair.getKey().equals("jobType")) {
				try {
					jobType = pair.getValue();
					byId.setJobType(jobType);
					continue;
				} catch (Exception e) {
					jobType = "Not-Specified";
					continue;
				}
			}

			if (pair.getKey().equals("priority")) {
				try {
					priority = pair.getValue();
					byId.setPriority(priority);
					continue;
				} catch (Exception e) {
					priority = "Not-Specified";
					continue;
				}
			}

			if (pair.getKey().equals("modeOfWork")) {
				try {
					modeOfWork = pair.getValue();
					byId.setModeOfWork(modeOfWork);
					continue;
				} catch (Exception e) {
					modeOfWork = "Not-Specified";
					continue;
				}
			}

			if (pair.getKey().equals("title")) {
				try {
					title = pair.getValue();
					byId.setTitle(title);
					continue;
				} catch (Exception e) {
					title = "Not-Specified";
					continue;
				}
			}

			if (pair.getKey().equals("ownerOfRequirement")) {
				try {
					ownerOfRequirement = pair.getValue();
					byId.setOwnerOfRequirement(ownerOfRequirement);
					continue;
				} catch (Exception e) {
					ownerOfRequirement = "Not-Specified";
					continue;
				}
			}
			if (pair.getKey().equals("city")) {
				try {
					city = pair.getValue();
					byId.setCity(city);
					continue;
				} catch (Exception e) {
					city = "Not-Specified";
					continue;
				}
			}

			if (pair.getKey().equals("country")) {
				try {
					country = pair.getValue();

					continue;
				} catch (Exception e) {
					country = "Not-Specified";
					continue;
				}
			}

			if (pair.getKey().equals("state")) {
				try {
					state = pair.getValue();
					byId.setState(state);
					continue;
				} catch (Exception e) {
					state = "Not-Specified";
					continue;
				}
			}

			if (pair.getKey().equals("requirementStatus")) {
				try {
					requirementStatus = pair.getValue();
					byId.setRequirementStatus(requirementStatus);
					continue;
				} catch (Exception e) {
					createdBy = null;
					continue;
				}
			}

//			if (pair.getKey().equals("totalPositions")) {
//				try {
//					totalPositions = Integer.parseInt(pair.getValue());
//					byId.setTotalPositions(totalPositions);
//					continue;
//				} catch (Exception e) {
//					totalPositions = 0;
//					continue;
//				}
//			}

		}

//		}
//--------------------------------------------
//		String jsonString = "";
//
//		RRF save = rrfRepo.saveAndFlush(byId);
//		RRF save = rrfRepo.save(findById);
//		JSONObject obj = new JSONObject();
//		obj.put("status", "RRF Successfully Created");
//---------------
//		if (id == null) {
//			JSONObject obj = new JSONObject();
//			obj.put("status", "RRF Not Updated,Specify RRF ID");
//			jsonString = obj.toString();
//		} else {
//			RRF byId = rrfRepo.getById(Long.parseLong(id));
//			byId.set

//			RRF rrf = new RRF();
//			rrf.setId(Long.parseLong(id));
//			rrf.setCreatedBy(createdBy);
//			rrf.setCustomerName(customerName);
//			rrf.setCustomerDetails(customerDetails);
//			rrf.setInterviewModes(interviewModes);
//			rrf.setBudget(budget);
//			rrf.setLocation(location);
//			rrf.setMinExp(minExp);
//			rrf.setMaxExp(maxExp);
//			rrf.setClosedPositions(closedPositions);
//			rrf.setOpenPositions(openPositions);
//			rrf.setJobTitle(jobTitle);
//			rrf.setJobDescription(jobDescription);
//			rrf.setJobLevel(jobLevel);
//			rrf.setHiringType(hiringType);
//			rrf.setHiringObjective(hiringObjective);
//			rrf.setPrimarySkills(primarySkills);
//			rrf.setSecondarySkills(secondarySkills);
//			rrf.setVisibility(1);
//			long millis = System.currentTimeMillis();
//			java.sql.Date date = new java.sql.Date(millis);
//			rrf.setPublishedOn(date);
//
//			rrf.setIpAddress(request.getRemoteAddr());
//
//			logger.info("/hiring/api/postRRF --> RRF Data  : " + rrf.toString());
//
//			int dd = 9;
//			if(dd==9) {
//				JSONObject obj = new JSONObject();
//				obj.put("status", "RRF Not Created=BlahBlah" );
//				jsonString = obj.toString();
//				return jsonString;
//			}
//
//			String returnMessages = "";
//			Set<ConstraintViolation<RRF>> violations = validator.validate(rrf);
//			for (ConstraintViolation<RRF> violation : violations) {
//				String propertyPath = violation.getPropertyPath().toString();
//				String message = violation.getMessage();
//				// Add JSR-303 errors to BindingResult
//				// This allows Spring to display them in view via a FieldError
//				// returnMessages += message + "<br/>";
//				// result.addError(new FieldError("employee", propertyPath, "Invalid " +
//				// propertyPath + "(" + message + ")"));
//			}
//
//			if (returnMessages.trim().length() == 0) {
//				logger.info("--------RRF Update Entry ----------");
//				java.util.Date d = new java.util.Date();
//				String s = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(d).replaceAll("-", "_").replaceAll(":",
//						"_");
//
//				// rrf.setAttachments(uploads);
//				// end of file upload
//
//				System.out.println(rrf);
//				RRF saved = rrfRepo.saveAndFlush(rrf);
//
		String jsonString = "";

		if (id == null) {
			JSONObject obj = new JSONObject();
			obj.put("status", "RRF Not Updated,Specify RRF ID");
			jsonString = obj.toString();
		} else {

			RRF rrf = new RRF();
			rrf.setId(Long.parseLong(id));
			rrf.setCreatedBy(createdBy);
			rrf.setCustomerName(customerName);
			rrf.setBudget(budget);
//		rrf.setMinExp(minExp);
//		rrf.setMaxExp(maxExp);
			rrf.setExperience(experience);
			rrf.setJobTitle(jobTitle);
			rrf.setLocation(location);
			rrf.setClosedPositions(closedPositions);
			rrf.setOpenPositions(openPositions);
			rrf.setJobDescription(jobDescription);
			rrf.setJobLevel(jobLevel);
			rrf.setHiringType(hiringType);
			rrf.setPrimarySkills(primarySkills);

			rrf.setSecondarySkills(secondarySkills);
			rrf.setVisibility(1);
			rrf.setJobType(jobType);
			rrf.setPriority(priority);
			rrf.setModeOfWork(modeOfWork);
			rrf.setTitle(title);
			rrf.setRequirementStatus(requirementStatus);// ---------------------------------------
			rrf.setOwnerOfRequirement(ownerOfRequirement);
			rrf.setCity(city);
//		rrf.setCountry(country);
			rrf.setState(state);
			rrf.setTotalPositions(totalPositions);
//		long millis = System.currentTimeMillis();
//		java.sql.Date date = new java.sql.Date(millis);
			System.out.println("______________________________ " + publishedOn + "-----------------------");
			rrf.setPublishedOn(publishedOn);
			System.out.println(" --------------------------------" + rrf.getPublishedOn() + "-----------------------");
//		rrf.setSheduledOn(sheduledOn);

			rrf.setIpAddress(request.getRemoteAddr());

			logger.info("/hiring/api/postRRF --> RRF Data  : " + rrf.toString());

			String returnMessages = "";
			Set<ConstraintViolation<RRF>> violations = validator.validate(rrf);
			for (ConstraintViolation<RRF> violation : violations) {
				String propertyPath = violation.getPropertyPath().toString();
				String message = violation.getMessage();
				// Add JSR-303 errors to BindingResult
				// This allows Spring to display them in view via a FieldError
				// returnMessages += message + "<br/>";
				// result.addError(new FieldError("employee", propertyPath, "Invalid " +
				// propertyPath + "(" + message + ")"));
			}
			// String s=null;
			if (returnMessages.trim().length() == 0) {
				logger.info("--------RRF Entry ----------");
				java.util.Date d = new java.util.Date();
				String s = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(d).replaceAll("-", "_").replaceAll(":",
						"_");

				// Save the RRF entry
				RRF saved = rrfRepo.saveAndFlush(rrf);
				long id2 = saved.getId();
				rrf.setRequirementName(requirementName);
				RRF savedEntity = rrfRepo.save(rrf);

				// Check if the Upload record exists by link
				Optional<Uploads> byLink = uploadsRepo.findByLink(id2);

				// If record exists, update it
				if (byLink.isPresent()) {
					Uploads upload = byLink.get(); // Get the Uploads object

					if (rrqDoc != null && rrqDoc.length > 0) {
						List<Uploads> uploads = new ArrayList<>();

						for (MultipartFile fileDoc : rrqDoc) {
							long bytes = fileDoc.getSize();
							double kilobytes = bytes / 1024.0;

							if (bytes > 0) {
								/*
								 * String fileName = fileDoc.getOriginalFilename(); String fileExtension =
								 * FilenameUtils.getExtension(fileName); byte[] fileContent =
								 * fileDoc.getBytes();
								 */
								String mimeType = fileDoc.getContentType();
								String fileName = fileDoc.getOriginalFilename();
								String fileExtension = FilenameUtils.getExtension(fileName);
								byte[] fileContent = fileDoc.getBytes();
								// Update existing record details
								// Uploads file = new Uploads(upload.getUploadId(), fileContent, fileName,
								// fileExtension, kilobytes, s, "RRF", upload.getLink());

								upload.setFileName(fileName);
								upload.setExtension(fileExtension);
								upload.setFileSize(kilobytes);
								upload.setLink(upload.getLink()); // Make sure 'saved' is initialized
																	// System.out.println(upload);
								upload.setUploadedDate(s);
								// upload.setUploadId(id2);
								// uploads.add(upload);
							}
						}
						// if(upload.getUploadId()!=0)
						uploadsRepo.save(upload);
						// Save updated files
						// for(Uploads u : uploads)
						{
							// if(u.getUploadId()==upload.getUploadId())

							// uploadsRepo.updateUpload(upload.getFileName(),upload.getExtension(),upload.getLink(),
							// id2, upload.getUploadedDate(),upload.getUploadId());
						}
					}

					// Return success message for update
					JSONObject obj = new JSONObject();
					obj.put("status", "RRF Successfully Updated");
					jsonString = obj.toString();

				} else {
					// Record doesn't exist, save a new one
					if (rrqDoc != null && rrqDoc.length > 0) {
						List<Uploads> uploads = new ArrayList<>();

						for (MultipartFile fileDoc : rrqDoc) {
							long bytes = fileDoc.getSize();
							double kilobytes = bytes / 1024.0;

							if (bytes > 0) {
								String fileName = fileDoc.getOriginalFilename();
								String fileExtension = FilenameUtils.getExtension(fileName);
								byte[] fileContent = fileDoc.getBytes();

								// Create new Uploads record
								Uploads file = new Uploads(0L, fileContent, fileName, fileExtension, kilobytes, s,
										"RRF", rrf.getId());
								file.setLink(saved.getId()); // Ensure 'saved' is initialized
								uploads.add(file);
							}
						}

						// Save new files
						uploadsRepo.saveAll(uploads);

						// Return success message for new record
						JSONObject obj = new JSONObject();
						obj.put("status", "RRF Successfully Saved");
						jsonString = obj.toString();
					}
				}

				// Fallback if no valid files were processed
				if (jsonString == null) {
					JSONObject obj = new JSONObject();
					obj.put("status", "RRF Not Created: " + returnMessages);
					jsonString = obj.toString();
				}
			}
			// Log the result
			logger.info("/hiring/api/postRRF --> Result : " + jsonString);
		}

		return jsonString;
	}
	// @GetMapping(path = "/getAllRRFDetails", produces =
	// MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	public @ResponseBody String getAllRRFDetails(HttpServletRequest request) {
//		// List<RRF> rrfData = rrfServiceImpl.getAllRRFDetails();
//		List<RRF> rrfData = rrfRepo.findAll(Sort.by(Sort.Direction.DESC, "publishedOn"));
//		rrfData = getOpenAndClosedPositions(rrfData);
//		ObjectMapper mapper = new ObjectMapper();
////		.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
//		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		String jsonString = null;
//		try {
//			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rrfData);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.info("/hiring/api/getAllRRFDetails --> Result : Sending " + rrfData.size() + " records");
//		return jsonString;
//	}

	@GetMapping(path = "/getRRFDetails/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody String getRRFDetails(@PathVariable("id") long id) {
		Optional<RRF> rrfData = rrfRepo.findById(id);
		RRF rrf = rrfData.get();
		int openPositions = rrf.getOpenPositions();
		int closedPositions = rrf.getClosedPositions();
		Candidate byRrfId = candidateRepo.getByRrfId(id);
		Optional<Candidate> candidateData = candidateRepo.getByCidStatusANDSubStatus(byRrfId.getCid());
//        if(candidateData.isPresent()) {
//        	int openedPostions = --openPositions;
//        	int closePositions = ++closedPositions;
//        	rrf.setOpenPositions(openedPostions);
//        	rrf.setClosedPositions(closePositions);
//        }
		rrfRepo.save(rrf);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rrfData.get());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("/hiring/api/getRRFDetails/" + id + " --> Result : " + jsonString);
		return jsonString;
	}

	@GetMapping(path = "/getRRFUploadDetails/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody String getRRFUploadDetails(@PathVariable("id") int id) {
		String uploadsData = rrfServiceImpl.findUploadsByID("data_uploads", "upload_id,file_name", id);
		System.out.println(uploadsData);
		// Gson gson = new Gson();
		// String json = gson.toJson(uploadsData);
		return uploadsData;
	}

	@GetMapping("/downloadFile/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable long id, HttpServletResponse resp) throws Exception {
		Optional<Uploads> filedetails = uploadsRepo.findById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
		resp.setHeader("Content-Disposition", "attachment; filename=" + filedetails.get().getFileName());

		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(filedetails.get().getUploadedFile(), headers,
				HttpStatus.OK);

		return response;
	}

//	@PutMapping("/updateRRF")
//	public RRF updateRRF(@RequestParam("id")long id,@RequestBody RRF rrf) throws Exception {
//		RRF updateRRF = rrfService.updateRRF(id,rrf);
//		return updateRRF;
//
//	}

	@DeleteMapping("/deleteByid/{id}")
	public String deleteRecord(@PathVariable("id") long id) {
		RRF byId = rrfRepo.getById(id);
		byId.setVisibility(0);
		List<Candidate> candidate = byId.getCandidate();
		for (int i = 0; i < candidate.size(); i++) {
			Candidate candidate2 = candidate.get(i);
			candidate2.setVisibility(0);
		}
		RRF save = rrfRepo.save(byId);
//		

//		 RRF byId = rrfRepo.getById(id);
//		    if (byId == null) {
//		        // Handle the case where the RRF with the given ID is not found
//		        return "RRF not found";
//		    }
//
//		    byId.setVisibility(0);
//		    List<Candidate> candidates = byId.getCandidate();
//		    if (candidates != null) {
//		        for (int i = 0; i < candidates.size(); i++) {
//		            Candidate candidate = candidates.get(i);
//		            if (candidate != null) {
//		                candidate.setVisibility(0);
//		            }
//		        }
//		    }

		return "Deleted Successfully";
	}

	@GetMapping("/getAllRRFDetails")
	public List<Map<String, Object>> getRRFDetails() {
		List<Map<String, Object>> rrfByVisibility = rrfService.getRRFByVisibility();

		return rrfByVisibility;
	}

	@GetMapping("/getAssignedRRFDetails")
	public List<Map<String, Object>> getAssignedRRFDetails(@RequestParam("employeeId") long employeeId) {
		List<Map<String, Object>> assignedRRF = rrfService.getAssignedRRF(employeeId);
		return assignedRRF;
	}

	private List<RRF> getOpenAndClosedPositions(List<RRF> rrfList) {
		List<RRF> totalRrf = new ArrayList<RRF>();
		for (RRF rrf : rrfList) {
			List<Candidate> candidate = rrf.getCandidate();
			if (candidate != null) {
				int closedPositions = getClosedPositions(candidate);
				int size = candidate.size();
				rrf.setOpenPositions(size);
				rrf.setClosedPositions(closedPositions);
				totalRrf.add(rrf);
			} else {
				rrf.setOpenPositions(0);
				totalRrf.add(rrf);
			}
		}
		return totalRrf;
	}

	private int getClosedPositions(List<Candidate> candidate) {
		int closedPositions = 0;
		for (Candidate can : candidate) {
			if (can.getStatus().equalsIgnoreCase("CLOSED")) {
				closedPositions++;
			}
		}
		return closedPositions;
	}

	@GetMapping("/getStatus")
	public List<String> getCustomerName() {
		List<String> customerName = rrfRepo.getCustomerName();
		return customerName;

	}

	@GetMapping("/getRRFDetailsByFilter")
	public List<RRF> getRRFDetailsByFilter(
			@RequestParam(value = "primarySkills", required = false) String primarySkills,
			@RequestParam(value = "customerName", required = false) String customerName,
			@RequestParam(value = "hiringType", required = false) String hiringType) {

		List<RRF> details = rrfRepo.getDetails(customerName, primarySkills, hiringType);
		return details;

	}

	@GetMapping("/getAllCustomersByType")
	public List<String> getAllInternalCustomer(@RequestParam("type") String type) {
		List<String> allCustomersByType = customersRepo.getAllInternalCustomers(type);
		return allCustomersByType;

	}

	@GetMapping("/getAllDetails")
	public List<RRF> getAllDetails(@RequestParam(required = false) String requirement_status,
			@RequestParam(required = false) String priority) {
		List<RRF> allDetails = rrfService.getAllDetails(requirement_status, priority);
		return allDetails;

	}

	@GetMapping("/getAllRRFDetailsByRange")
	public List<RRF> getRRFDetailsByRange(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		List<RRF> rrfDetailsByCustomDate = rrfRepo.getRRFDetailsByCustomDate(startDate, endDate);

		return rrfDetailsByCustomDate;
	}

	@GetMapping("/download/RRFData")
	public ResponseEntity<byte[]> download() throws Exception {
		List<RRF> downloadRRFData = rrfRepo.downloadRRFData();
		ObjectMapper objectMapper = new ObjectMapper();
		String xml = objectMapper.writeValueAsString(downloadRRFData);
		byte[] stringtobytes = xml.getBytes();
		String fileName = "file.xml";
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentLength(stringtobytes.length);
		respHeaders.setContentType(MediaType.TEXT_XML);
		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return new ResponseEntity<byte[]>(stringtobytes, respHeaders, HttpStatus.OK);
	}

	@GetMapping("/getRMGDetails")
	public ResponseEntity<Map<String, String>> getRMGDetails(@RequestParam("id") long id) {
//		RRF byId = rrfRepo.getById(id);
		Optional<RRF> byId = rrfRepo.findById(id);//

		if (byId.isPresent()) {
			String experience = byId.get().getExperience();

			int[] experienceRange = parseExperience(experience);

			System.out.println(experienceRange[0] + "\t" + experienceRange[1]);

			String technology = byId.get().getPrimarySkills();

			List<Object[]> rmgDetails = rrfRepo.getRMGDetails(experienceRange[1], experienceRange[0], technology);
			List<Integer> candidatesByRRFId = candidateRepo.getCandidatesByRRFId(id);
			List<Map<String, String>> resultList = new ArrayList<>();
			for (Object[] objects : rmgDetails) {
				Map<String, String> map = new LinkedHashMap();
				int candidateId = (int) objects[0];
				if (!candidatesByRRFId.contains(candidateId)) {
					map.put("id", (objects[0].toString()));
					map.put("name", objects[1].toString());
					map.put("technology", objects[2].toString());
					map.put("experience", objects[3].toString());
					resultList.add(map);
				}
			}
			return new ResponseEntity(resultList, HttpStatus.OK);
		} else {
			throw new RrfIdNotFound("Invalid RRFID");
		}

	}

	private int[] parseExperience(String experience) {
		experience = experience.toLowerCase().trim();
		int minExp = 0;
		int maxExp = 0;

		if (experience.contains("+")) {
			// Handles "4+ years" or "4+" case
			String[] parts = experience.split("\\+");
			minExp = Integer.parseInt(parts[0].trim());
		} else if (experience.contains("-")) {
			// Handles "4-6 years" case
			String[] parts = experience.split("-");
			minExp = Integer.parseInt(parts[0].trim());
			maxExp = Integer.parseInt(parts[1].trim());
		} else if (experience.matches("\\d+")) {
			// Handles "4" or "4 years" case
			minExp = Integer.parseInt(experience.replaceAll("\\D", "").trim());
		}

		return new int[] { minExp, maxExp };
	}

//	@PostMapping("/rmgAssign")
//	public ResponseEntity<?> addingRMGData(@RequestParam List<String> emp_name, @RequestParam("rrfId") long rrfId) {
//	    if (emp_name != null && !emp_name.isEmpty()) {
//	        Optional<RRF> rrfData = rrfRepo.findById(rrfId);
//	        if (!rrfData.isPresent()) {
//	            throw new NoRecordFoundException("No RRF Record is existed in the database with RRFID : " + rrfId);
//	        }
//
//	        for (String string : emp_name) {
//	            Object[] userDataArray = rrfRepo.getuserData(string);
//	            if (userDataArray != null && userDataArray.length > 0) {
//	                Object[] userData = (Object[]) userDataArray[0]; // Assuming each row is an array of data
//	                if (userData != null && userData.length >= 3) {
//	                    Long employeeId = Long.parseLong(userData[0].toString()); // Assuming employee_id is a Long
//	                    String email = userData[1].toString();
//	                    String phoneNumber = userData[2].toString();
//
//	                    Candidate candidate = new Candidate();
//	                    candidate.setEmployeeId(employeeId);
//	                    candidate.setEmailId(email);
//	                    candidate.setMobileNo(phoneNumber);
//	                    candidate.setFullName(string);
//	                    candidate.setStatus("InProgress");
//	                    candidate.setSubStatus("YET_TO_SCHEDULE");
//	                    candidate.setResourceType("Internal");
//	                    candidate.setVisibility(1);
//	                    int candidateCount = rrfData.get().getCandidateCount();
//	                    rrfData.get().setCandidateCount(++candidateCount);
//	                    rrfRepo.save(rrfData.get());
//	                    rrfData.get().setRequirementStatus(CandidateStatus.InProgress.toString());
//	                    candidate.setRrf(rrfData.get());
//	                    candidateRepo.save(candidate);
//	                }
//	            }
//	        }
//	        return new ResponseEntity<>("RMG data added successfully", HttpStatus.OK);
//	    } else {
//	        return new ResponseEntity<>("No employee names provided", HttpStatus.BAD_REQUEST);
//	    }
//	}

	@PostMapping("rmgAssign")
	public ResponseEntity<?> addingRMGData(@RequestParam List<Integer> employeeIds, @RequestParam("rrfId") long rrfId) {
		if (employeeIds != null && !employeeIds.isEmpty()) {
			Optional<RRF> rrfData = rrfRepo.findById(rrfId);
			if (!rrfData.isPresent()) {
				throw new NoRecordFoundException("No RRF Record is existed in the database with RRFID : " + rrfId);
			}
			List<Object[]> usersByemps = rrfRepo.getUsersByemps(employeeIds);
			for (Object[] user : usersByemps) {
				Object object = user[0];
				Long employeeId = Long.parseLong(user[0].toString());
				String email = user[1].toString();
				String phoneNumber = user[2].toString();
				String name = user[3].toString();
				double experience = Double.parseDouble(user[4].toString());

				Candidate candidate = new Candidate();
				candidate.setEmployeeId(employeeId);
				candidate.setEmailId(email);
				candidate.setMobileNo(phoneNumber);
				candidate.setFullName(name);
				candidate.setTotalExperience(experience);
				candidate.setStatus("InProgress");
				candidate.setSubStatus("YET_TO_SCHEDULE");
				candidate.setResourceType("Internal");
				candidate.setRequirementName(rrfData.get().getRequirementName());
				candidate.setVisibility(1);
				int candidateCount = rrfData.get().getCandidateCount();
				rrfData.get().setCandidateCount(++candidateCount);
//				rrfRepo.save(rrfData.get());
				rrfData.get().setRequirementStatus(rrfData.get().getRequirementStatus());
				candidate.setRrf(rrfData.get());
				candidateRepo.save(candidate);
			}
			return new ResponseEntity<>("RMG data added successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No employee names provided", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@ModelAttribute("file") MultipartFile file, HttpServletRequest request) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid Excel file.");
		}

		try {
			String result = rrfServiceImpl.saveDataFromExcel(file, request);

			return ResponseEntity.ok(result);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error processing file: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}