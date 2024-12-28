package com.ojas.hiring.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.CandidateInterviewStagesHistory;
import com.ojas.hiring.entity.InterviewFeedback;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.entity.Uploads;
import com.ojas.hiring.repo.CandidateInterviewStagesHistoryRepo;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.InterviewsRepository;
import com.ojas.hiring.repo.UploadsRepo;
import com.ojas.hiring.service.InterviewFeedbackService;
import com.ojas.hiring.service.InterviewService;

@RestController
@RequestMapping("/api")
public class InterviewFeedbackController {

	@Autowired
	private InterviewService interview;
	@Autowired
	private InterviewFeedbackService interviewFeedbackService;
	@Autowired
	private InterviewsRepository interviewsRepository;

	@Autowired
	CandidateRepo candidateRepo;
	@Autowired
	UploadsRepo uploadsRepo;
	@Autowired
	private CandidateInterviewStagesHistoryRepo candidateInterviewStagesHistoryRepo;
//=========================================================================================================
	/*
	 * @PostMapping("/createInterviewFeedback") public InterviewFeedback
	 * createInterviewFeedback(@RequestBody InterviewFeedback interviewFeedback) {
	 * long millis = System.currentTimeMillis(); Date date = new Date(millis);
	 * interviewFeedback.setPublishedOn(date); InterviewFeedback t =
	 * interviewFeedbackService.createEmployee(interviewFeedback); long interview_id
	 * = interviewFeedback.getInterview_id(); Interviews byId =
	 * interviewsRepository.getById(interview_id);
	 * 
	 * CandidateInterviewStagesHistory bySchedulenterviewidAndInterviewRound =
	 * candidateInterviewStagesHistoryRepo
	 * .getByscheduleinterviewidANDinterviewRound(interviewFeedback.getInterview_id(
	 * ), byId.getInterviewRound());
	 * 
	 * if(bySchedulenterviewidAndInterviewRound != null) {
	 * bySchedulenterviewidAndInterviewRound.setInterviewerName(byId.
	 * getInterviewerName());
	 * bySchedulenterviewidAndInterviewRound.setInterviewerGmail(byId.
	 * getInterviewerGmail());
	 * bySchedulenterviewidAndInterviewRound.setInterviewStatus(interviewFeedback.
	 * getStatus()); candidateInterviewStagesHistoryRepo.save(
	 * bySchedulenterviewidAndInterviewRound); } else {
	 * CandidateInterviewStagesHistory newHistorObj = new
	 * CandidateInterviewStagesHistory();
	 * newHistorObj.setInterviewerName(byId.getInterviewerName());
	 * newHistorObj.setInterviewStatus(interviewFeedback.getStatus());
	 * candidateInterviewStagesHistoryRepo.save(newHistorObj); } String string =
	 * interviewFeedbackService.saveStatus(interviewFeedback); long cid =
	 * byId.getCandidate().getCid();
	 * 
	 * Candidate byCid = candidateRepo.getByCid(cid);
	 * byCid.setSubStatus(byId.getInterviewRound()+"_".concat(interviewFeedback.
	 * getStatus())); Candidate save = candidateRepo.save(byCid);
	 * byId.setInterviewStatus(interviewFeedback.getStatus());
	 * interviewsRepository.saveAndFlush(byId); return t; }
	 * 
	 */

	// ===============================================================================================================

