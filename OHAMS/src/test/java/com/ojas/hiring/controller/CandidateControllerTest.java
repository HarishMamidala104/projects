package com.ojas.hiring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.entity.Uploads;
import com.ojas.hiring.exceptions.CandidateChecking;
import com.ojas.hiring.exceptions.NoRecordFoundException;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.InterviewsRepository;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.repo.UploadsRepo;
import com.ojas.hiring.service.CandidateService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CandidateControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private HttpServletRequest request;

	@MockBean
	private RRFRepo rrfRepo;

//	@Mock
//	private RRF rrf;

	@Mock
	private Validator validator;

	@Mock
	private UploadsRepo uploadsRepo;

	@MockBean
	private CandidateRepo candidateRepo;

	@Mock
	private CandidateService candidateService;

	@Mock
	private InterviewsRepository interviewRepo;
	
	@Autowired
	private CandidateController candidateController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(candidateController).build();
	}

	@Test
	public void testPostCandidateDetails_Success() throws Exception {
		// Arrange
		Map<String, String> parameters = new HashMap<>();
		parameters.put("id", "1");
		parameters.put("createdBy", "John Doe");
		parameters.put("minExp", "2");
		parameters.put("maxExp", "5");
		parameters.put("budget", "20lpa");
		parameters.put("primarySkills", "java");
		parameters.put("secondarySkills", "react");
		parameters.put("jobLevel", "l1");
		parameters.put("jobDescription", "high");
		parameters.put("customerName", "Ey");
		parameters.put("hiringType", "internal");
		parameters.put("jobTitle", "senior");
		parameters.put("openPositions", "1");
		parameters.put("closedPositions", "1");
		parameters.put("jobType", "internal");
		parameters.put("priority", "high");
		parameters.put("modeOfWork", "remote");
		parameters.put("title", "internal");
		parameters.put("ownerOfRequirement", "shekhar");
		parameters.put("city", "hyd");
		parameters.put("country", "India");
		parameters.put("state", "Telangana");
		parameters.put("totalPositions", "1");
		parameters.put("requirementName", "ohams");
		parameters.put("location", "pune");
		parameters.put("interviewModes", "client");
		parameters.put("customerDetails", "Ey");
		parameters.put("hiringObjective", "ohams");
		parameters.put("jobTitle", "ohams");
		parameters.put("rrfId", "1");

		when(request.getRemoteAddr()).thenReturn("127.0.0.1");
		
		RRF rrf = new RRF();

		rrf.setId(1L);
		rrf.setBudget(12.00);
		rrf.setCity("eruiu");
		
		when(rrfRepo.save(rrf)).thenReturn(rrf);

		//when(rrfRepo.saveAndFlush(rrf)).thenReturn(rrf);
		when(rrfRepo.findById(1L)).thenReturn(Optional.of(rrf));
		Set<ConstraintViolation<RRF>> violations = new HashSet<>(); // Create an empty set
//	        when(validator.validate(any(RRF.class))).thenReturn(violations);

		MockMultipartFile[] files = {
				new MockMultipartFile("rrqDoc", "filename.txt", "text/plain", "file content".getBytes()) };

		Uploads uploads = new Uploads();
		uploads.setFileName("shekar");
		uploads.setLink(1L);
		long uploadId = 0L;
		byte[] fileContent = "file content".getBytes();
		String fileName = "filename.txt";
		String extension = "txt";
		double fileSize = 12.34; // Size in kilobytes
		String uploadedDate = "2023-08-30";
		String uploadedModule = "CANDIDATE";
		long link = 0L;
		Uploads mockUploads = new Uploads(uploadId, fileContent, fileName, extension, fileSize, uploadedDate, uploadedModule, link);
		Candidate can = new Candidate();
		can.setCid(1L);
		can.setAdditionalSkills("java");
		when(candidateRepo.saveAndFlush(any(Candidate.class))).thenReturn(can);
		when(uploadsRepo.saveAndFlush(uploads)).thenReturn(mockUploads);

		// Act
		String response = candidateController.postCandidateDetails(parameters, files, request);
		assertNotNull(response);
	}

