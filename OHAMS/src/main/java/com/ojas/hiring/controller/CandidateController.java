package com.ojas.hiring.controller;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.ojas.hiring.dto.CandidateDetailsDto;
import com.ojas.hiring.dto.GetAllCandidatesDto;
import com.ojas.hiring.dto.GetAllCandidatesDtoInfo;
import com.ojas.hiring.dto.UploadsDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.CandidateStatus;
import com.ojas.hiring.entity.CandidateSubStatus;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.entity.Uploads;
import com.ojas.hiring.exceptions.BaseController;
import com.ojas.hiring.exceptions.BaseErrors;
import com.ojas.hiring.exceptions.BaseException;
import com.ojas.hiring.exceptions.CandidateChecking;
import com.ojas.hiring.exceptions.NoRecordFoundException;
import com.ojas.hiring.exceptions.RrfIdNotFound;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.InterviewsRepository;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.repo.UploadsRepo;
import com.ojas.hiring.service.CandidateService;
import com.ojas.hiring.serviceImpl.CandidateServiceImpl;


@RestController
@RequestMapping("/api")
public class CandidateController extends BaseController {
	private static final Logger logger = LogManager.getLogger(CandidateController.class);
	private static final long MAX_FILE_SIZE_BYTES = 3 * 1024 * 1024; // up to 6 MB

	@Autowired
	private CandidateRepo candidateRepo;

	@Autowired
	CandidateService candidateService;

	@Autowired
	private InterviewsRepository interviewRepo;

	@Autowired
	private RRFRepo rrfRepo;

	@Autowired
	private UploadsRepo uploadsRepo;
	@Autowired
	private Validator validator;
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private HttpServletRequest request;

	private CandidateServiceImpl serviceImpl;
	private Optional<RRF> findById;
	private Optional<Candidate> candidateUploading;

	public CandidateController(CandidateServiceImpl serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

	@PostMapping("/postTagDetails")
	public String postTagDetails(@RequestBody Candidate candidate) {
		Map<String, Object> datamap = new HashMap<>();
		Candidate save = candidateRepo.save(candidate);
		return "Successfully Created Candidate details";

	}

	@GetMapping("/getCandidateById/{id}")
	public ResponseEntity<Candidate> getCandidate(@PathVariable("id") long id) {
		Optional<Candidate> candidateData = candidateRepo.findById(id);

		if (candidateData.isPresent()) {
			// If the candidate is found, return it with a 200 OK status
			return ResponseEntity.ok(candidateData.get());
		} else {
			// If the candidate is not found, return a 404 Not Found status
			throw new  BaseException(BaseErrors.NO_DATA_FOUND);
		}
	}

	@GetMapping("/getCandidateUploadDetails/{uploaded_module}/{id}")
	public ResponseEntity<UploadsDto> getUploadDetails(@PathVariable("uploaded_module") String uploaded_module,
			@PathVariable("id") long id) {

		Optional<Uploads> candidateUploading = uploadsRepo.getCandidateFileUploads(uploaded_module, id);
		if (candidateUploading.isPresent()) {
			UploadsDto uploads = new UploadsDto();
			uploads.setUploadId(candidateUploading.get().getUploadId());
			uploads.setFileName(candidateUploading.get().getFileName());
			return ResponseEntity.ok(uploads);
		} else {

			throw new BaseException(BaseErrors.NO_DATA_FOUND);
		}

	}

	@GetMapping("/candidateDownloadFile/{id}")
	public ResponseEntity<?> candidateDownloadFile(@PathVariable long id, HttpServletResponse resp) {
		Optional<Uploads> filedetails = uploadsRepo.findById(id);

		// Check if the file exists
		if (filedetails.isPresent()) {
			Uploads upload = filedetails.get();

			if (upload.getUploadedModule().equals("CANDIDATE")) {

				// Set headers
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + upload.getFileName() + "\"");

				// Return response entity with the file content
				return new ResponseEntity<>(upload.getUploadedFile(), headers, HttpStatus.OK);
			}else {
				// Return a response entity indicating the file was not found
				throw new BaseException(BaseErrors.NO_DATA_FOUND);
			}
		} else {
			// Return a response entity indicating the file was not found
			throw new BaseException(BaseErrors.NO_DATA_FOUND);
		}
	}

	@GetMapping("/getCandidateDetails")
	public ResponseEntity<?> getCandidateDetails() {
		Map<String, List<CandidateDetailsDto>> candidateDetails = candidateService.getCandidateDetails();

		if (candidateDetails == null || candidateDetails.isEmpty()) {
			return ResponseEntity.ok(Collections.emptyMap());
		}
		return ResponseEntity.ok(candidateDetails);
	}

//    @PostMapping("/postCandidate")
//    public @ResponseBody String postCandidate(@RequestBody Candidate candidate) {
//      return candidateService.postCandidate(candidate);
//
//    }