	/*
	 * @PostMapping("/createInterviewFeedback") public String
	 * createInterviewFeedback(@RequestBody InterviewFeedback
	 * interviewFeedback,@RequestParam(value = "fedDoc", required = false)
	 * MultipartFile[] fedDoc, HttpServletRequest request) throws Exception,
	 * IOException { long millis = System.currentTimeMillis(); Date date = new
	 * Date(millis); interviewFeedback.setPublishedOn(date); InterviewFeedback t =
	 * interviewFeedbackService.createEmployee(interviewFeedback); long interview_id
	 * = interviewFeedback.getInterview_id(); Interviews byId =
	 * interviewsRepository.getById(interview_id);
	 * 
	 * CandidateInterviewStagesHistory bySchedulenterviewidAndInterviewRound =
	 * candidateInterviewStagesHistoryRepo
	 * .getByscheduleinterviewidANDinterviewRound(interviewFeedback.getInterview_id(
	 * ), byId.getInterviewRound());
	 * 
	 * if(bySchedulenterviewidAndInterviewRound != null) {
	 * bySchedulenterviewidAndInterviewRound.setInterviewerName(byId.
	 * getInterviewerName());
	 * bySchedulenterviewidAndInterviewRound.setInterviewerGmail(byId.
	 * getInterviewerGmail());
	 * bySchedulenterviewidAndInterviewRound.setInterviewStatus(interviewFeedback.
	 * getStatus()); candidateInterviewStagesHistoryRepo.save(
	 * bySchedulenterviewidAndInterviewRound); } else {
	 * CandidateInterviewStagesHistory newHistorObj = new
	 * CandidateInterviewStagesHistory();
	 * newHistorObj.setInterviewerName(byId.getInterviewerName());
	 * newHistorObj.setInterviewStatus(interviewFeedback.getStatus());
	 * candidateInterviewStagesHistoryRepo.save(newHistorObj); } String string =
	 * interviewFeedbackService.saveStatus(interviewFeedback); long cid =
	 * byId.getCandidate().getCid();
	 * 
	 * Candidate byCid = candidateRepo.getByCid(cid);
	 * byCid.setSubStatus(byId.getInterviewRound()+"_".concat(interviewFeedback.
	 * getStatus())); Candidate save = candidateRepo.save(byCid);
	 * byId.setInterviewStatus(interviewFeedback.getStatus());
	 * interviewsRepository.saveAndFlush(byId);
	 * 
	 * Date publishedOn = t.getPublishedOn(); // Assuming this returns a Date object
	 * SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy"); String s =
	 * sdf.format(publishedOn); String jsonString=null; // process file if (fedDoc
	 * != null) { List<Uploads> uploads = new ArrayList<Uploads>();
	 * 
	 * for (int i = 0; i < fedDoc.length; i++) { long bytes = fedDoc[i].getSize();
	 * double kilobytes = (bytes / 1024); double megabytes = (kilobytes / 1024);
	 * System.out.println("---------------------Bytes-------------------" +
	 * kilobytes); if (bytes > 0) { String mimeType = fedDoc[i].getContentType();
	 * String fileName = fedDoc[i].getOriginalFilename(); String fileExtension =
	 * FilenameUtils.getExtension(fedDoc[i].getOriginalFilename()); byte[]
	 * fileContent = fedDoc[i].getBytes();
	 * 
	 * Uploads file = new Uploads(0, fileContent, fileName, fileExtension,
	 * kilobytes, s, "FEEDBACK", t.getId()); uploads.add(file); } } for (int i = 0;
	 * i < uploads.size(); i++) { uploads.get(i).setLink(t.getId()); Uploads u =
	 * uploadsRepo.saveAndFlush(uploads.get(i)); } }
	 * 
	 * JSONObject obj = new JSONObject(); obj.put("status",
	 * "FeedBack Successfully Created"); jsonString = obj.toString();
	 * 
	 * 
	 * return jsonString;
	 * 
	 * 
	 * }
	 * 
	 */

