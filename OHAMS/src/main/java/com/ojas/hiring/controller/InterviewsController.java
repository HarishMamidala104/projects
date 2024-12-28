package com.ojas.hiring.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.ojas.hiring.dto.EmailRequest;
import com.ojas.hiring.dto.EmailResponse;
import com.ojas.hiring.dto.InterviewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.CandidateDTO;
import com.ojas.hiring.entity.CandidateInterviewStagesHistory;
import com.ojas.hiring.entity.CandidateSubStatus;
import com.ojas.hiring.entity.InterviewFeedback;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.entity.MailProperties;
import com.ojas.hiring.entity.User;
import com.ojas.hiring.repo.CandidateInterviewStagesHistoryRepo;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.InterviewFeedbackRepository;
import com.ojas.hiring.repo.InterviewsRepository;
import com.ojas.hiring.repo.UserRepository;
import com.ojas.hiring.service.CandidateService;
import com.ojas.hiring.service.InterviewService;
import com.ojas.hiring.serviceImpl.MailServiceImpl;

@RestController
@RequestMapping("/api")
public class InterviewsController {

	private static final Logger logger = LogManager.getLogger(InterviewsController.class);

	@Autowired
	private InterviewService interviewService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	CandidateRepo candidateRepo;

	@Autowired
	InterviewsRepository interviewsRepository;

	@Autowired
	InterviewFeedbackRepository interviewFeedbackRepository;

	@Autowired
	CandidateInterviewStagesHistoryRepo candidateInterviewStagesHistoryRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MailServiceImpl mailServiceImpl;

	@Autowired
	JavaMailSender mailSender;

	String interviewerGmail;

	String teamsLink;

	@RequestMapping(value = "/createInterview", method = RequestMethod.POST)
	public @ResponseBody String postInterviewDetails(@ModelAttribute InterviewDto parameters,
			HttpServletRequest request) throws Exception, IOException {

		Candidate byCid = candidateRepo.findById(parameters.getCid())
				.orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
		List<Candidate> all = candidateRepo.findAll();
		System.out.println(all);
		String jsonString = "";
		Interviews interviews = new Interviews();
		interviews.setInterviewerName(parameters.getInterviewerName());
		interviews.setInterviewOn(parameters.getInterviewOn());
		interviews.setInterviewRound(parameters.getInterviewRound());
		interviews.setInterviewType(parameters.getInterviewType());
		interviews.setInterviewerGmail(parameters.getInterviewerGmail());
		System.out.println(parameters.getInterviewRound());
		// interviews.setInterviewStatus(parameters.getInterviewStatus());
//        interviews.setId(parameters.getId());
		interviews.setEmployeeId(byCid.getEmployeeId());

		Date scheduledOn = parseDate(parameters.getScheduledOn());
		interviews.setScheduledOn(scheduledOn);

		interviews.setTeamsLink(parameters.getTeamsLink());
		interviews.setCandidate(byCid);
		interviews.setRequirementName(byCid.getRequirementName());
		interviews.setRrfId(byCid.getRrf().getId());

		Optional<Interviews> byInterviewId = interviewsRepository.getByInterviewId(parameters.getId());
		if (!byInterviewId.isPresent()) {
			Interviews save = interviewsRepository.save(interviews);
			saveCandidateInterviewHistory(save);
		} else {
			Interviews interviewsData = byInterviewId.get();
			InterviewFeedback interviewFeedback = interviewFeedbackRepository.getByInterviewId(interviewsData.getId());
			interviewFeedback.setStatus(interviewsData.getInterviewStatus());

			interviewsData.setInterviewerGmail(interviews.getInterviewerGmail());
			interviewsData.setInterviewType(interviews.getInterviewType());
			interviewsData.setInterviewRound(interviews.getInterviewRound());
			interviewsData.setInterviewerName(interviews.getInterviewerName());

			Date updatedScheduledOn = parseDate(parameters.getScheduledOn());
			interviewsData.setScheduledOn(updatedScheduledOn);
			interviewsData.setTeamsLink(parameters.getTeamsLink());

			interviewsRepository.save(interviewsData);
			saveCandidateInterviewHistory(interviewsData);
		}
		emailSending(byCid.getCid(), parameters.getInterviewerGmail());

		long cid = interviews.getCandidate().getCid();
		Candidate candidate = candidateService.getCandidateById(cid);
		// candidate.setSubStatus(CandidateSubStatus.INTERVIEW_SCHEDULED.toString());

		candidate.setSubStatus(parameters.getInterviewRound().concat("_SCHEDULED"));

		Candidate savedCandidate = candidateService.saveCandidate(candidate);

		JSONObject obj = new JSONObject();
		obj.put("status", "Interview Scheduled Successfully");
		jsonString = obj.toString();
		return jsonString;
	}

