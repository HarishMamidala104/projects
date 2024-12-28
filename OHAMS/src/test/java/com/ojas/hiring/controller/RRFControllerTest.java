package com.ojas.hiring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.util.ReflectionUtils;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ojas.hiring.Exception.BadRequestException;
import com.ojas.hiring.Exception.InternalServerException;
import com.ojas.hiring.entity.AnalyticalDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.entity.Uploads;
import com.ojas.hiring.exceptions.RrfIdNotFound;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.CustomersRepo;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.repo.UploadsRepo;
import com.ojas.hiring.serviceImpl.RRFServiceImpl;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false) 
@SpringBootTest
public class RRFControllerTest {

	@InjectMocks
	private RRFControllerV2 rrfControllerv2;

	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private RRFRepo rrfRepo;
    
    @Mock
    private RRF rrf;
    
	@Mock
	private Validator validator;
	
	@Mock
	private UploadsRepo uploadsRepo;
	
	@Mock
	private CandidateRepo candidateRepo;
	
	@Mock
	private CustomersRepo customersRepo;
	
	@Mock
	private RRFServiceImpl rrfServiceImpl;
	
	@Mock
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	   @Test
	    public void testPostRRFDetails_Success1() throws Exception {
	       
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
	        
	        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
	        
	        rrf.setId(1);
	      	        
	        when(rrfRepo.saveAndFlush(any(RRF.class))).thenReturn(rrf);
	        
	        Set<ConstraintViolation<RRF>> violations = new HashSet<>(); // Create an empty set
	        when(validator.validate(any(RRF.class))).thenReturn(violations);
	  
	        MockMultipartFile[] files = { new MockMultipartFile("rrqDoc", "filename.txt", "text/plain", "file content".getBytes()) };

	        Uploads uploads = new Uploads();
	        uploads.setFileName("shekar");
	        when(rrfServiceImpl.inputParameters(parameters)).thenReturn(rrf);
	        when(uploadsRepo.saveAndFlush(any())).thenReturn(uploads);
	        when(rrfServiceImpl.processRrfAndFiles(rrf, files)).thenReturn("RRF Successfully Created");
	        String result = rrfControllerv2.postRRFDetails(parameters, files, request);
	        assertEquals("RRF Successfully Created", result);
	    }
	   
	   @Test
        public void testUpdateRRFDetails() throws IOException, Exception {
        	
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
            parameters.put("location", "1");
            parameters.put("requirementName", "s");
            parameters.put("experience", "0-10");
            parameters.put("requirementStatus", "active");
            
            HttpServletRequest request = mock(HttpServletRequest.class);
            MockMultipartFile[] rrqDoc = new MockMultipartFile[] {
                    new MockMultipartFile("file", "testFile.txt", "text/plain", "This is the file content".getBytes())
                };
            RRF rrfCre = new RRF();
            rrfCre.setId(1111L);
            when(rrfRepo.getByRRFDataById(1111L)).thenReturn(rrfCre);
            when(rrfRepo.saveAndFlush(any())).thenReturn(rrfCre);
            
            Uploads up = new Uploads();
            up.setFileName("scr.tx");
            up.setFileSize(4000.000);
            up.setExtension(".txt");
            up.setLink(0L);
            up.setUploadedDate("08-09-9329");
            up.setUploadedFile("This is the file content".getBytes());
            up.setUploadedModule("dount");
            up.setUploadId(1L);
            when(uploadsRepo.findByLink(1111L)).thenReturn(Optional.of(up));
            when(uploadsRepo.saveAll(List.of(up))).thenReturn(List.of(up));
            String updateRRFDetails = rrfControllerv2.updateRRFDetails(parameters, "1111", rrqDoc, request);
            JSONObject jsonResponse = new JSONObject(updateRRFDetails);
            String status = jsonResponse.getString("status");
            assertEquals("RRF Successfully Updated", status);
        }
	   
	   
		@Test
		public void testGetAggregatedInterviewData() throws Exception {
			LocalDate startDate = LocalDate.now();
			LocalDate endDate = LocalDate.now();
			String technology = "java";
			String customer = "OJAS";
			List<AnalyticalDto> responseList = new ArrayList<>();

			when(rrfServiceImpl.fetchAggregatedInterviewData(technology, customer, startDate, endDate))
					.thenReturn(responseList);
			when(rrfServiceImpl.getInterviewsByTechnologyAndDates(technology, startDate, endDate))
					.thenReturn(responseList);
			when(rrfServiceImpl.getInterviewsByCustomerAndDates(customer, startDate, endDate)).thenReturn(responseList);
			when(rrfServiceImpl.getCustomDateInfo(startDate)).thenReturn(responseList);
			when(rrfServiceImpl.getInterviewDataForOneWeek()).thenReturn(responseList);
			when(rrfServiceImpl.getInterviewDataForOneMonth()).thenReturn(responseList);
			ResponseEntity<?> result = rrfControllerv2.getAggregatedInterviewData(technology, customer, endDate, endDate);
			assertNotNull(result);
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertEquals("No data found for the specified criteria.", result.getBody());
		}
		