	@PostMapping("/createInterviewFeedback")
	public @ResponseBody String createInterviewFeedback(@RequestParam Map<String, String> parameters,
			@RequestParam(value = "fedDoc", required = false) MultipartFile[] fedDoc, HttpServletRequest request)
			throws Exception, IOException {

		long interview_id = 0;
		String primarySkill = null;
		String overallRatings = null;
		String overallFeedback = null;
		String status = null;
		// Date publishedOn=null;
		System.out.println("values-------------------------------------------");
		parameters.entrySet().forEach(System.out::println); // .forEach((k,v)->System.out.println(k +"--"+v));

		for (Map.Entry<String, String> pair : parameters.entrySet()) {
			System.out.println(String.format("Key (name) is: %s, Value is : %s", pair.getKey(), pair.getValue()));
			if (pair.getKey().equals("primarySkill")) {
				try {
					primarySkill = pair.getValue();
					continue;
				} catch (Exception e) {
					primarySkill = null;
					continue;
				}
			}

			if (pair.getKey().equals("overallRating")) {
				try {
					overallRatings = pair.getValue();
					continue;
				} catch (Exception e) {
					overallRatings = null;
					continue;
				}
			}

			if (pair.getKey().equals("status")) {
				try {
					status = pair.getValue();
					continue;
				} catch (Exception e) {
					status = null;
					continue;
				}
			}

			if (pair.getKey().equals("overallFeedback")) {
				try {
					overallFeedback = pair.getValue();
					continue;
				} catch (Exception e) {
					overallFeedback = null;
					continue;
				}
			}
			System.out.println("values-------------------------------------------");
			System.out.println("pair.getKey()" + pair.getKey());
			if (pair.getKey().equals("interview_id")) {
				try {
					interview_id = Long.parseLong(pair.getValue());
					continue;
				} catch (Exception e) {
					interview_id = 0;
					continue;
				}
			}

			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy"); if
			 * (pair.getKey().equals("publishedOn")) { try { publishedOn =
			 * sdf.parse(pair.getValue().trim()); System.out.println("published" +
			 * publishedOn);
			 * 
			 * System.out.println("The published on property : this from the DB " +
			 * publishedOn); continue; } catch (Exception e) { // //
			 * System.out.println("the updated date is "+byId.getPublishedOn()
			 * +"this is updated database" ); continue; } }
			 */

		} // end of for loop

		InterviewFeedback interviewFeedback = new InterviewFeedback();
		interviewFeedback.setInterview_id(interview_id);
		interviewFeedback.setOverallFeedback(overallFeedback);
		interviewFeedback.setOverallRating(overallRatings);
		// interviewFeedback.setPrimarySkill(primarySkill);
		// interviewFeedback.setRemarks(status);
		interviewFeedback.setStatus(status);
		// interviewFeedback.setPublishedOn(publishedOn);
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		interviewFeedback.setPublishedOn(date);
		System.out.println(
				interviewFeedback.getPublishedOn() + "============================================================");

//		long millis = System.currentTimeMillis();
//		Date date = new Date(millis);
//		interviewFeedback.setPublishedOn(date);
		InterviewFeedback t = interviewFeedbackService.createEmployee(interviewFeedback);
		System.out.println(
				t.getId() + "==============================================================================" + t);
		// interview_id = interviewFeedback.getInterview_id();
		Interviews byId = interviewsRepository.getById(interview_id);

		CandidateInterviewStagesHistory bySchedulenterviewidAndInterviewRound = candidateInterviewStagesHistoryRepo
				.getByscheduleinterviewidANDinterviewRound(interviewFeedback.getInterview_id(),
						byId.getInterviewRound());

		if (bySchedulenterviewidAndInterviewRound != null) {
			bySchedulenterviewidAndInterviewRound.setInterviewerName(byId.getInterviewerName());
			bySchedulenterviewidAndInterviewRound.setInterviewerGmail(byId.getInterviewerGmail());
			bySchedulenterviewidAndInterviewRound.setInterviewStatus(interviewFeedback.getStatus());
			candidateInterviewStagesHistoryRepo.save(bySchedulenterviewidAndInterviewRound);
		} else {
			CandidateInterviewStagesHistory newHistorObj = new CandidateInterviewStagesHistory();
			newHistorObj.setInterviewerName(byId.getInterviewerName());
			newHistorObj.setInterviewStatus(interviewFeedback.getStatus());
			candidateInterviewStagesHistoryRepo.save(newHistorObj);
		}
		String string = interviewFeedbackService.saveStatus(interviewFeedback);
		long cid = byId.getCandidate().getCid();

		Candidate byCid = candidateRepo.getByCid(cid);
		byCid.setSubStatus(byId.getInterviewRound() + "_".concat(interviewFeedback.getStatus()));
		Candidate save = candidateRepo.save(byCid);
		byId.setInterviewStatus(interviewFeedback.getStatus());
		interviewsRepository.saveAndFlush(byId);

		Date publishedOn1 = t.getPublishedOn(); // Assuming this returns a Date object
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String s = sdf.format(publishedOn1);
		String jsonString = null;
// process file
		if (fedDoc != null) {
			System.out.println(fedDoc + "===========================================");
			List<Uploads> uploads = new ArrayList<Uploads>();

			for (int i = 0; i < fedDoc.length; i++) {
				long bytes = fedDoc[i].getSize();
				double kilobytes = (bytes / 1024);
				double megabytes = (kilobytes / 1024);
				System.out.println("---------------------Bytes-------------------" + kilobytes);
				if (bytes > 0) {
					String mimeType = fedDoc[i].getContentType();
					String fileName = fedDoc[i].getOriginalFilename();
					String fileExtension = FilenameUtils.getExtension(fedDoc[i].getOriginalFilename());
					byte[] fileContent = fedDoc[i].getBytes();

					Uploads file = new Uploads(0, fileContent, fileName, fileExtension, kilobytes, s, "FEEDBACK",
							t.getId());
					System.out.println(file + "===============================");
					uploads.add(file);
				}
			}
			for (int i = 0; i < uploads.size(); i++) {
				uploads.get(i).setLink(t.getId());
				Uploads u = uploadsRepo.saveAndFlush(uploads.get(i));
			}
		}

		JSONObject obj = new JSONObject();
		obj.put("status", "FeedBack Successfully Created");
		jsonString = obj.toString();

		return jsonString;

	}