	private Date parseDate(String dateStr) {
		if (dateStr == null) {
			return new Date();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr.trim());
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid date format");
		}
	}

	private void saveCandidateInterviewHistory(Interviews interviews) {
		CandidateInterviewStagesHistory candidateHistory = new CandidateInterviewStagesHistory();
		long interviewId = interviews.getId();
		Interviews byId = interviewsRepository.getById(interviewId);
		candidateHistory.setInterviewerName(byId.getInterviewerName());
		candidateHistory.setCid(byId.getCandidate().getCid());
		candidateHistory.setInterviewRound(byId.getInterviewRound());
		candidateHistory.setInterviewStatus(byId.getInterviewStatus());
		candidateHistory.setSchedule_interview_id(byId.getId());
		candidateHistory.setInterviewerGmail(byId.getInterviewerGmail());
		candidateHistory.setScheduledOn(byId.getScheduledOn());
		candidateInterviewStagesHistoryRepo.save(candidateHistory);
	}
//	@PostMapping("createInterview")

//	@RequestMapping(method=RequestMethod.POST, value="/createInterview",headers="Accept=application/json")
//	public ResponseEntity<Object> createInterview(@RequestBody Interviews interview) {
//		long millis = System.currentTimeMillis();
//		interviewerGmail = interview.getInterviewerGmail();
//		teamsLink = interview.getTeamsLink();
//		java.sql.Date date = new java.sql.Date(millis);
//		String jsonString = "";
//		Optional<Interviews> byInterviewId = interviewsRepository.getByInterviewId(interview.getId());
//		if (!byInterviewId.isPresent()) {
//			if (interview.getInterviewRound() != null) {
//				interview.setPublishedOn(date);
//				interview.setCandidate(interview.getCandidate());
//				Interviews savedInterview = interviewService.createInterview(interview);
//				if (savedInterview.getId() > 0) {
//					JSONObject obj = new JSONObject();
//				emailSending(interview.getCid());
//					CandidateInterviewStagesHistory can = new CandidateInterviewStagesHistory();
//					long id = interview.getId();
//					Interviews byId = interviewsRepository.getById(id);
////					can.setCid(byId.getCid());
//					can.setInterviewerName(byId.getInterviewerName());
//					can.setInterviewRound(byId.getInterviewRound());
//					can.setInterviewStatus(byId.getInterviewStatus());
//					can.setSchedule_interview_id(interview.getId());
//					can.setScheduledOn(date);
//					can.setInterviewerGmail(interview.getInterviewerGmail());
//					candidateInterviewStagesHistoryRepo.save(can);
//					obj.put("status", "Interview Scheduled Successfully");
//					jsonString = obj.toString();
//				} else {
//					JSONObject obj = new JSONObject();
//					obj.put("status", "Failed to Scheduled Interview");
//					jsonString = obj.toString();
//				}
//			} else {
//				JSONObject obj = new JSONObject();
//				obj.put("status", "Failed to ScheduledInterview-Missing Interview Round");
//				jsonString = obj.toString();
//			}
//		} else {
//
//			Interviews interviewsData = byInterviewId.get();
//			InterviewFeedback interviewfeedback = interviewFeedbackRepository.getByInterviewId(interviewsData.getId());
//			interviewfeedback.setStatus(null);
//			interviewsData.setInterviewerGmail(interviewerGmail);
//			interviewsData.setInterviewType(interview.getInterviewType());
//			interviewsData.setInterviewRound(interview.getInterviewRound());
//			interviewsData.setInterviewerName(interview.getInterviewerName());
//			interview.setPublishedOn(date);
//			interview.setTeamsLink(teamsLink);
////			emailSending(interview.getCid());
////			List<CandidateInterviewStagesHistory> byCid2 = candidateInterviewStagesHistoryRepo
////					.getByCid(interviewsData.getCid());
//			CandidateInterviewStagesHistory can = new CandidateInterviewStagesHistory();
////			can.setCid(interviewsData.getCid());
//			can.setInterviewerName(interview.getInterviewerName());
//			can.setInterviewRound(interview.getInterviewRound());
//			can.setSchedule_interview_id(interviewsData.getId());
//			can.setScheduledOn(date);
//			can.setInterviewerGmail(interview.getInterviewerGmail());
////			byCid2.add(can);
//			 candidateInterviewStagesHistoryRepo.save(can);
//		}
//		return  null;
//
////		long cid = interview.getCid();
////		Candidate candidate = candidateService.getCandidateById(cid);
////		candidate.setSubStatus(CandidateSubStatus.INTERVIEW_SCHEDULED.toString());
////		Candidate saveCandidate = candidateService.saveCandidate(candidate);
////		System.out.println("Candidate object After First Round Scheduled " + saveCandidate.toString());
//
//		//return new ResponseEntity<Object>(can, HttpStatus.CREATED);
//
//	}

	@RequestMapping(value = "/updateInterviewById", method = RequestMethod.PUT)
	public @ResponseBody String updateInterview(@RequestParam Map<String, String> parameters,
			HttpServletRequest request) throws Exception, IOException {
		Long id = null;
		String interviewOn = null;
		String interviewRound = null;
		String interviewerName = null;
		String interviewType = null;
		Date scheduledOn = null;
		Date publishedOn = null;
		String interviewstatus = null;
		String teamsLink = null;
		String interviewerGmail = null;
		String candidate_id = null;
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		for (Map.Entry<String, String> pair : parameters.entrySet()) {
			System.out.println(String.format("Key (name) is: %s, Value is : %s", pair.getKey(), pair.getValue()));
			if (pair.getKey().equals("id")) {
				try {
					id = (Long.parseLong(pair.getValue()));
					continue;
				} catch (Exception e) {
					id = null;
					continue;
				}
			}
			if (pair.getKey().equals("interviewOn")) {
				try {
					interviewOn = (pair.getValue());
					continue;
				} catch (Exception e) {
					interviewOn = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("interviewRound")) {
				try {
					interviewRound = (pair.getValue());
					System.out.println("interviewRound = " + interviewRound);
					continue;
				} catch (Exception e) {
					interviewRound = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("interviewerName")) {
				try {
					interviewerName = (pair.getValue());
					continue;
				} catch (Exception e) {
					interviewerName = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("interviewType")) {
				try {
					interviewType = (pair.getValue());
					continue;
				} catch (Exception e) {
					interviewType = "NA";
					continue;
				}

			}
			if (pair.getKey().equals("interviewStatus")) {
				try {
					interviewstatus = (pair.getValue());
					continue;
				} catch (Exception e) {
					interviewstatus = "NA";
					continue;
				}
			}
			if (pair.getKey().equals("scheduledOn")) {
				try {
					scheduledOn = new Date(pair.getValue().trim());
					continue;
				} catch (Exception e) {
					scheduledOn = new Date();
					continue;
				}
			}
			if (pair.getKey().equals("teamsLink")) {
				try {
					teamsLink = (pair.getValue());
					continue;
				} catch (Exception e) {
					teamsLink = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("candidate_id") || pair.getKey().equals("cid")) {
				try {
					candidate_id = (pair.getValue());
					continue;
				} catch (Exception e) {
					candidate_id = "NA";
					continue;
				}
			}

			if (pair.getKey().equals("interviewerGmail")) {
				try {
					interviewerGmail = (pair.getValue());
					continue;
				} catch (Exception e) {
					interviewerGmail = "NA";
					continue;

				}
			}
		}

		long interviewID = (id);
		Optional<Interviews> interviewDetails = interviewsRepository.getByInterviewId(interviewID);

		JSONObject obj = new JSONObject();
		if (interviewDetails.isPresent()) {
			Interviews interviews = interviewDetails.get();
			if (interviewerName != null) {
				System.out.println("interviewerName: " + interviews.getInterviewerName());
				interviews.setInterviewerName(interviewerName);
				System.out.println("updated interviewerName  " + interviews.getInterviewerName());
			}
			if (interviewOn != null) {
				interviews.setInterviewOn(interviewOn);
			}

			if (interviewRound != null) {
				interviews.setInterviewRound(interviewRound);
				System.out.println("updated interviewRound  " + interviews.getInterviewRound());
			}
			if (interviewType != null) {
				System.out.println("interviewType: " + interviews.getInterviewType());
				interviews.setInterviewType(interviewType);
				System.out.println("updated interviewType  " + interviews.getInterviewType());
			}
			if (interviewerGmail != null) {
				System.out.println("interviewerGmail: " + interviews.getInterviewerGmail() + interviewerGmail);
				interviews.setInterviewerGmail(interviewerGmail);
				System.out.println("updated interviewerGmail  " + interviews.getInterviewerGmail());

			}
			if (interviewstatus != null) {
				interviews.setInterviewStatus(interviewstatus);
				System.out.println("updated interviewStatus  " + interviews.getInterviewStatus());
			}
			if (scheduledOn != null) {
				System.out.println("schedulingInterview: " + interviews.getScheduledOn());
				interviews.setScheduledOn(scheduledOn);
				System.out.println("updated scheduled  " + interviews.getScheduledOn());
			}
			if (teamsLink != null) {
				interviews.setTeamsLink(teamsLink);
			}
			// Save and flush the updated interview
			Interviews interviews1 = interviewsRepository.saveAndFlush(interviews);
			System.out.println("Interview updated and saved to database: " + interviews);

			if (interviews1 != null) {
				obj.put("status", "Interview  Schedule updated Successfully");
			} else {
				obj.put("status", "Failed to update interview");
			}
		} else {
			obj.put("status", "No records found");
			System.out.println("No interview found with ID: " + interviewID);
		}
		// Convert JSON object to string
		String jsonString = obj.toString();
		return jsonString;
	}

	@GetMapping("getInterviewById/{id}")
	public @ResponseBody String getUserById(@PathVariable("id") Long id) {
		int interviewId = id.intValue();
		Interviews byId = interviewsRepository.getById(interviewId);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(byId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	@GetMapping("getInterviewsByCandidateId/{id}")
	public @ResponseBody String getInterviewsByCandidateId(@PathVariable("id") Long id) {
		List<Interviews> interviews = interviewService.getInterviewsByCandidateId(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(interviews);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	@GetMapping("getAllInterviews")
	public @ResponseBody String getAllInterviewsAndInterviewScedule() {
		List<Interviews> interviews = interviewService.getAllInterviews();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(interviews);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	@GetMapping("getAllInterviewsByUserId")
	public String getAllINterviewsByUser(@RequestParam("employeeId") long employeeId,
			@RequestParam("password") String password) {
		Optional<User> userdetails = userRepository.findByemployeeId(employeeId);
		if (!userdetails.isPresent()) {
			return "No records found";
		}
		List<Interviews> listOfInterviews = interviewsRepository
				.getByinterviewerGmail(userdetails.get().getEmailaddress());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfInterviews);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	@GetMapping("getAllInterviewsWithCandidateName")
	public List<CandidateDTO> getAllInterviewsWithCandidateName() {
		return interviewService.getAllInterviewsWithCandidateName();
	}

//	@PutMapping("updateInterviewById")
//	public String updateInterview(@RequestBody Interviews interview) {
//		long millis = System.currentTimeMillis();
//		java.sql.Date date = new java.sql.Date(millis);
//		Interviews updatedInterview = interviewService.updateInterview(interview);
////		Candidate byId = candidateRepo.getByCid(updatedInterview.getCid());
//		CandidateInterviewStagesHistory can = candidateInterviewStagesHistoryRepo
//				.getByscheduleinterviewidANDinterviewRound(updatedInterview.getId(),
//						updatedInterview.getInterviewRound());
//		if (interview.getInterviewerName() != null) {
//			can.setInterviewerName(interview.getInterviewerName());
//		}
//		can.setScheduledOn(date);
//		can.setInterviewerGmail(interview.getInterviewerGmail());
//		candidateInterviewStagesHistoryRepo.save(can);
////		byId.setInterviews(updatedInterview);
////		emailSending(updatedInterview.getCid());
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		String jsonString = null;
//		try {
////			jsonString = mapper.writeValueAsString(byId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("/hiring/api/getCandidateDataByInyerviewId --> Result : " + e.getMessage());
//		}
//		logger.info("/hiring/api/getCandidateDataByInyerviewId --> Result : " + jsonString);
//		return jsonString;
//
//	}

//	@PutMapping("/updateInterviewById")
//	public String updateInterview(@RequestPart("interview") Interviews interview) {
//		long millis = System.currentTimeMillis();
//		java.sql.Date date = new java.sql.Date(millis);
//		Interviews updatedInterview = interviewService.updateInterview(interview);
////		Candidate byId = candidateRepo.getByCid(updatedInterview.getCid());
//		CandidateInterviewStagesHistory can = candidateInterviewStagesHistoryRepo
//				.getByscheduleinterviewidANDinterviewRound(updatedInterview.getId(),
//						updatedInterview.getInterviewRound());
//		if (interview.getInterviewerName() != null) {
//			can.setInterviewerName(interview.getInterviewerName());
//		}
//		can.setScheduledOn(date);
//		can.setInterviewerGmail(interview.getInterviewerGmail());
//		candidateInterviewStagesHistoryRepo.save(can);
////		byId.setInterviews(updatedInterview);
////		emailSending(updatedInterview.getCid());
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		String jsonString = null;
//		try {
////			jsonString = mapper.writeValueAsString(byId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("/hiring/api/getCandidateDataByInyerviewId --> Result : " + e.getMessage());
//		}
//		logger.info("/hiring/api/getCandidateDataByInyerviewId --> Result : " + jsonString);
//		return jsonString;
//
//	}

	@PutMapping("scheduling_interview")
	public String schedulingInterview(@RequestParam("Id") long id, @RequestBody Interviews interview) {
		Interviews interviews = interviewsRepository.getById(id);
		interviews.setInterviewRound(interview.getInterviewRound());
		interviews.setInterviewerName(interview.getInterviewerName());
		interviews.setInterviewerGmail(interview.getInterviewerGmail());
		interviews.setTeamsLink(interview.getTeamsLink());
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
//		interviews.setPublishedOn(date);
		interviewsRepository.save(interviews);
		CandidateInterviewStagesHistory can = new CandidateInterviewStagesHistory();
		Interviews byId = interviewsRepository.getById(id);
//		can.setCid(byId.getCid());
		can.setInterviewerName(byId.getInterviewerName());
		can.setInterviewRound(byId.getInterviewRound());
		can.setInterviewStatus(byId.getInterviewStatus());
		can.setSchedule_interview_id(id);
		candidateInterviewStagesHistoryRepo.save(can);
		return "Successfully created";

	}

	@GetMapping("/getInterviewListByEmployeeId")
	public String getInterviewsDataByEmployeeId(@RequestParam("employeeId") long employeeId) {
		List<Interviews> byEmployeeId = interviewsRepository.getByEmployeeId(employeeId);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(byEmployeeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSONObject obj = new JSONObject();
			obj.put("status", "Exception:" + e.toString());
			jsonString = obj.toString();
		}
		logger.info("/hiring/api/getAllCandidates --> Result : " + jsonString);

		return jsonString;

	}

	@DeleteMapping("deleteInterviewById/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
		interviewService.deleteInterview(id);
		return new ResponseEntity<>("Interview successfully deleted!", HttpStatus.OK);
	}

	@GetMapping("/getCandidateDataByInyerviewId")
	public String getCandidateDataByInyerviewId(@RequestParam("id") long id) {
		Candidate candidateDataByInyerviewId = candidateRepo.getCandidateDataByInyerviewId(id);
		List<CandidateInterviewStagesHistory> candidateInterviewHistory = candidateInterviewStagesHistoryRepo
				.getByCid(candidateDataByInyerviewId.getCid());
		List<InterviewFeedback> interviewFeedback = interviewFeedbackRepository.getByInyerviewId(id);
		Interviews interviewData = interviewsRepository.getById(id);
		interviewData.setInterviewFeedbacks(interviewFeedback);
		interviewData.setCandidateInterviewStagesHistory(candidateInterviewHistory);
		Candidate can = new Candidate();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(interviewData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("/hiring/api/getCandidateDataByInyerviewId --> Result : " + e.getMessage());
		}
		logger.info("/hiring/api/getCandidateDataByInyerviewId --> Result : " + jsonString);
		return jsonString;
	}

	public void emailSending(long cid, String cc) throws MessagingException {
		Candidate byId = candidateRepo.getById(cid);

		String to = byId.getEmailId();
		Optional<Interviews> interviewList = byId.getInterviews().stream()
				.max(Comparator.comparingLong(Interviews::getId));
		System.out.println("interviewList==========>" + interviewList);
		Interviews interview = interviewList.get();
//        List<String> list = new ArrayList<>();
//        list.add(to);
//        list.add(interviewerGmail);
//        MailProperties mailProperties = new MailProperties();
//        mailProperties.setToAddress(list);
		String subject = "Interview Scheduled for " + interview.getInterviewRound();
		String messasge = "Hi " + interview.getCandidate().getFullName() + ",\r\n" + "\r\n" + " \r\n"
				+ "Blocked your calendar for " + interview.getInterviewRound() + " round.\r\n" + "\r\n"
				+ "It would be a video call please join through laptop only.\r\n" + "\r\n"
				+ "Please carry your Pan card/ Aadhar card for verification purpose" + "\r\n" + "\r\n" + "\r\n"
				+ "_______________________________________________" + "\r\n" + "\r\n" + "\r\n"
				+ interview.getTeamsLink() + "\r\n" + "\r\n" + "\r\n" + "Microsoft Teams meeting" + "\r\n" + "\r\n"
				+ "Join on your computer, mobile app or room device" + "\r\n" + teamsLink;

		// String from = "zaktyson25@gmail.com";
		sendEmail(messasge, subject, to, cc);

	}

	private String sendEmail(String messasge, String subject, String to, String cc) throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		try {

			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setCc(cc);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(messasge);

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {

			e.printStackTrace();
		}

		return "Message Sent SuccessFully....";

	}

	@GetMapping("/getAllInterviewsData")
	public List<InterviewDto> getInterviewsList(@RequestParam Long id) {
		List<Object[]> Interviews = interviewsRepository.getAllInterviewData(id);
		List<InterviewDto> ListOfInterviews = new ArrayList<InterviewDto>();
		for (Object[] interviewDto : Interviews) {
			InterviewDto interview = new InterviewDto();
			interview.setId(((BigInteger) interviewDto[0]).longValue());
			interview.setCid(((BigInteger) interviewDto[1]).longValue());
			interview.setInterviewerName((String) interviewDto[2]);
			interview.setInterviewType(String.valueOf(interviewDto[3]));
			interview.setScheduledOn(String.valueOf(interviewDto[4]));
			interview.setInterviewOn(String.valueOf(interviewDto[5]));
			interview.setInterviewRound((String) interviewDto[6]);
			interview.setInterviewStatus((String) interviewDto[7]);
			interview.setRating((String) interviewDto[8]);
			interview.setRemarks((String) interviewDto[9]);
			ListOfInterviews.add(interview);

		}
		return ListOfInterviews;
	}

	@PostMapping("/send")
	public String sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		try {

			mimeMessageHelper.setTo(emailRequest.getToEmail());
			mimeMessageHelper.setCc(emailRequest.getCcEmail());
			mimeMessageHelper.setSubject(emailRequest.getSubject());
			mimeMessageHelper.setText(emailRequest.getBody());

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {

			e.printStackTrace();
		}

		return "Message Sent SuccessFully....";

	}

}