		@Test
		public void testGetAggregatedInterviewDataHttpStatusOK() {
		    String technology = "Java";
		    String customer = "CustomerName";
		    LocalDate startDate = LocalDate.of(2023, 8, 1);
		    LocalDate endDate = LocalDate.of(2023, 8, 31);

		    List<AnalyticalDto> mockResponseList = new ArrayList<>();
		    mockResponseList.add(new AnalyticalDto()); 

		    when(rrfServiceImpl.fetchAggregatedInterviewData(technology, customer, startDate, endDate))
		        .thenReturn(mockResponseList);

		    ResponseEntity<?> result = rrfControllerv2.getAggregatedInterviewData(technology, customer, startDate, endDate);

		    assertNotNull(result);
		    assertEquals(HttpStatus.OK, result.getStatusCode()); 
		    assertNotNull(result.getBody()); 
		    assertEquals(mockResponseList, result.getBody()); 
		}

	   
	   @Test
	   public void testGetRRFUploadDetails() {
		   int id = 123;
		   String rrfUploadDetails = rrfControllerv2.getRRFUploadDetails(id);
		 assertEquals(null, rrfUploadDetails);
	   }
	   
	   @Test
       public void testDeleteRecord() {
		   Candidate candidate = new Candidate(); 
		   candidate.setCid(1);
		   candidate.setFullName("Shekhar");
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
		 
		    when(rrfRepo.getById(1234L)).thenReturn(rrf);
		   
		   String deleteRecord = rrfControllerv2.deleteRecord(1234L);
		   assertEquals("Deleted Successfully", deleteRecord);
       }
	
	   @Test 
       public void testGetRRFDetailsByFilter() throws Exception {
		   String primarySkills = "Java";
		   String customerName = "EY";
		   String hiringType = "Internal";
		   
		   RRF rrf = new RRF();
		   rrf.setId(1234L);
		  
		   List<RRF> list = new ArrayList<>();
		   list.add(rrf);
		   
		   when(rrfRepo.getDetails(customerName, primarySkills, hiringType)).thenReturn(list);
		   
		   List<RRF> rrfDetailsByFilter = rrfControllerv2.getRRFDetailsByFilter(primarySkills, customerName, hiringType);
		   assertNotNull(rrfDetailsByFilter);
}
	   @Test
	   public void testGetAllInternalCustomer() {
		   
		   String type = "internal";
		   List<String> list = new ArrayList<>();
		   list.add(type);
		   when(customersRepo.getAllInternalCustomers(type)).thenReturn(list);
		   List<String> allInternalCustomer = rrfControllerv2.getAllInternalCustomer(type);
		   assertNotNull(allInternalCustomer);
	   }
	   
		@Test
		@WithMockUser
		public void testGetMorePSAData_IsInternalServerError() throws Exception {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("id", "1");
			resultMap.put("name", "John Doe");
			resultMap.put("technology", "Java");
			resultMap.put("experience", "5-7");

			List<Map<String, String>> resultList = Arrays.asList(resultMap);

			when(rrfServiceImpl.showMoreData(111l)).thenReturn(new ResponseEntity<>(resultList, HttpStatus.OK));

			mockMvc.perform(
					get("/api/v2/showMoreData").param("id", "1").with(jwt().jwt(jwt -> jwt.claim("scope", "read"))))
					.andExpect(status().isInternalServerError()).andReturn();

		}
	   