	@GetMapping("/getAllInterviewFeedbacks")
	public List<InterviewFeedback> getAllInterviewFeedbacks() {
//		List<Interviews> interviewByFeedBackData = interviewsRepository.getInterviewByFeedBackData();
		return interviewFeedbackService.getAllInterviewFeedback();
//		return interviewByFeedBackData;
	}

	@GetMapping("/getInterviewFeedbackById/{id}")
	public ResponseEntity<Optional<InterviewFeedback>> getInterviewFeedbackById(@PathVariable(value = "id") Long id) {
		Optional<InterviewFeedback> getinterviewFeedbackById = interviewFeedbackService.getInterviewFeedbackById(id);

		return ResponseEntity.ok().body(getinterviewFeedbackById);
	}

	@PutMapping("/updateInterviewFeedbackById/{id}")
	public ResponseEntity<InterviewFeedback> updateInterviewFeedback(@PathVariable(value = "id") Long id,
			@RequestBody InterviewFeedback interviewFeedbackDetails) {
		Optional<InterviewFeedback> updateinterviewFeedbackById = interviewFeedbackService.getInterviewFeedbackById(id);
		InterviewFeedback updateinterviewFeedback = updateinterviewFeedbackById.get();

		updateinterviewFeedback.setInterview_id(interviewFeedbackDetails.getInterview_id());
		updateinterviewFeedback.setStatus(interviewFeedbackDetails.getStatus());
		updateinterviewFeedback.setOverallFeedback(interviewFeedbackDetails.getOverallFeedback());
		updateinterviewFeedback.setOverallRating(interviewFeedbackDetails.getOverallRating());
		updateinterviewFeedback.setPrimarySkill(interviewFeedbackDetails.getPrimarySkill());
		updateinterviewFeedback.setSkillRatings(interviewFeedbackDetails.getSkillRatings());
		updateinterviewFeedback.setSkillFeedbacks(interviewFeedbackDetails.getSkillFeedbacks());

		final InterviewFeedback updatedInterviewFeedback = interviewFeedbackService
				.createEmployee(updateinterviewFeedback);

		return ResponseEntity.ok(updatedInterviewFeedback);
	}

	@DeleteMapping("/deleteInterviewFeedbackById/{id}")
	public void deleteInterviewFeedback(@PathVariable(value = "id") Long id) {

		interviewFeedbackService.deleteInterviewFeedback(id);
	}

}