//	@Test
	public void TestPostCandidateDetails_Failure() throws Exception {

		Map<String, String> parameters = new HashMap<>();
		parameters.put("hiringType", "internal");
		parameters.put("hiringManager", "shekar");
		parameters.put("position", "senior developer");
		parameters.put("recordAuthor", "soma");
		parameters.put("fullName", "shekar");
		parameters.put("mobileNo", "9876543210");
		parameters.put("emailId", "shekar@gamil.com");
		parameters.put("totalExperience", "5");
		parameters.put("currentlyWorkingAs", "software ");
		parameters.put("currentlyWorkingAt", "ojas");
		parameters.put("currentLocation", "hyd");
		parameters.put("preferredLocation", "hyd");
		parameters.put("ctcPA", "12lpa");
		parameters.put("noticePeriod", "30 days");
		parameters.put("currentlyServingNoticePeriod", "yes");
		parameters.put("comments", "good to go");
		parameters.put("createdBy", "java");
		parameters.put("offered_CTC", "16lpa");
		parameters.put("offered_Date", "25-03-2024");
		parameters.put("doj", "01-04-2024");
		parameters.put("rrfLink", "www.java.com");
		parameters.put("rrfId", "majx78h");
		parameters.put("vendor", "keap");
		parameters.put("additionalSkill", "react");
		parameters.put("cid", "123");
		parameters.put("source", "naukari");

		RRF rrf = new RRF();
		rrf.setId(1234L);
		rrf.setVisibility(1);

		Optional<RRF> rrfData = Optional.of(rrf);

		MockMultipartFile[] files = {
				new MockMultipartFile("rrqDoc", "filename.txt", "text/plain", "file content".getBytes()) };

		when(rrfRepo.findById(1234L)).thenReturn(rrfData);
		assertThrows(NoRecordFoundException.class, () -> {
			candidateController.postCandidateDetails(parameters, files, request);
		});
	}

//	@Test
	public void TestUpdateCandidateDetails_Success() throws Exception {
		String candidate_id = "1234";
		Map<String, String> parameters = new HashMap<>();
		parameters.put("hiringType", "internal");
		parameters.put("hiringManager", "shekar");
		parameters.put("position", "senior developer");
		parameters.put("recordAuthor", "soma");
		parameters.put("fullName", "shekar");
		parameters.put("mobileNo", "9876543210");
		parameters.put("emailId", "shekar@gamil.com");
		parameters.put("totalExperience", "5");
		parameters.put("currentlyWorkingAs", "software ");
		parameters.put("currentlyWorkingAt", "ojas");
		parameters.put("currentLocation", "hyd");
		parameters.put("preferredLocation", "hyd");
		parameters.put("ctcPA", "12lpa");
		parameters.put("noticePeriod", "30 days");
		parameters.put("currentlyServingNoticePeriod", "yes");
		parameters.put("comments", "good to go");
		parameters.put("createdBy", "java");
		parameters.put("offered_CTC", "16lpa");
		parameters.put("offered_Date", "25-03-2024");
		parameters.put("doj", "01-04-2024");
		parameters.put("rrfLink", "www.java.com");
		parameters.put("rrfId", "1234");
		parameters.put("vendor", "keap");
		parameters.put("additionalSkill", "react");
		parameters.put("cid", "123");
		parameters.put("source", "naukari");

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		Candidate candidate2 = new Candidate();
		candidate2.setCid(1);
		candidate2.setFullName("Bodraboina");
		candidate2.setVisibility(1);

		List<Candidate> list = new ArrayList<>();
		list.add(candidate);
		list.add(candidate2);

		RRF rrf = new RRF();
		rrf.setId(1234L);
		rrf.setVisibility(1);
		rrf.setCandidate(list);

		Optional<RRF> rrfData = Optional.of(rrf);

		Uploads uploads = new Uploads();
		uploads.setFileName("shekar");

		MockMultipartFile[] files = {
				new MockMultipartFile("rrqDoc", "filename.txt", "text/plain", "file content".getBytes()) };

		when(candidateRepo.saveAndFlush(any())).thenReturn(candidate);
		String updateCandidateDetails = candidateController.updateCandidateDetails(parameters, candidate_id, files,
				request);
		assertNotNull(updateCandidateDetails);
	}