		@Test
		public void testGetMorePSAData_Success() throws Exception {
			RRF rrf = new RRF();
			rrf.setId(111L);
			rrf.setExperience("5-7");
			rrf.setPrimarySkills("Java");

			List<Object[]> rmgDetails = new ArrayList<>();
			rmgDetails.add(new Object[] { 1, "John Doe", "Java", "5-7" });

			List<Integer> candidatesByRRFId = new ArrayList<>();

			List<Map<String, String>> resultList = new ArrayList<>();
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("id", "1");
			resultMap.put("name", "John Doe");
			resultMap.put("technology", "Java");
			resultMap.put("experience", "5-7");
			resultList.add(resultMap);

			when(rrfRepo.findById(111L)).thenReturn(Optional.of(rrf));
			when(rrfRepo.getRMGDetailsForShowMore(5, 7, "Java")).thenReturn(rmgDetails);
			when(candidateRepo.getCandidatesByRRFId(111L)).thenReturn(candidatesByRRFId);
			when(rrfServiceImpl.showMoreData(111L)).thenReturn(new ResponseEntity<>(resultList, HttpStatus.OK));
			ResponseEntity<List<Map<String, String>>> response = rrfControllerv2.getMorePSAData(111L);

			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertNotNull(response.getBody());
			assertEquals(1, response.getBody().size());

		}
		
		@Test
		void testGetRRFDetails_Success() throws Exception {
			long id = 1L;
	        String expectedJson = "{\"id\":1,\"name\":\"Sample RRF\"}";
	        when(rrfServiceImpl.fetchRRFDetails(id, null)).thenReturn(expectedJson);
	        String result  = rrfControllerv2.getRRFDetails(id);
	        assertNotNull(result);
	        assertEquals(expectedJson, result);
		}
		
		@Test
		void testGetRRFDetails_Failure() throws Exception {
			long id = 1L;
			 when(rrfServiceImpl.fetchRRFDetails(id, null))
				.thenThrow(new RuntimeException("Some error"));
	        assertThrows(InternalServerException.class, () -> rrfControllerv2.getRRFDetails(id));
		}
		
		@Test
		void testGetRRFUploadDetails_Sucess() throws Exception {
			int id = 1;
			String expectedJson = "{\"id\":1,\"name\":\"Sample RRF\"}";
			when(rrfServiceImpl.findUploadsByID("data_uploads", "upload_id,file_name", id)).thenReturn(expectedJson);
			String result = rrfControllerv2.getRRFUploadDetails(id);
			assertNotNull(result);
		}
		
		@Test
		void testGetRRFUploadDetails_Exception() throws Exception {
			int id = 1;
			when(rrfServiceImpl.findUploadsByID("data_uploads", "upload_id,file_name", id))
					.thenThrow(new RuntimeException("Some error"));
			assertThrows(BadRequestException.class, () -> rrfControllerv2.getRRFUploadDetails(id));
		}
		
		
		@Test
		void testDownloadFile_Success() throws Exception {
			long fileId = 1L;
			byte[] fileContent = "This is a test file".getBytes();
			Uploads mockUpload = new Uploads();
			mockUpload.setFileName("testfile.txt");
			mockUpload.setUploadedFile(fileContent);
			when(uploadsRepo.findById(fileId)).thenReturn(Optional.of(mockUpload));
			MockHttpServletResponse mockResponse = new MockHttpServletResponse();
			ResponseEntity<?> response = rrfControllerv2.downloadFile(fileId, mockResponse);

			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
			assertEquals(fileContent, response.getBody());
			assertEquals("attachment; filename=testfile.txt", mockResponse.getHeader(HttpHeaders.CONTENT_DISPOSITION));

			verify(uploadsRepo, times(1)).findById(fileId);
		}
		
		@Test
		void testDownloadFile_Failure() throws Exception {
			long fileId = 1L;
			assertThrows(BadRequestException.class, () -> rrfControllerv2.downloadFile(fileId, null));
		}
		
		@Test
		public void testDeleteRecord_Success() throws Exception {
			long id = 1L;
			when(rrfServiceImpl.deleteRecordById(id)).thenThrow(new RuntimeException("Some error"));
			assertThrows(BadRequestException.class, () -> rrfControllerv2.deleteRecord(id));
		}
		
		

}