	@RequestMapping(value = "/postCandidateDetails", method = RequestMethod.POST)
	public @ResponseBody String postCandidateDetails(@RequestParam Map<String, String> parameters,
			@RequestParam(value = "candidateFiles", required = false) MultipartFile[] candidateFiles,
			HttpServletRequest request) throws Exception, IOException {

		logger.info("/hiring/api/postCandidateDetails --> Processing Canidate FormData and Uploaded Files");

		String fullName = null;
		String mobileNo = null;
		String emailId = null;
		double totalExperience = 0.0;
		String currentlyWorkingAs = null;
		String currentlyWorkingAt = null;
		String currentLocation = null;
		String preferredLocation = null;
		double ctcPA = 0.0;
		double expectedCtcPa = 0.0;
		double noticePeriod = 0.0;
		String currentlyServingNoticePeriod = null;
		String comments = null;
		String skillSet = null;
		long rrfLink = 0;
		String createdBy = null;
		String status = null; // status
		String subStatus = null;
		String remark = null;
		double offered_CTC = 0.0;
		String scheduledOn = null;
		Date offered_Date = null;
		Date doj = null;

		Uploads[] attachments = null;
		Date publishedOn = null;
		int visibility = 1;

		String hiringType = null;
		String hiringManager = null;
		String position = null;
		String recordAuthor = null;
		Long rrfId = 0L;
		String source = null;
		String vendor = null;
		Date creation_date = null;
		String additionalSkills = null;
		long cid = 0L;
		logger.info("Hii");
		logger.info(parameters.entrySet().size());
		for (Entry<String, String> pair : parameters.entrySet()) {
//            System.out.println(String.format("Key (name) is: %s, Value is : %s", pair.getKey(), pair.getValue()));
			logger.info("key  : " + pair.getKey() + " value : " + pair.getValue());
			if (pair.getKey().equals("hiringType")) {
				try {
					hiringType = (pair.getValue());
					continue;
				} catch (Exception e) {
					hiringType = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("hiringManager")) {
				try {
					hiringManager = (pair.getValue());
					continue;
				} catch (Exception e) {
					hiringManager = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("position")) {
				try {
					position = (pair.getValue());
					continue;
				} catch (Exception e) {
					position = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("recordAuthor")) {
				try {
					recordAuthor = (pair.getValue());
					continue;
				} catch (Exception e) {
					recordAuthor = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("fullName")) {
				try {
					fullName = (pair.getValue());
					continue;
				} catch (Exception e) {
					fullName = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("mobileNo")) {
				try {
					mobileNo = (pair.getValue());
					continue;
				} catch (Exception e) {
					mobileNo = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("emailId")) {
				try {
					emailId = (pair.getValue());
					continue;
				} catch (Exception e) {
					emailId = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("totalExperience")) {
				try {
					totalExperience = Double.parseDouble(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					totalExperience = 0;
					continue;
				}
			}
			if (pair.getKey().equals("currentlyWorkingAs")) {
				try {
					currentlyWorkingAs = (pair.getValue());
					continue;
				} catch (Exception e) {
					currentlyWorkingAs = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("currentlyWorkingAt")) {
				try {
					currentlyWorkingAt = (pair.getValue());
					continue;
				} catch (Exception e) {
					currentlyWorkingAt = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("currentLocation")) {
				try {
					currentLocation = (pair.getValue());
					continue;
				} catch (Exception e) {
					currentLocation = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("preferredLocation")) {
				try {
					preferredLocation = (pair.getValue());
					continue;
				} catch (Exception e) {
					preferredLocation = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("ctcPA")) {
				try {
					ctcPA = Double.parseDouble(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					ctcPA = 0;
					continue;
				}
			}
			if (pair.getKey().equals("expectedCtcPa")) {
				try {
					expectedCtcPa = Double.parseDouble(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					expectedCtcPa = 0;
					continue;
				}
			}
			if (pair.getKey().equals("noticePeriod")) {
				try {
					noticePeriod = Double.parseDouble(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					noticePeriod = 0;
					continue;
				}
			}
			if (pair.getKey().equals("currentlyServingNoticePeriod")) {
				try {
					currentlyServingNoticePeriod = pair.getValue();
					continue;
				} catch (Exception e) {
					currentlyServingNoticePeriod = null;
					continue;
				}
			}
			if (pair.getKey().equals("comments")) {
				try {
					comments = (pair.getValue().trim());

					continue;
				} catch (Exception e) {
					comments = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("skillSet")) {
				try {
					skillSet = (pair.getValue().trim());
					continue;
				} catch (Exception e) {
					skillSet = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("createdBy")) {
				try {
					createdBy = (pair.getValue().trim());
					continue;
				} catch (Exception e) {
					createdBy = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("offered_CTC")) {
				try {
					offered_CTC = Double.parseDouble(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					offered_CTC = 0;
					continue;
				}
			}
			if (pair.getKey().equals("offered_Date")) {
				try {
					offered_Date = new Date(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					offered_Date = new Date();
					continue;
				}
			}
			if (pair.getKey().equals("doj")) {
				try {
					doj = new Date(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					doj = new Date();
					continue;
				}
			}
			if (pair.getKey().equals("rrfLink")) {
				try {
					rrfLink = Long.parseLong(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					rrfLink = 0;
					continue;
				}
			}

			if (pair.getKey().equals("rrfId")) {
				try {
					rrfId = Long.parseLong(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					rrfId = 0L;
					continue;
				}
			}

			if (pair.getKey().equals("source")) {
				try {
					source = pair.getValue().trim();
					continue;
				} catch (Exception e) {
					source = null;
					continue;
				}
			}

			if (pair.getKey().equals("vendor")) {
				try {
					vendor = pair.getValue().trim();

					continue;
				} catch (Exception e) {
					vendor = null;
					continue;
				}
			}

			if (pair.getKey().equals("additionalSkill")) {
				try {
					additionalSkills = pair.getValue().trim();
					continue;
				} catch (Exception e) {
					additionalSkills = null;
					continue;
				}
			}

			if (pair.getKey().equals("scheduledOn")) {
				try {
					scheduledOn = pair.getValue();
					continue;
				} catch (Exception e) {
					scheduledOn = "NA";
					continue;
				}
			}

//            if (pair.getKey().equals("status")) {
//                try {
//                    status = pair.getValue();
//                    continue;
//                } catch (Exception e) {
//                    status = null;
//                    continue;
//                }
//            }

			if (pair.getKey().equals("cid")) {
				try {
					cid = Long.parseLong(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					cid = 0L;
					continue;
				}
			}

			long millis = System.currentTimeMillis();
			creation_date = new java.sql.Date(millis);

		}
		String jsonString = "";
		Candidate can = new Candidate();
		can.setFullName(fullName);
		can.setMobileNo(mobileNo);
		can.setEmailId(emailId);
		can.setTotalExperience(totalExperience);
		can.setCurrentlyWorkingAs(currentlyWorkingAs);
		can.setCurrentlyWorkingAt(currentlyWorkingAt);
		can.setCurrentLocation(currentLocation);
		can.setPreferredLocation(preferredLocation);
		can.setCtcPA(ctcPA);
		can.setExpectedCtcPa(expectedCtcPa);
//		can.setNoticePeriod(currentlyServingNoticePeriod);
		can.setCurrentlyServingNoticePeriod(currentlyServingNoticePeriod);
		can.setComments(comments);
		can.setAdditionalSkills(additionalSkills);
		can.setCreationDate(creation_date);
		can.setVendor(vendor);
		can.setSource(source);

		Gson gson = new Gson();
		Map<String, Double> sks = null;
		if (skillSet != null) {
			try {
				sks = gson.fromJson(skillSet, Map.class);
			} catch (Exception e) {
				sks = null;
			}
		} else {
			sks = null;

		}

		// System.out.println("================"+skillSet);

		if (sks != null) {
			can.setSkillSet(sks);
		}
		can.setRrfLink(rrfLink);

		can.setStatus(status);
		can.setStatus(CandidateStatus.InProgress.toString());
		can.setSubStatus(CandidateSubStatus.YET_TO_SCHEDULE.toString());
//		can.setRemark(remark);
//
//		can.setHiringType(hiringType);
//		can.setHiringManager(hiringManager);
//		can.setPosition(position);
		can.setRecordAuthor(recordAuthor);
		can.setIpAddress(request.getRemoteAddr());

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
//		can.setPublishedOn(date);
		can.setVisibility(visibility);

		Long rrfIdLong = 1L;
		Optional<RRF> rrfData = rrfRepo.findById(rrfId);

		if (!rrfData.isPresent()) {
			throw new NoRecordFoundException("No RRF Record exists in the database with RRFID: " + rrfId);
		} else {
			// rrfData is present, so it's safe to call get()
			RRF rrf = rrfData.get();
			int candidateCount = rrf.getCandidateCount();
			rrf.setCandidateCount(++candidateCount);
			rrfRepo.save(rrf);

//		if (rrfData.isPresent()) {
//			int candidateCount = rrfData.get().getCandidateCount();
//			rrfData.get().setCandidateCount(++candidateCount);
//		}else{
//			throw new NoRecordFoundException("No RRF Record is existed in the database with RRFID : " + rrfId);
//
//		}
			rrfRepo.save(rrfData.get());
			RRF rrf2 = rrfData.get();
			rrfData.get().setRequirementStatus(rrf2.getRequirementStatus());
			can.setRrf(rrfData.get());
			can.setComments(comments);
			can.setVendor(vendor);
			can.setEmployeeId(rrfData.get().getEmployeeId());
			can.setResourceType(rrf2.getHiringType());
			can.setRequirementName(rrf2.getRequirementName());
			Candidate saved = candidateRepo.saveAndFlush(can);

			if (candidateFiles != null) {
				Date d = new Date();
				String s = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(d).replaceAll("-", "_").replaceAll(":",
						"_");
				Uploads[] uploads = new Uploads[candidateFiles.length];

				for (int i = 0; i < candidateFiles.length; i++) {
					long bytes = candidateFiles[i].getSize();
					double kilobytes = (bytes / 1024);
					double megabytes = (kilobytes / 1024);
					if (bytes <= MAX_FILE_SIZE_BYTES) {
						String mimeType = candidateFiles[i].getContentType();
						String fileName = candidateFiles[i].getOriginalFilename();
						String fileExtension = FilenameUtils.getExtension(candidateFiles[i].getOriginalFilename());
						byte[] fileContent = candidateFiles[i].getBytes();

						Uploads file = new Uploads(0, fileContent, fileName, fileExtension, kilobytes, s, "CANDIDATE",
								0);
						uploads[i] = file;
					}
				}
				for (int i = 0; i < candidateFiles.length; i++) {
					if (uploads[i] != null) {
						uploads[i].setLink(saved.getCid());
//					uploads[i].setCandidate(saved);
						Uploads u = uploadsRepo.saveAndFlush(uploads[i]);
					}
				}
			}

			// create default interview with 'Not-Scheduled' as status
//		Interviews defaultInterview = new Interviews();
//		defaultInterview.setCid(can.getCid());
//		defaultInterview.setInterviewStatus("Not-Scheduled");
//		interviewRepo.saveAndFlush(defaultInterview);

			JSONObject obj = new JSONObject();
			obj.put("status", "Candidate Data Successfully Created");
			jsonString = obj.toString();

			return jsonString;
		}
	}

	@RequestMapping(value = "/updateCandidateDetails", method = RequestMethod.PUT)
	public @ResponseBody String updateCandidateDetails(@RequestParam Map<String, String> parameters,
			@RequestParam("cid") String id,
			@RequestParam(value = "candidateFiles", required = false) MultipartFile[] candidateFiles,
			HttpServletRequest request) throws Exception, IOException {

		logger.info("/hiring/api/updateCandidateDetails --> Processing Update Canidate FormData and Uploaded Files");

		String cid = null;
		String fullName = null;
		String mobileNo = null;
		String emailId = null;
		double totalExperience = 0.0;
		String currentlyWorkingAs = null;
		String currentlyWorkingAt = null;
		String currentLocation = null;
		String preferredLocation = null;
		double ctcPA = 0.0;
		double expectedCtcPa = 0.0;
		double noticePeriod = 0.0;
		String currentlyServingNoticePeriod = null;
		String comments = null;
		String skillSet = null;
		long rrfLink = 0;
		String source = null;
		String createdBy = null;
		String status = null;
		String availability = null;
		String subStatus = null;
		String remark = null;
		double offered_CTC = 0.0;
		String additionalSkills = null;
		Date offered_Date = null;
		Date doj = null;

		Uploads[] attachments = null;
		Date publishedOn = null;
		int visibility = 1;

		String hiringType = null;
		String hiringManager = null;
		String position = null;
		String recordAuthor = null;

		Candidate candidate = null;
		if (id != null) {
			candidate = candidateRepo.getByCid(Long.parseLong(id));
		}
		for (Entry<String, String> pair : parameters.entrySet()) {
			System.out.println(String.format("Key (name) is: %s, Value is : %s", pair.getKey(), pair.getValue()));
			if (pair.getKey().equals("cid")) {
				try {
					cid = (pair.getValue());
					candidate.setCid(Long.parseLong(cid));
					continue;
				} catch (Exception e) {
					cid = null;
					continue;
				}
			}
			if (pair.getKey().equals("hiringType")) {
				try {
					hiringType = (pair.getValue());

					continue;
				} catch (Exception e) {
					hiringType = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("hiringManager")) {
				try {
					hiringManager = (pair.getValue());
					continue;
				} catch (Exception e) {
					hiringManager = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("position")) {
				try {
					position = (pair.getValue());

					continue;
				} catch (Exception e) {
					position = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("recordAuthor")) {
				try {
					recordAuthor = (pair.getValue());
					continue;
				} catch (Exception e) {
					recordAuthor = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("fullName")) {
				try {
					fullName = (pair.getValue());
					candidate.setFullName(fullName);
					continue;
				} catch (Exception e) {
					fullName = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("mobileNo")) {
				try {
					mobileNo = (pair.getValue());
					candidate.setMobileNo(mobileNo);
					continue;
				} catch (Exception e) {
					mobileNo = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("emailId")) {
				try {
					emailId = (pair.getValue());
					candidate.setEmailId(emailId);

					continue;
				} catch (Exception e) {
					emailId = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("totalExperience")) {
				try {
					totalExperience = Double.parseDouble(pair.getValue().trim());
					candidate.setTotalExperience(totalExperience);
					continue;
				} catch (Exception e) {
					totalExperience = 0;
					continue;
				}
			}
			if (pair.getKey().equals("currentlyWorkingAs")) {
				try {
					currentlyWorkingAs = (pair.getValue());
					candidate.setCurrentlyWorkingAs(currentlyWorkingAs);
					continue;
				} catch (Exception e) {
					currentlyWorkingAs = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("currentlyWorkingAt")) {
				try {
					currentlyWorkingAt = (pair.getValue());
					candidate.setCurrentlyWorkingAt(currentlyWorkingAt);
					continue;
				} catch (Exception e) {
					currentlyWorkingAt = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("currentLocation")) {
				try {
					currentLocation = (pair.getValue());
					candidate.setCurrentLocation(currentLocation);
					continue;
				} catch (Exception e) {
					currentLocation = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("preferredLocation")) {
				try {
					preferredLocation = (pair.getValue());
					candidate.setPreferredLocation(preferredLocation);
					continue;
				} catch (Exception e) {
					preferredLocation = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("ctcPA")) {
				try {
					ctcPA = Double.parseDouble(pair.getValue().trim());
					candidate.setCtcPA(ctcPA);
					continue;
				} catch (Exception e) {
					ctcPA = 0;
					continue;
				}
			}
			if (pair.getKey().equals("expectedCtcPa")) {
				try {
					expectedCtcPa = Double.parseDouble(pair.getValue().trim());
					candidate.setExpectedCtcPa(expectedCtcPa);
					continue;
				} catch (Exception e) {
					expectedCtcPa = 0;
					continue;
				}
			}
			if (pair.getKey().equals("noticePeriod")) {
				try {
					noticePeriod = Double.parseDouble(pair.getValue().trim());
					candidate.setNoticePeriod(noticePeriod);
					continue;
				} catch (Exception e) {
					noticePeriod = 0;
					continue;
				}
			}
			if (pair.getKey().equals("currentlyServingNoticePeriod")) {
				try {
					currentlyServingNoticePeriod = pair.getValue();
					candidate.setCurrentlyServingNoticePeriod(currentlyServingNoticePeriod);

					continue;
				} catch (Exception e) {
					currentlyServingNoticePeriod = null;
					continue;
				}
			}
			if (pair.getKey().equals("source")) {
				try {
					source = pair.getValue().trim();
					candidate.setSource(source);
					continue;
				} catch (Exception e) {
					source = null;
					continue;
				}
			}
			if (pair.getKey().equals("comments")) {
				try {
					comments = (pair.getValue().trim());
					candidate.setComments(comments);
					continue;
				} catch (Exception e) {
					comments = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("createdBy")) {
				try {
					createdBy = (pair.getValue().trim());
					continue;
				} catch (Exception e) {
					createdBy = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("totalExperience")) {
				try {
					totalExperience = Double.parseDouble(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					totalExperience = 0;
					continue;
				}
			}
			if (pair.getKey().equals("offered_Date")) {
				try {
					offered_Date = new Date(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					offered_Date = new Date();
					continue;
				}
			}
			if (pair.getKey().equals("doj")) {
				try {
					doj = new Date(pair.getValue().trim());
				} catch (Exception e) {
					doj = new Date();
					continue;
				}
			}
			if (pair.getKey().equals("rrfLink")) {
				try {
					rrfLink = Long.parseLong(pair.getValue().trim());
					candidate.setRrfLink(rrfLink);

					continue;
				} catch (Exception e) {
					rrfLink = 0;
					continue;
				}
			}
			if (pair.getKey().equals("availability")) {
				try {
					availability = pair.getValue().trim();
					candidate.setAvailability(availability);

					continue;
				} catch (Exception e) {
					availability = "";
					continue;
				}
			}
			if (pair.getKey().equals("status")) {
				try {
					status = pair.getValue().trim();
					candidate.setStatus(status);
					continue;
				} catch (Exception e) {
					status = "";
					continue;
				}
			}
			if (pair.getKey().equals("additionalSkill")) {
				try {
					additionalSkills = pair.getValue().trim();
					candidate.setAdditionalSkills(additionalSkills);
					continue;
				} catch (Exception e) {
					additionalSkills = null;
					continue;
				}
			}
			if (pair.getKey().equals("vendor")) {
				String vendor;
				try {
					vendor = pair.getValue().trim();
					candidate.setVendor(vendor);
					continue;
				} catch (Exception e) {
					vendor = null;
					continue;
				}
			}
			if (pair.getKey().equals("subStatus")) {
				try {
					subStatus = pair.getValue().trim();
					candidate.setSubStatus(subStatus);
					continue;
				} catch (Exception e) {
					subStatus = "";
					continue;
				}
			}
		}

		String jsonString = "";

//		Long CID = Long.parseLong(id);
//		Optional<Candidate> dbObj = candidateRepo.findById(CID);
//		if (dbObj.isPresent()) {
//			Candidate candidate = dbObj.get();
//			if (currentLocation != null) {
//				candidate.setCurrentLocation(currentLocation);
//			}
//			if (comments != null) {
//				candidate.setComments(comments);
//			}
//			if (ctcPA > 0.0) {
//				candidate.setCtcPA(ctcPA);
//			}
//			if (currentlyWorkingAs != null) {
//				candidate.setCurrentlyWorkingAs(currentlyWorkingAs);
//			}
//			if (currentlyWorkingAt != null) {
//				candidate.setCurrentlyWorkingAt(currentlyWorkingAt);
//			}
//			if (emailId != null) {
//				candidate.setEmailId(emailId);
//			}
//			if (expectedCtcPa > 0.0) {
//				candidate.setExpectedCtcPa(expectedCtcPa);
//			}
//			if (fullName != null) {
//				candidate.setFullName(fullName);
//			}
////			if (candidate.getHiringManager() != null) {
////				dbObj.setHiringManager(candidate.getHiringManager());
////			}
////			if (candidate.getHiringType() != null) {
////				dbObj.setHiringType(candidate.getHiringType());
////			}
//			if (mobileNo != null) {
//				candidate.setMobileNo(mobileNo);
//			}
//			if (noticePeriod > 0.0) {
//				candidate.setNoticePeriod(noticePeriod);
//			}
////			if (candidate.getOffered_CTC() > 0.0) {
////				dbObj.setOffered_CTC(candidate.getOffered_CTC());
////			}
//			if (preferredLocation != null) {
//				candidate.setPreferredLocation(preferredLocation);
//			}
////			if (candidate.getPublishedOn() != null) {
////				dbObj.setPublishedOn(candidate.getPublishedOn());
////			}
////			if (candidate.getRemark() != null) {
////				dbObj.setRemark(candidate.getRemark());
////			}
////			if (candidate.getSkillSet() != null) {
////				dbObj.setSkillSet(candidate.getSkillSet());
////			}
//			if (status != null) {
//				candidate.setStatus(status);
//			}
//			if (subStatus != null) {
//				candidate.setSubStatus(subStatus);
//			}
//			if (availability != null) {
//				candidate.setAvailability(availability);
//			}
//			if (noticePeriod != 0.0) {
//				candidate.setNoticePeriod(noticePeriod);
//			}
//			if (totalExperience > 0) {
//				candidate.setTotalExperience(totalExperience);
//			}
//
//			if (subStatus != null && subStatus.equalsIgnoreCase(subStatus)) {
//				candidate.setSubStatus(subStatus);
//			}
//
////			if(findById.isPresent()) {
////			Candidate can = new Candidate();
////			can.setFullName(fullName);
//////			can.setMiddleName(middleName);
//////			can.setLastName(lastName);
////			can.setMobileNo(mobileNo);
////			can.setEmailId(emailId);
////			can.setTotalExperience(totalExperience);
////			can.setCurrentlyWorkingAs(currentlyWorkingAs);
////			can.setCurrentlyWorkingAt(currentlyWorkingAt);
////			can.setCurrentLocation(currentLocation);
////			can.setPreferredLocation(preferredLocation);
////			can.setCtcPA(ctcPA);
////			can.setExpectedCtcPa(expectedCtcPa);
//////			can.setNoticePeriod(currentlyServingNoticePeriod);
////			can.setCurrentlyServingNoticePeriod(currentlyServingNoticePeriod);
////			can.setComments(comments);
////			Gson gson = new Gson();
////			Map<String, Double> sks = null;
////			if (skillSet != null) {
////				try {
////					sks = gson.fromJson(skillSet, Map.class);
////				} catch (Exception e) {
////					sks = null;
////				}
////			} else {
////				sks = null;
////
////			}
////
////			// System.out.println("================"+skillSet);
////
////			if (sks != null) {
////				can.setSkillSet(sks);
////			}
////			can.setRrfLink(rrfLink);
////
//////			can.setStatus(status);
//////			can.setSubStatus(subStatus);
//////			can.setRemark(remark);
//////
//////			can.setHiringType(hiringType);
//////			can.setHiringManager(hiringManager);
//////			can.setPosition(position);
////			can.setRecordAuthor(recordAuthor);
////			can.setIpAddress(request.getRemoteAddr());
////
////			long millis = System.currentTimeMillis();
////			java.sql.Date date = new java.sql.Date(millis);
//////			can.setPublishedOn(date);
////			can.setVisibility(visibility);
////

		Candidate saved = candidateRepo.saveAndFlush(candidate);
		if (saved != null && saved.getStatus().equalsIgnoreCase(status) && saved.getStatus() != null) {
			RRF rrf = saved.getRrf();
			if (rrf != null && saved.getSubStatus().equalsIgnoreCase("Onboarded")) {
				int open1 = rrf.getOpenPositions();
				int closed1 = rrf.getClosedPositions();
				int openedPostions = --open1;
				int closePositions = ++closed1;
				rrf.setOpenPositions(openedPostions);
				rrf.setClosedPositions(closePositions);
			}
			RRF rrfupdate = rrfRepo.save(rrf);
			if (rrfupdate != null) {
				jsonString = "The Candidate data is updated succssfully";

			} else
				jsonString = "failing to update the candidate information";

		}
//
//			if (candidateFiles != null) {
//				java.util.Date d = new java.util.Date();
//				String s = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(d).replaceAll("-", "_").replaceAll(":",
//						"_");
//				Uploads[] uploads = new Uploads[candidateFiles.length];
//
//				for (int i = 0; i < candidateFiles.length; i++) {
//					long bytes = candidateFiles[i].getSize();
//					double kilobytes = (bytes / 1024);
//					double megabytes = (kilobytes / 1024);
//					if (bytes > 0) {
//						String mimeType = candidateFiles[i].getContentType();
//						String fileName = candidateFiles[i].getOriginalFilename();
//						String fileExtension = FilenameUtils.getExtension(candidateFiles[i].getOriginalFilename());
//						byte[] fileContent = candidateFiles[i].getBytes();
//
//						Uploads file = new Uploads(0, fileContent, fileName, fileExtension, kilobytes, s, "CANDIDATE",
//								0);
//						uploads[i] = file;
//					}
//				}
//				for (int i = 0; i < candidateFiles.length; i++) {
//					if (uploads[i] != null) {
//						uploads[i].setLink(saved.getCid());
//						Uploads u = uploadsRepo.saveAndFlush(uploads[i]);
//					}
//				}
//			}
//
//			// create default interview with 'Not-Scheduled' as status
////		Interviews defaultInterview = new Interviews();
////		defaultInterview.setCid(can.getCid());
////		defaultInterview.setInterviewStatus("Not-Scheduled");
////		interviewRepo.saveAndFlush(defaultInterview);
//
//			JSONObject obj = new JSONObject();
//			obj.put("status", "Candidate Data Successfully Updated");
//			jsonString = obj.toString();
//		} else {
//			JSONObject obj = new JSONObject();
//			obj.put("status", "No records found");
//			jsonString = obj.toString();
//
//		}

		return jsonString;
	}

	@GetMapping(path = "/getAllCandidates", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody String getAllCandidates() {
		logger.info("/hiring/api/getAllCandidates --> Processing Canidate FormData and Uploaded Files");

		List<Candidate> candidateData = candidateRepo.getCandidateDetails();
//		ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(candidateData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSONObject obj = new JSONObject();
			obj.put("status", "Exception:" + e.toString());
			jsonString = obj.toString();
		}
		logger.info("/hiring/api/getAllCandidates --> Result : " + jsonString);

		return jsonString;
	}

	@GetMapping("/getAllRRFValuesForCandidates")
	public ResponseEntity<?> getAllRRFValues() throws IllegalAccessException {

		List<GetAllCandidatesDto> allRRFValues = serviceImpl.getAllRRFValues();
//
//		// Convert the map to JSON string
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonString = "";
//		try {
//			jsonString = mapper.writeValueAsString(data);
//		} catch (JsonProcessingException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}

		return ResponseEntity.ok(allRRFValues);
	}

	@GetMapping(path = "/scheduleInterviewList1", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody String getAllCandidatesWithIntStatus() {
		List<Interviews> findAll = interviewRepo.findAll();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(findAll);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSONObject obj = new JSONObject();
			obj.put("status", "Exception:" + e.toString());
			jsonString = obj.toString();
		}
		logger.info("/hiring/api/getAllCandidates --> Result : " + jsonString);

		return jsonString;
	}

	@GetMapping("/getBDMDetails")
	public ResponseEntity<?> getBDMData() {
		Map<String, List<GetAllCandidatesDtoInfo>> bdmDetails = candidateService.getBDMDetails();

		if (bdmDetails == null || bdmDetails.isEmpty()) {
			Map<String, Integer> defaultResponse = new HashMap<>();
			defaultResponse.put("Total", 0);
			defaultResponse.put("Open", 0);
			defaultResponse.put("Closed", 0);
			return ResponseEntity.ok(defaultResponse);
		}

		// You may want to perform additional checks here to ensure the data within the
		// lists is also valid

		return ResponseEntity.ok(bdmDetails);
	}

//	@GetMapping(path = "/scheduleInterviewList", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	public @ResponseBody String getAllCandidatesWithIntStatusOptimized() {
//		logger.info("/hiring/api/scheduleInterviewList --> Processing Canidate FormData and Uploaded Files");
//
//		List<String> finalResult = new ArrayList<String>();
//
//		List<Candidate> candidateData = candidateRepo.findAll(Sort.by(Sort.Direction.DESC, "publishedOn"));
//		String candidateInterviewer = null;
//		String candidateInterviewsStatus = null;
//		String candidateInterviewsLevel = null;
//		long interviewId = -1;
//		for (int i = 1; i < candidateData.size(); i++) {
//			candidateInterviewer = "";
//			candidateInterviewsLevel = "";
//			candidateInterviewsStatus = "";
//
//			Optional<RRF> rrfData = rrfRepo.findById(candidateData.get(i).getRrfLink());
//			//Optional<RRF> rrfData = rrfRepo.findById(candidateData.get(i).getRrf().getId());
//		List<Interviews> interviewsData = interviewRepo
//					.findByCidOrderByScheduledOnDesc(candidateData.get(i).getCid());
////			
////			List<Interviews> interviewsData = interviewRepo.findAll();
////										.findByCidOrderByScheduledOnDesc(candidateData.get(i).getCid());
//			
//			
//			if (interviewsData.size() > 0) {
//				for (int j = 0; j < interviewsData.size(); j++) {
////					System.out.println(interviewsData.get(j).getId()+"-----------------"+interviewsData.get(j).getCid()+"-----------------"+interviewsData.get(j).getInterviewStatus());
//					interviewId = interviewsData.get(j).getId(); 
//					candidateInterviewer = interviewsData.get(j).getInterviewerName().trim();
//					candidateInterviewsStatus = interviewsData.get(j).getInterviewStatus().trim();
//					candidateInterviewsLevel = interviewsData.get(j).getInterviewRound().trim();
//					if (candidateInterviewsLevel.length() > 0) {
//						if (candidateInterviewsStatus.length() == 0) {
//							candidateInterviewsStatus = "Scheduled";
//						}
//					} else {
//						candidateInterviewer = "";
//						candidateInterviewsLevel = "";
//						candidateInterviewsStatus = "NotScheduled";
//					}
//					break;
//				}
//			} else {
//				candidateInterviewer = "";
//				candidateInterviewsLevel = "";
//				candidateInterviewsStatus = "NotScheduled";
//			}
//
//			Gson gson = new GsonBuilder().serializeNulls().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
//					.create();
//			String sourceJson = gson.toJson(candidateData.get(i));
//			String json222 = "\"interviewId\" : " + interviewId + "";
//			String json22 = "\"interviewLevel\" : \"" + candidateInterviewsLevel + "\"";
//			String json2 = "\"interviewStatus\" : \"" + candidateInterviewsStatus + "\"";
//			String json33 = "\"interviewerName\" : \"" + candidateInterviewer + "\"";
//			
//			String json3 = "\"hiringManager\" : \"NA\"";
//			String json4 = "\"hiringType\" : \"NA\"";
//			String json44 = "\"jobLevel\" : \"NA\"";
//			
//			
//			if (rrfData.isPresent()) {
//				json3 = "\"hiringManager\" : \"" + rrfData.get().getCreatedBy() + "\"";
//				json4 = "\"hiringType\" : \"" + rrfData.get().getHiringType() + "\"";
//				json44 = "\"jobLevel\" : \"" + rrfData.get().getJobLevel() + "\"";				
//			}
//			
//			
//			String targetJson = "{" + json222 + "," + json2 + "," + json33 + "," + json3 + "," + json4 + "," + json44
//					+ "}";
//
//			// System.out.println(sourceJson);
//			// System.out.println(targetJson);
//
//			Map<String, Object> firstObject = gson.fromJson(sourceJson, HashMap.class);
//			for (Map.Entry<String, Object> entry : firstObject.entrySet()) {
//				String key = entry.getKey();
//				Object value = entry.getValue();
//				if (value == null) {
//					firstObject.put(key, "NA");
//				}
//			}
//
//			Map secondObject = gson.fromJson(targetJson, HashMap.class);
//
//			firstObject.forEach((key1, value1) -> {
//				secondObject.merge(key1, value1, (key2, value2) -> key2);
//			});
//
//			// remove an attribute
//			List<String> keys = new ArrayList<>(secondObject.keySet());
//			Map<String, Integer> filter = new HashMap<String, Integer>();
//			// generateFilterData(keys, filter);
//			filter.put("lastName", 1);
//			filter.put("interviewStatus", 1);
//			filter.put("currentlyServingNoticePeriod", 0);
//			filter.put("publishedOn", 0);
//			filter.put("remark", 0);
//			filter.put("emailId", 0);
//			filter.put("recordAuthor", 1);
//			filter.put("result", 0);
//			filter.put("rrfLink", 1);
//			filter.put("offered_Date", 0);
//			filter.put("expectedCtcPa", 0);
//			filter.put("skillSet", 1);
//			filter.put("comments", 0);
//			filter.put("visibility", 0);
//			filter.put("ipAddress", 0);
//			filter.put("ctcPA", 0);
//			filter.put("mobileNo", 0);
//			filter.put("currentlyWorkingAs", 0);
//			filter.put("hiringManager", 1);
//			filter.put("subStatus", 0);
//			filter.put("preferredLocation", 0);
//			filter.put("currentLocation", 0);
//			filter.put("currentlyWorkingAt", 0);
//			filter.put("hiringType", 1);
//			filter.put("noticePeriod", 0);
//			filter.put("firstName", 1);
//			filter.put("totalExperience", 1);
//			filter.put("offered_CTC", 0);
//			filter.put("middleName", 1);
//			filter.put("position", 1);
//			filter.put("doj", 0);
//			filter.put("status", 0);
//			filter.put("cid", 1);
//
//			// apply filters
//			for (Map.Entry<String, Integer> entry : filter.entrySet()) {
//				String key = entry.getKey();
//				Integer value = entry.getValue();
//				if (value == 0) {
//					secondObject.remove(key);
//				}
//			}
//
//			String resultJson = gson.toJson(secondObject);
//			finalResult.add(resultJson);
//
//		}
//
//		String res = "[";
//		for (int j = 0; j < finalResult.size(); j++) {
//			if (j == finalResult.size() - 1) {
//				res += finalResult.get(j);
//			} else {
//				res += finalResult.get(j) + ",";
//			}
//
//		}
//
//		res += "]";
//		// System.out.println(res);
//		return res;
//	}

	private void generateFilterData(List<String> keys, Map<String, Integer> filter) {
/////////////Filters data generation ///////////////////////
		for (int i = 0; i < keys.size(); i++) {
			filter.put(keys.get(i), 1);
		}
		for (Entry<String, Integer> entry : filter.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println("filter.put(\"" + key + "\"," + value + ");");
		}
		///////////// End Of Filters data generation ///////////////////////

	}

//	@GetMapping(path = "/getCandidate/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	public @ResponseBody String getCandidate(@PathVariable("id") long id) {
//		logger.info("/hiring/api/getCandidate --> Processing Canidate FormData and Uploaded Files");
//
//		Optional<Candidate> candidateData = candidateRepo.findById(id);
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonString = null;
//		try {
//			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(candidateData.get());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			JSONObject obj = new JSONObject();
//			obj.put("status", "Exception:" + e.toString());
//			jsonString = obj.toString();
//		}
//		logger.info("/hiring/api/getCandidate --> Result : " + jsonString);
//
//		return jsonString;
//	}

//	@PutMapping("/update/{cid}")
////	public Candidate updateCandidate(@RequestParam ("cid") String cid,@RequestBody Candidate candidate) {
//	public Candidate updateCandidate(@PathVariable("cid") String cid, @RequestBody Candidate candidate) {
//		Candidate updateCandidate = candidateService.updateCandidate(cid, candidate);
//		return updateCandidate;
//	}

	@DeleteMapping("/deleteTagByCid/{cid}")
	public String deleteTagById(@PathVariable("cid") long cid) {
		Candidate byId = candidateRepo.getById(cid);
		byId.setVisibility(0);
		Candidate save = candidateRepo.save(byId);
		return "success";
	}

	@GetMapping(path = "/getAllTagDetails", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody String getAllTagDetails() {
		List<Candidate> byvisibility = candidateRepo.getByvisibility(1);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(byvisibility);
			System.out.println(jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSONObject obj = new JSONObject();
			obj.put("status", "Exception:" + e.toString());
			jsonString = obj.toString();
		}
		logger.info("/hiring/api/getAllCandidates --> Result : " + jsonString);

		return jsonString;

	}

	@GetMapping("/getTagDetailsByRrfId")
	public @ResponseBody String getTagDetailsByRrfId(@RequestParam("rrfId") Long rrfId) {
		List<Candidate> candidatesByRrfId = candidateRepo.findByRrfId(Optional.ofNullable(rrfId)).stream()
				.filter(candidate -> candidate.getVisibility() != 0).collect(Collectors.toList());
		if (candidatesByRrfId.isEmpty()) {
			throw new RrfIdNotFound(" Please provide valid rrf_id ");
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(candidatesByRrfId);
		} catch (Exception e) {
			JSONObject obj = new JSONObject();
			obj.put("status", "Exception:" + e.toString());
			jsonString = obj.toString();
		}
//		logger.info("/hiring/api/getTagDetailsByRrfId --> Result : " + jsonString);
		return jsonString;
	}

	@GetMapping("/getTagDetailsByEmployeeId")
	public @ResponseBody String getTagDetailsByEmployeeId(@RequestParam("employeeId") long employeeId) {
		List<Candidate> candidateDetailsByEmployeeId = candidateRepo.getByEmployeeId(employeeId);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(candidateDetailsByEmployeeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSONObject obj = new JSONObject();
			obj.put("status", "Exception:" + e.toString());
			jsonString = obj.toString();
		}
		logger.info("/hiring/api/getAllCandidates --> Result : " + jsonString);

		return jsonString;
	}

	@GetMapping("/checkByEmailId")
	public ResponseEntity checkByEmailId(@RequestParam("emailId") String emailId) {
		Optional<Candidate> findByemailId = candidateRepo.findByemailId(emailId);
		if (findByemailId.isPresent()) {
			throw new CandidateChecking("Candidate is already exist");
		} else {
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	@GetMapping("/checkByMobileNumber")
	public ResponseEntity checkByMobileNumber(@RequestParam("mobileNo") String mobileNo) {
		Optional<Candidate> findBymobileNo = candidateRepo.findBymobileNo(mobileNo);
		if (findBymobileNo.isPresent()) {
			throw new CandidateChecking("Candidate is already exist");
		} else {
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	@GetMapping("/getCandidateDataById")
	public String getCandidateDataById(@RequestParam("cid") int cid) {
		Candidate byCid = candidateRepo.getByCid(cid);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(byCid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSONObject obj = new JSONObject();
			obj.put("status", "Exception:" + e.toString());
			jsonString = obj.toString();
		}
		logger.info("/hiring/api/getAllCandidates --> Result : " + jsonString);

		return jsonString;

	}

	@GetMapping("/getAllCandidatesByRange")
	public String getAllCandidateByRange(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate)
			throws JsonProcessingException {
		List<Candidate> allCandidateBtCustom = candidateRepo.getAllCandidateByCustom(startDate, endDate);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String writeValueAsString = objectMapper.writeValueAsString(allCandidateBtCustom);
		return writeValueAsString;

	}

	@GetMapping("/download/candidateData")
	public ResponseEntity<byte[]> download() throws Exception {
		List<Candidate> downloadAllCandidateData = candidateRepo.downloadAllCandidateData();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String xml = objectMapper.writeValueAsString(downloadAllCandidateData);
		byte[] stringtobytes = xml.getBytes();
		String fileName = "file.xml";
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentLength(stringtobytes.length);
		respHeaders.setContentType(MediaType.TEXT_XML);
		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return new ResponseEntity<byte[]>(stringtobytes, respHeaders, HttpStatus.OK);
	}

	@GetMapping("/file/{cid}")
	public ResponseEntity<ByteArrayResource> getFile(@PathVariable("cid") long cid) {

		Optional<Uploads> findById = uploadsRepo.findByCid(cid);
		if (findById.isPresent()) {
			Uploads file = findById.get();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
					.body(new ByteArrayResource(file.getUploadedFile()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		}
	}
}
/*
 *
 * @GetMapping(path = "/getAllCandidatesWithIntStatus", produces =
 * MediaType.APPLICATION_OCTET_STREAM_VALUE) public @ResponseBody String
 * getAllCandidatesWithIntStatus() { logger.
 * info("/hiring/api/getAllCandidates --> Processing Canidate FormData and Uploaded Files"
 * );
 *
 * String cols =
 * "i.cid,i.comments,i.ctcpa,i.current_location,i.currently_serving_notice_period,i.currently_working_as,i.currently_working_at,i.doj,i.email_id,i.expected_ctc_pa,i.first_name,i.hiring_manager,i.\r\n"
 * +
 * "hiring_type,i.ip_address,i.last_name,i.middle_name,i.mobile_no,i.notice_period,i.offered_ctc,i.offered_date,i.position,i.preferred_location,i.published_on,i.record_author,i.remark,i.\r\n"
 * + "result,i.rrf_link,i.status,i.sub_status,i.total_experience";
 * ArrayList<String> t_columns = new ArrayList<String>(); String p[] =
 * cols.split(","); for (int i = 0; i < p.length; i++) {
 * t_columns.add(p[i].replace("i.", "")); }
 *
 * cols +=
 * ",(select sh.cid from data_interviews_schedule sh where sh.cid=i.cid) as interview_status"
 * ; t_columns.add("interview_status"); cols +=
 * ",(select sh.cid from data_interviews_schedule sh where sh.cid=i.cid) as skills"
 * ; t_columns.add("interview_status");
 *
 * String query = "select " + cols +
 * " from data_candidate i order by i.published_on desc";
 * System.out.println(query);
 *
 * List<Object[]> valuePairs =
 * entityManager.createNativeQuery(query).getResultList(); String data[] = new
 * String[valuePairs.size()]; for (int j = 0; j < data.length; j++) { data[j] =
 * ""; }
 *
 * for (int j = 0; j < t_columns.size(); j++) { int count = 0; for (Object[] dat
 * : valuePairs) { String str = ""; str = "" + dat[j]; str = "\"" +
 * t_columns.get(j) + "\":\"" + str + "\","; data[count] += str; count++; } //
 * String str = (String) valuePairs.get(j); // System.out.println(str); } String
 * res = "["; for (int j = 0; j < data.length; j++) { data[j] =
 * data[j].substring(0, data[j].length() - 1); if ((j + 1) == data.length) { res
 * += "{" + data[j] + "}"; } else { res += "{" + data[j] + "},"; }
 *
 * } //
 *
 * res += "]"; System.out.println(res); return res;
 *
 * }
 *
 *
 *
 */