//	@Test
	public void testGetAllCandidates() throws Exception {

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		Candidate candidate2 = new Candidate();
		candidate2.setCid(1);
		candidate2.setFullName("Bodraboina");
		candidate2.setVisibility(1);

		List<Candidate> list = new ArrayList<>();
		list.add(candidate);
		list.add(candidate2);
		when(candidateRepo.getCandidateDetails()).thenReturn(list);
		String allCandidates = candidateController.getAllCandidates();
		assertNotNull(allCandidates);
	}

//	@Test
	public void testGetAllCandidatesWithIntStatus() throws Exception {

		Interviews interviews = new Interviews();
		interviews.setId(123L);

		List<Interviews> lis = new ArrayList<Interviews>();
		lis.add(interviews);
		when(interviewRepo.findAll()).thenReturn(lis);
		String allCandidatesWithIntStatus = candidateController.getAllCandidatesWithIntStatus();
		assertNotNull(allCandidatesWithIntStatus);
	}

//	@Test
	public void testGetAllTagDetails() throws Exception {

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		Candidate candidate2 = new Candidate();
		candidate2.setCid(1);
		candidate2.setFullName("Bodraboina");
		candidate2.setVisibility(1);

		List<Candidate> list = new ArrayList<>();
		list.add(candidate);
		list.add(candidate2);

		when(candidateRepo.getByvisibility(1)).thenReturn(list);
		String allTagDetails = candidateController.getAllTagDetails();
		assertNotNull(allTagDetails);
	}

//	@Test
	public void testGetTagDetailsByEmployeeId() throws Exception {

		long employeeId = 1234L;

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		candidate.setEmployeeId(1234L);
		Candidate candidate2 = new Candidate();
		candidate2.setCid(1);
		candidate2.setFullName("Bodraboina");
		candidate2.setVisibility(1);

		List<Candidate> list = new ArrayList<>();
		list.add(candidate);
		list.add(candidate2);

		when(candidateRepo.getByvisibility(1)).thenReturn(list);

		String tagDetailsByEmployeeId = candidateController.getTagDetailsByEmployeeId(employeeId);
		assertNotNull(tagDetailsByEmployeeId);
	}

//	@Test
	public void TestCheckByEmailId_Success() throws Exception {
		String emailId = "shekar@gmail.com";

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		candidate.setEmployeeId(1234L);
		candidate.setMobileNo("9876543210");
		candidate.setEmailId(null);
		Optional<Candidate> can = Optional.of(candidate);

		when(candidateRepo.findByemailId(null)).thenReturn(can);
		ResponseEntity response = candidateController.checkByEmailId(emailId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

//	@Test
	public void TestCheckByEmailId_Failure() throws Exception {
		String emailId = "shekar@gmail.com";

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		candidate.setEmployeeId(1234L);
		candidate.setMobileNo("9876543210");
		candidate.setEmailId(null);
		Optional<Candidate> can = Optional.of(candidate);

		when(candidateRepo.findByemailId(any())).thenReturn(can);
		assertThrows(CandidateChecking.class, () -> {
			candidateController.checkByEmailId(emailId);
		});
	}

//	@Test
	public void TestCheckByMobileNumber_Success() throws Exception {
		String mobileNo = "9876543210";

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		candidate.setEmployeeId(1234L);
		candidate.setMobileNo(null);
		Optional<Candidate> can = Optional.of(candidate);

		when(candidateRepo.findBymobileNo(null)).thenReturn(can);
		ResponseEntity response = candidateController.checkByMobileNumber(mobileNo);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

//	@Test
	public void TestCheckByMobileNumber_Failure() throws Exception {
		String mobileNo = "9876543210";

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		candidate.setEmployeeId(1234L);
		candidate.setMobileNo(null);
		Optional<Candidate> can = Optional.of(candidate);

		when(candidateRepo.findBymobileNo(any())).thenReturn(can);
		assertThrows(CandidateChecking.class, () -> {
			candidateController.checkByMobileNumber(mobileNo);
		});
	}

//	@Test
	public void TestPostTagDetails() throws Exception {

		Candidate candidate = new Candidate();
		candidate.setCid(1);
		candidate.setFullName("Shekhar");
		candidate.setStatus("Select");
		candidate.setSubStatus("Onboarded");
		candidate.setEmployeeId(1234L);
		when(candidateRepo.save(any())).thenReturn(candidate);
		String result = candidateController.postTagDetails(candidate);
		assertEquals("Successfully Created Candidate details", result);
	}
}