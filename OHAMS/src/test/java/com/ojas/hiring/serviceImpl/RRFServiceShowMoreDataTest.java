package com.ojas.hiring.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ojas.hiring.commonutility.CommonConstants;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.exceptions.RrfIdNotFound;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.service.RRFService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RRFServiceShowMoreDataTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private RRFServiceImpl rrfServiceImpl;

	@Autowired
	private RRFService rrfService;

	@Autowired
	private WebApplicationContext context;

	@Mock
	private RRFRepo rrfRepo;

	@Mock
	private ObjectMapper mapper;

	@Mock
	private ObjectWriter objWritter;

	@Mock
	private CandidateRepo candidateRepo;

	@MockBean
	private EntityManager entityManager;

	@Mock
	private Query mockPreQuery;

	@Mock
	private Query mockMainQuery;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		MockitoAnnotations.initMocks(this);
//		rrfServiceImpl = new RRFServiceImpl();
//		rrfServiceImpl.setEntityManager(entityManager);
		ReflectionTestUtils.setField(rrfServiceImpl, "entityManager", entityManager);
	}

	@Test
	public void testShowMoreData_Success() {
		long id = 1L;
		RRF rrf = new RRF();
		rrf.setExperience("2-5");
		rrf.setPrimarySkills("Java");

		when(rrfRepo.findById(id)).thenReturn(Optional.of(rrf));

		List<Object[]> rmgDetails = Arrays.asList(new Object[] { 1, "John Doe", "Java", "4" },
				new Object[] { 2, "Jane Smith", "Spring", "3" });

		List<Integer> candidatesByRRFId = Arrays.asList(1);

		when(rrfRepo.getRMGDetailsForShowMore(2, 5, "Java")).thenReturn(rmgDetails);
		when(candidateRepo.getCandidatesByRRFId(id)).thenReturn(candidatesByRRFId);

		ResponseEntity<List<Map<String, String>>> response = rrfServiceImpl.showMoreData(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<Map<String, String>> resultList = response.getBody();
		assertNotNull(resultList);
		assertEquals(1, resultList.size());

		Map<String, String> firstResult = resultList.get(0);
		assertEquals("2", firstResult.get(CommonConstants.ID));
		assertEquals("Jane Smith", firstResult.get(CommonConstants.NAME));
		assertEquals("Spring", firstResult.get(CommonConstants.TECHNOLOGY));
		assertEquals("3", firstResult.get(CommonConstants.EXPERIENCE));

		verify(rrfRepo).findById(id);
		verify(rrfRepo).getRMGDetailsForShowMore(2, 5, "Java");
		verify(candidateRepo).getCandidatesByRRFId(id);
	}

	@Test
	public void testShowMoreData_ThrowsRrfIdNotFoundException() {
		long id = 1L;
		when(rrfRepo.findById(id)).thenReturn(Optional.empty());

		assertThrows(RrfIdNotFound.class, () -> rrfServiceImpl.showMoreData(id));
	}

	@Test
	void testGetRRFDetails_Success() throws Exception {
		long id = 1L;
		String expectedJson = "{\"id\":1,\"name\":\"Sample RRF\"}";
		RRF rrf = new RRF();
		rrf.setId(id);
		rrf.setCreatedBy("python");
		when(rrfRepo.findById(id)).thenReturn(Optional.of(rrf));
		when(rrfRepo.save(rrf)).thenReturn(rrf);
		when(mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)).thenReturn(mapper);
		when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objWritter);
		when(objWritter.writeValueAsString(rrf)).thenReturn(expectedJson);
		String result = rrfServiceImpl.fetchRRFDetails(id, expectedJson);
		assertNotNull(result);
		assertEquals(expectedJson, result);
	}

	@Test
	void testGetRRFDetails_Failure() throws Exception {
		long id = 1L;
		String expectedJson = "{\"id\":1,\"name\":\"Sample RRF\"}";
		assertThrows(RrfIdNotFound.class, () -> rrfServiceImpl.fetchRRFDetails(id, expectedJson));
	}

	
	 @Test
	    public void testDeleteRecordById_Success() {
	        long id = 1L;
	        
	        RRF rrf = new RRF();
	        rrf.setId(id);
	        Candidate candidate = new Candidate();
	        candidate.setVisibility(0);
	        when(rrfRepo.getById(id)).thenReturn(rrf);

	        String result = rrfServiceImpl.deleteRecordById(id);

	        assertEquals("Deleted Successfully", result);
	        assertEquals(0, rrf.getVisibility());
	        assertEquals(0, candidate.getVisibility());

	        verify(rrfRepo, times(1)).getById(id);
	        verify(rrfRepo, times(1)).save(rrf);
	    }

	    @Test
	    public void testDeleteRecordById_NoCandidates() {
	        long id = 1L;
	        RRF rrf = new RRF();
	        rrf.setId(id);
	        rrf.setCandidate(Collections.emptyList());

	        when(rrfRepo.getById(id)).thenReturn(rrf);

	        String result = rrfServiceImpl.deleteRecordById(id);

	        assertEquals("Deleted Successfully", result);
	        assertEquals(0, rrf.getVisibility());

	        verify(rrfRepo, times(1)).getById(id);
	        verify(rrfRepo, times(1)).save(rrf);
	    }

	    @Test
	    public void testDeleteRecordById_CandidateListContainsNull() {
	        long id = 1L;
	        RRF rrf = new RRF();
	        rrf.setId(id);
	        Candidate candidate = new Candidate();
	        candidate.setVisibility(0);
	        rrf.setCandidate(Arrays.asList(candidate, null));

	        when(rrfRepo.getById(id)).thenReturn(rrf);

	        String result = rrfServiceImpl.deleteRecordById(id);

	        assertEquals("Deleted Successfully", result);
	        assertEquals(0, rrf.getVisibility());
	        assertEquals(0, candidate.getVisibility());

	        verify(rrfRepo, times(1)).getById(id);
	        verify(rrfRepo, times(1)).save(rrf);
	    }
	    
	    @Test
		public void testDeleteRecordById() {
			assertThrows(RrfIdNotFound.class, () -> rrfServiceImpl.deleteRecordById(0));
		}
	
}
