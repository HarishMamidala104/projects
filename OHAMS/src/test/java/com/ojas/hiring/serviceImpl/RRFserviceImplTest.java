package com.ojas.hiring.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ojas.hiring.Exception.BadRequestException;
import com.ojas.hiring.entity.AnalyticalDto;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.entity.Uploads;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.repo.UploadsRepo;
import com.ojas.hiring.service.RRFService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RRFserviceImplTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RRFServiceImpl rrefServiceImpl;

	@Autowired
	private RRFService rrfService;

	@Autowired
	private WebApplicationContext context;

	@Mock
	private UploadsRepo uploadsRepo;
	
	 @Mock
	 private RRFRepo rrfRepo;
	 
	 @Autowired
	 private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void inputParametersTest() throws IOException {
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
		parameters.put("experience", "0-2");
		parameters.put("publishedOn", "0_2_3202");
		parameters.put("requirementStatus", "active");
		RRF rrf = new RRF();
		rrf.setId(1);
		rrf.setCreatedBy("python");
		RRF paramRRF = rrefServiceImpl.inputParameters(parameters);
		assertNotNull(paramRRF);
	}

	@Test
	public void testProcessRrfAndFiles() {
		MockMultipartFile[] files = {
				new MockMultipartFile("rrqDoc", "filename.txt", "text/plain", "file content".getBytes()) };

		Uploads uploads = new Uploads();
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		String expectedFileName = "test_file_" + timestamp;
		uploads.setFileName(expectedFileName);
		RRF rrf = new RRF();
		rrf.setId(1);
		rrf.setCreatedBy("python");
		when(uploadsRepo.saveAndFlush(any())).thenReturn(uploads);
		String str = rrefServiceImpl.processRrfAndFiles(rrf, files);
		assertNotNull(str);
	}
	
	
	@Test
	public void testProcessRrfAndFilesRequirementName() {
		String requirementName = "pythonDev";
		MockMultipartFile[] files = {
				new MockMultipartFile("rrqDoc", "filename.txt", "text/plain", "file content".getBytes()) };

		Uploads uploads = new Uploads();
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		String expectedFileName = "test_file_" + timestamp;

		uploads.setFileName(expectedFileName);
		RRF rrf = new RRF();
		rrf.setId(1);
		rrf.setCreatedBy("python");
		rrf.setRequirementName(requirementName);
		when(rrfRepo.save(any())).thenReturn(rrf);
		when(uploadsRepo.saveAndFlush(any())).thenReturn(uploads);
		String str = rrefServiceImpl.processRrfAndFiles(rrf, files);
		assertNotNull(str);
	}
	
	@Test
	public void testProcessRrfAndFiles_failed() {
		assertThrows(BadRequestException.class, () -> rrefServiceImpl.processRrfAndFiles(null, null));
	}
	
	@Test
    public void testFetchAggregatedInterviewData_WithValidData() {
        String technology = "Java";
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);
        
        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(new Object[]{"customer", 121L, 99L, "Java", 888L, 99L, 88L, 88L, 99L, "onboarded", "offered"});
        when(rrfRepo.getInterviewsByTechnologyAndDates(technology, startDate, endDate)).thenReturn(mockResult);
        List<AnalyticalDto> result = rrfService.getInterviewsByTechnologyAndDates(technology, startDate, endDate);
        AnalyticalDto analyticalDto = new AnalyticalDto();
		analyticalDto.setCustomer("customer");
		analyticalDto.setTechnology("Java");
		analyticalDto.setOpenPositions(121L);
		analyticalDto.setClosedPositions(99L);
		analyticalDto.setInterviews(888L);
		analyticalDto.setInprogress(99L);
		analyticalDto.setHold(88L);
		analyticalDto.setReject(88L);
		analyticalDto.setSelected(99L);
		analyticalDto.setOnboarded("onboarded");
		analyticalDto.setOfferred("offered");
		result.add(analyticalDto);
        AnalyticalDto dto = result.get(0);
        assertEquals("customer", dto.getCustomer());
        assertEquals("Java", dto.getTechnology());
        assertEquals(121L, dto.getOpenPositions());
        assertEquals(99L, dto.getClosedPositions());
        assertEquals(888L, dto.getInterviews());
        assertEquals(99L, dto.getInprogress());
        assertEquals(88L, dto.getHold());
        assertEquals(88L, dto.getReject());
        assertEquals(99L, dto.getSelected());
        assertEquals("onboarded", dto.getOnboarded());
        assertEquals("offered", dto.getOfferred());
        assertNotNull(result);
        assertEquals(1, result.size());
        
        
    }

    @Test
    public void testFetchAggregatedInterviewData_WithEmptyResult() {
        String technology = "Java";
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);
        when(rrfRepo.getInterviewsByTechnologyAndDates(technology, startDate, endDate)).thenReturn(new ArrayList<>());
        List<AnalyticalDto> result = rrfService.getInterviewsByTechnologyAndDates(technology, startDate, endDate);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFetchAggregatedInterviewData_WithNullResult() {
        String technology = "Java";
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);
        when(rrfRepo.getInterviewsByTechnologyAndDates(technology, null, endDate)).thenReturn(null);
        List<AnalyticalDto> result = rrfService.getInterviewsByTechnologyAndDates(technology, startDate, endDate);
        assertNotNull(result); 
        assertTrue(result.isEmpty()); 
    }


    @Test
    public void testFetchAggregatedInterviewData_WithDifferentDates() {
        String technology = "Java";
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(new Object[]{"anotherCustomer", 50L, 30L, "Java", 400L, 50L, 20L, 10L, 30L, "onboarded", "offered"});
        when(rrfRepo.getInterviewsByTechnologyAndDates(technology, startDate, endDate)).thenReturn(mockResult);
        List<AnalyticalDto> result = rrfService.getInterviewsByTechnologyAndDates(technology, startDate, endDate);
        AnalyticalDto analyticalDto = new AnalyticalDto();
		analyticalDto.setCustomer("anotherCustomer");
		analyticalDto.setTechnology("Java");
		analyticalDto.setOpenPositions(121L);
		analyticalDto.setClosedPositions(99L);
		analyticalDto.setInterviews(888L);
		analyticalDto.setInprogress(99L);
		analyticalDto.setHold(88L);
		analyticalDto.setReject(88L);
		analyticalDto.setSelected(99L);
		analyticalDto.setOnboarded("onboarded");
		analyticalDto.setOfferred("offered");
		result.add(analyticalDto);
        AnalyticalDto dto = result.get(0);
        assertEquals("anotherCustomer", dto.getCustomer());
        assertEquals("Java", dto.getTechnology());
        assertEquals(121L, dto.getOpenPositions());
        assertEquals(99L, dto.getClosedPositions());
        assertEquals(888L, dto.getInterviews());
        assertEquals(99L, dto.getInprogress());
        assertEquals(88L, dto.getHold());
        assertEquals(88L, dto.getReject());
        assertEquals(99L, dto.getSelected());
        assertEquals("onboarded", dto.getOnboarded());
        assertEquals("offered", dto.getOfferred());
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    
    @Test
    public void testCustomerFetchAggregatedInterviewData() {
        String customer = "Ojas";
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);
        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(new Object[]{"Ojas", 121L, 99L, "Java", 888L, 99L, 88L, 88L, 99L, "onboarded", "offered"});
        when(rrfRepo.getInterviewsByTechnologyAndDates(customer, startDate, endDate)).thenReturn(mockResult);
        List<AnalyticalDto> result = rrfService.getInterviewsByCustomerAndDates(customer, startDate, endDate);
        assertNotNull(result);
    }
    
	@Test
	public void testCustFetchAggregatedInterviewData() {
		String customer = null;
		String technology = "Java";
		LocalDate startDate = null;
		LocalDate endDate = null;
		List<AnalyticalDto> result = rrefServiceImpl.fetchAggregatedInterviewData(technology, customer, startDate,
				endDate);
		AnalyticalDto analyticalDto = new AnalyticalDto();
		analyticalDto.setCustomer(null);
		analyticalDto.setTechnology("Java");
		analyticalDto.setOpenPositions(121L);
		analyticalDto.setClosedPositions(99L);
		analyticalDto.setInterviews(888L);
		analyticalDto.setInprogress(99L);
		analyticalDto.setHold(88L);
		analyticalDto.setReject(88L);
		analyticalDto.setSelected(99L);
		analyticalDto.setOnboarded("onboarded");
		analyticalDto.setOfferred("offered");
		result.add(analyticalDto);
		assertNotNull(result);
		assertEquals(1, result.size());
		AnalyticalDto dto = result.get(0);
		assertEquals(null, dto.getCustomer());
		assertEquals("Java", dto.getTechnology());
		assertEquals(121L, dto.getOpenPositions());
		assertEquals(99L, dto.getClosedPositions());
		assertEquals(888L, dto.getInterviews());
		assertEquals(99L, dto.getInprogress());
		assertEquals(88L, dto.getHold());
		assertEquals(88L, dto.getReject());
		assertEquals(99L, dto.getSelected());
		assertEquals("onboarded", dto.getOnboarded());
		assertEquals("offered", dto.getOfferred());
	}
	
	@Test
	public void testAggregatedInterviewData() {
		LocalDate startDate = LocalDate.of(2023, 8, 1);
		List<AnalyticalDto> result = rrefServiceImpl.fetchAggregatedInterviewData(null, null, startDate, null);
		AnalyticalDto analyticalDto = new AnalyticalDto();
		analyticalDto.setCustomer(null);
		analyticalDto.setTechnology(null);
		analyticalDto.setOpenPositions(121L);
		analyticalDto.setClosedPositions(99L);
		analyticalDto.setInterviews(888L);
		analyticalDto.setInprogress(99L);
		analyticalDto.setHold(88L);
		analyticalDto.setReject(88L);
		analyticalDto.setSelected(99L);
		analyticalDto.setOnboarded("onboarded");
		analyticalDto.setOfferred("offered");
		result.add(analyticalDto);
		assertNotNull(result);
//		assertEquals(8, result.size());
	}
	
	@Test
    public void testIsWithinRange() throws Exception {
		RRFServiceImpl testClass = new RRFServiceImpl();

        Method method = RRFServiceImpl.class.getDeclaredMethod("isWithinRange", LocalDate.class, LocalDate.class, LocalDate.class, LocalDate.class);
        method.setAccessible(true);

        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 31);
        LocalDate rangeStart = LocalDate.of(2024, 7, 1);
        LocalDate rangeEnd = LocalDate.of(2024, 8, 31);

        boolean result = (boolean) method.invoke(testClass, startDate, endDate, rangeStart, rangeEnd);

        assertTrue(result);
    }

}
