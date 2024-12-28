package com.ojas.hiring.serviceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.CandidateDetailsDto;
import com.ojas.hiring.dto.CandidateDto;
import com.ojas.hiring.dto.GetAllCandidatesDto;
import com.ojas.hiring.dto.GetAllCandidatesDtoInfo;
import com.ojas.hiring.entity.AnalyticalDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.service.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepo candidateRepo;

	@Autowired
	CandidateService candidateService;

	@Autowired
	RRFRepo rrfRepo;

	@Override
	public Candidate updateCandidate(String cid, Candidate candidate) {
		Optional<Candidate> byId = candidateRepo.findById(Long.parseLong(cid));
		System.out.println(byId);
		Candidate dbObj = byId.get();
		if (candidate.getCurrentLocation() != null) {
			dbObj.setCurrentLocation(candidate.getCurrentLocation());
		}
		if (candidate.getComments() != null) {
			dbObj.setComments(candidate.getComments());
		}
		if (candidate.getCtcPA() > 0.0) {
			dbObj.setCtcPA(candidate.getCtcPA());
		}
		if (candidate.getCurrentlyWorkingAs() != null) {
			dbObj.setCurrentlyWorkingAs(candidate.getCurrentlyWorkingAs());
		}
		if (candidate.getCurrentlyWorkingAt() != null) {
			dbObj.setCurrentlyWorkingAt(candidate.getCurrentlyWorkingAt());
		}
		if (candidate.getEmailId() != null) {
			dbObj.setEmailId(candidate.getEmailId());
		}
		if (candidate.getExpectedCtcPa() > 0.0) {
			dbObj.setExpectedCtcPa(candidate.getExpectedCtcPa());
		}
//		if (candidate.getFullName() != null) {
//			dbObj.setFullName(candidate.getFullName());
//		}
//		if (candidate.getHiringManager() != null) {
//			dbObj.setHiringManager(candidate.getHiringManager());
//		}
//		if (candidate.getHiringType() != null) {
//			dbObj.setHiringType(candidate.getHiringType());
//		}
		if (candidate.getMobileNo() != null) {
			dbObj.setMobileNo(candidate.getMobileNo());
		}
		if (candidate.getNoticePeriod() > 0.0) {
			dbObj.setNoticePeriod(candidate.getNoticePeriod());
		}
//		if (candidate.getOffered_CTC() > 0.0) {
//			dbObj.setOffered_CTC(candidate.getOffered_CTC());
//		}
		if (candidate.getPreferredLocation() != null) {
			dbObj.setPreferredLocation(candidate.getPreferredLocation());
		}
//		if (candidate.getPublishedOn() != null) {
//			dbObj.setPublishedOn(candidate.getPublishedOn());
//		}
//		if (candidate.getRemark() != null) {
//			dbObj.setRemark(candidate.getRemark());
//		}
//		if (candidate.getSkillSet() != null) {
//			dbObj.setSkillSet(candidate.getSkillSet());
//		}
		if (candidate.getStatus() != null) {
			dbObj.setStatus(candidate.getStatus());
		}
		if (candidate.getAvailability() != null) {
			dbObj.setAvailability(candidate.getAvailability());
		}
		if (candidate.getNoticePeriod() != 0.0) {
			dbObj.setNoticePeriod(candidate.getNoticePeriod());
		}
		if (candidate.getTotalExperience() > 0) {
			dbObj.setTotalExperience(candidate.getTotalExperience());
		}

		Candidate updatedCandidate = candidateRepo.save(dbObj);
		return updatedCandidate;
	}
	
	

	@Override
	public Candidate getCandidateById(long cid) {
		Candidate candidate = candidateRepo.getById(cid);
		return candidate;
	}

	@Override
	public Candidate saveCandidate(Candidate candidate) {
		Candidate candidateObj = candidateRepo.save(candidate);
		return candidateObj;
	}

	@Override
	public ResponseEntity<String> getInterviewsByCustomerAndDates(LocalDate startDate, LocalDate endDate) {

		Map<String, String> map = new HashMap<>();
		List<Object[]> result = rrfRepo.getInterviewsByCustomersAndDates(startDate, endDate);

//		List<AnalyticalDto> interviewDataList = new ArrayList<>();

		for (Object[] objects : result) {
			map.put(objects[0].toString(), objects[1].toString());
		}

		return new ResponseEntity(map, HttpStatus.OK);
	}

	@Override
	public List<AnalyticalDto> getInterviewsByStatusAndDates(LocalDate startDate, LocalDate endDate) {
		List<Object[]> result = candidateRepo.getAllCandidates(startDate, endDate);

		List<AnalyticalDto> interviewDataList = new ArrayList<>();

		for (Object[] o : result) {
			String customer = o[0].toString();
			String tecnology = o[1].toString();
			Long cid = Long.valueOf(o[2].toString());
			Long inProgress = Long.valueOf(o[3].toString());
			Long hold = Long.valueOf(o[4].toString());
			Long reject = Long.valueOf(o[5].toString());
			Long selected = Long.valueOf(o[6].toString());

			AnalyticalDto analyticalDto = new AnalyticalDto();
			analyticalDto.setCustomer(customer);
			analyticalDto.setTechnology(tecnology);
			analyticalDto.setInterviews(cid);
			analyticalDto.setInprogress(inProgress);
			analyticalDto.setHold(hold);
			analyticalDto.setReject(reject);
			analyticalDto.setSelected(selected);
			interviewDataList.add(analyticalDto);
		}

		return interviewDataList;
	}

	/*
	 * public List<GetAllCandidatesDto> getAllRRFValues() { List<Object[]> results =
	 * candidateRepo.findAllRRFWithCandidates(); List<GetAllCandidatesDto> dtos =
	 * new ArrayList<>();
	 * 
	 * for (Object[] result : results) { GetAllCandidatesDto dto = new
	 * GetAllCandidatesDto(); dto.setRrfId(((BigInteger) result[0]).longValue());
	 * dto.setJobDescription((String) result[1]); dto.setCustomerName((String)
	 * result[2]); dto.setPrimarySkills((String) result[3]);
	 * dto.setHiringType((String) result[4]); dto.setJobLevel((String) result[5]);
	 * dto.setMinExp((Double) result[6]); dto.setMaxExp((Double) result[7]);
	 * dto.setBudget((Double) result[8]); dto.setSecondarySkills((String)
	 * result[9]); dto.setEmailAddress((String) result[10]);
	 * dto.setEmployeeId(((BigInteger) result[11]).longValue());
	 * dto.setJobTitle((String) result[12]); dto.setJobType((String) result[13]);
	 * dto.setPriority((String) result[14]); dto.setModeOfWork((String) result[15]);
	 * dto.setTitle((String) result[16]); dto.setOwnerOfRequirement((String)
	 * result[17]); dto.setCity((String) result[18]); dto.setState((String)
	 * result[19]); dto.setLocation((String) result[20]); dto.setExperience((String)
	 * result[21]); dto.setRequirementName((String) result[22]);
	 * dto.setVisibility((Integer) result[23]); dto.setOpenPositions((Integer)
	 * result[24]); dto.setClosedPositions((Integer) result[25]);
	 * dto.setCandidateCount((Integer) result[26]);
	 * 
	 * // Handling the 'publishedOn' field carefully Object publishedOnValue =
	 * result[27]; if (publishedOnValue != null) { if (publishedOnValue instanceof
	 * String) { dto.setPublishedOn((String) publishedOnValue); } else if
	 * (publishedOnValue instanceof Date) { dto.setPublishedOn(new
	 * SimpleDateFormat("yyyy-MM-dd").format((Date) publishedOnValue)); } else {
	 * dto.setPublishedOn(publishedOnValue.toString()); } } else {
	 * dto.setPublishedOn(null); }
	 * 
	 * dto.setCreatedBy((String) result[28]); dto.setTotalPositions((Integer)
	 * result[29]); dto.setRequirementStatus((String) result[30]);
	 * 
	 * // Candidate DTO handling CandidateDto candidateDto = new CandidateDto();
	 * candidateDto.setCid(((BigInteger) result[31]).longValue());
	 * candidateDto.setFullName((String) result[32]);
	 * candidateDto.setMobileNo((String) result[33]);
	 * candidateDto.setEmailId((String) result[34]);
	 * candidateDto.setTotalExperience((Double) result[35]);
	 * candidateDto.setCurrentlyWorkingAs((String) result[36]);
	 * candidateDto.setCurrentlyWorkingAt((String) result[37]);
	 * candidateDto.setCurrentLocation((String) result[38]);
	 * candidateDto.setPreferredLocation((String) result[39]);
	 * candidateDto.setExpectedCtcPa((Double) result[40]);
	 * candidateDto.setNoticePeriod((Integer) result[41]);
	 * 
	 * // Handling the 'currentlyServingNoticePeriod' field carefully Object
	 * noticePeriodValue = result[42]; if (noticePeriodValue != null) {
	 * candidateDto.setCurrentlyServingNoticePeriod(noticePeriodValue.toString()); }
	 * else { candidateDto.setCurrentlyServingNoticePeriod(null); }
	 * 
	 * candidateDto.setComments((String) result[43]);
	 * candidateDto.setAvailability((String) result[44]);
	 * candidateDto.setEmployeeId(((BigInteger) result[45]).longValue());
	 * candidateDto.setResourceType((String) result[46]);
	 * candidateDto.setRequirementName((String) result[47]);
	 * candidateDto.setRrfLink(((BigInteger) result[48]).longValue());
	 * candidateDto.setStatus((String) result[49]);
	 * candidateDto.setOffered_CTC((Double) result[50]);
	 * candidateDto.setVisibility((Integer) result[51]);
	 * candidateDto.setRecordAuthor((String) result[52]);
	 * candidateDto.setIpAddress((String) result[53]);
	 * candidateDto.setVendor((String) result[54]);
	 * candidateDto.setCreationDate((Date) result[55]);
	 * candidateDto.setSource((String) result[56]);
	 * candidateDto.setSubStatus((String) result[57]);
	 * candidateDto.setAdditionalSkills((String) result[58]);
	 * 
	 * // Add the candidate to the DTO List<CandidateDto> candidates = new
	 * ArrayList<>(); candidates.add(candidateDto); dto.setCandidates(candidates);
	 * 
	 * dtos.add(dto); }
	 * 
	 * return dtos; }
	 */
	//=====================================================================Requirement Resource  with candidate information 
	/*
	 * public List<GetAllCandidatesDto> getAllRRFValues() {
	 * List<GetAllCandidatesDto> list = new ArrayList<>();
	 * 
	 * try { // Fetch all RRFs where visibility is 1 and sort them in reverse order
	 * by ID List<RRF> allRRFs = rrfRepo.findAll().stream() .filter(rrf -> rrf !=
	 * null && rrf.getVisibility() == 1)
	 * .sorted(Comparator.comparing(RRF::getId).reversed())
	 * .collect(Collectors.toList());
	 * 
	 * for (RRF rrf : allRRFs) { GetAllCandidatesDto dto = new
	 * GetAllCandidatesDto();
	 * 
	 * // Fetch all candidates associated with the current RRF where visibility is 1
	 * List<Candidate> candidatesByRrf = candidateRepo.findByRrf(rrf).stream()
	 * .filter(candidate -> candidate != null && candidate.getVisibility() == 1)
	 * .collect(Collectors.toList());
	 * 
	 * // Map RRF fields to DTO dto.setRrfId(rrf.getId());
	 * dto.setJobDescription(rrf.getJobDescription());
	 * dto.setCustomerName(rrf.getCustomerName());
	 * dto.setPrimarySkills(rrf.getPrimarySkills());
	 * dto.setHiringType(rrf.getHiringType()); dto.setJobLevel(rrf.getJobLevel());
	 * dto.setMinExp(rrf.getMinExp()); dto.setMaxExp(rrf.getMaxExp());
	 * dto.setBudget(rrf.getBudget());
	 * dto.setSecondarySkills(rrf.getSecondarySkills());
	 * dto.setEmailAddress(rrf.getEmailAddress());
	 * dto.setEmployeeId(rrf.getEmployeeId()); dto.setJobTitle(rrf.getJobTitle());
	 * dto.setJobType(rrf.getJobType()); dto.setPriority(rrf.getPriority());
	 * dto.setModeOfWork(rrf.getModeOfWork()); dto.setTitle(rrf.getTitle());
	 * dto.setOwnerOfRequirement(rrf.getOwnerOfRequirement());
	 * dto.setCity(rrf.getCity()); dto.setState(rrf.getState());
	 * dto.setLocation(rrf.getLocation()); dto.setExperience(rrf.getExperience());
	 * dto.setRequirementName(rrf.getRequirementName());
	 * dto.setVisibility(rrf.getVisibility());
	 * dto.setOpenPositions(rrf.getOpenPositions());
	 * dto.setClosedPositions(rrf.getClosedPositions());
	 * dto.setCandidateCount(rrf.getCandidateCount());
	 * dto.setPublishedOn(rrf.getPublishedOn() != null ?
	 * rrf.getPublishedOn().toString() : null);
	 * dto.setCreatedBy(rrf.getCreatedBy());
	 * dto.setTotalPositions(rrf.getTotalPositions());
	 * dto.setRequirementStatus(rrf.getRequirementStatus());
	 * 
	 * // Map candidates to CandidateDto and add to dto List<CandidateDto>
	 * candidateDtos = new ArrayList<>(); for (Candidate candidate :
	 * candidatesByRrf) { CandidateDto candidateDto = CandidateDto.builder()
	 * .cid(candidate.getCid()) .fullName(candidate.getFullName())
	 * .mobileNo(candidate.getMobileNo()) .emailId(candidate.getEmailId())
	 * .totalExperience(candidate.getTotalExperience())
	 * .currentlyWorkingAs(candidate.getCurrentlyWorkingAs())
	 * .currentlyWorkingAt(candidate.getCurrentlyWorkingAt())
	 * .currentLocation(candidate.getCurrentLocation())
	 * .preferredLocation(candidate.getPreferredLocation())
	 * .ctcPA(candidate.getCtcPA()) .expectedCtcPa(candidate.getExpectedCtcPa())
	 * .noticePeriod(candidate.getNoticePeriod())
	 * .currentlyServingNoticePeriod(candidate.getCurrentlyServingNoticePeriod())
	 * .comments(candidate.getComments()) .availability(candidate.getAvailability())
	 * .employeeId(candidate.getEmployeeId())
	 * .resourceType(candidate.getResourceType())
	 * .requirementName(candidate.getRequirementName())
	 * .skillSet(candidate.getSkillSet()) .rrfLink(candidate.getRrfLink())
	 * .status(candidate.getStatus()) .offered_CTC(candidate.getOffered_CTC())
	 * .visibility(candidate.getVisibility())
	 * .recordAuthor(candidate.getRecordAuthor())
	 * .ipAddress(candidate.getIpAddress()) .vendor(candidate.getVendor())
	 * .creationDate(candidate.getCreationDate()) .source(candidate.getSource())
	 * .hiringType(rrf.getHiringType()) .primarySkills(rrf.getPrimarySkills())
	 * .customerName(rrf.getCustomerName())
	 * .additionalSkills(candidate.getAdditionalSkills())
	 * .subStatus(candidate.getSubStatus()) .interviews(candidate.getInterviews())
	 * .tagExecutive(rrf.getCreatedBy()) .priority(rrf.getPriority())
	 * .ownerOfRequirement(rrf.getOwnerOfRequirement())
	 * .totalPositions(rrf.getTotalPositions())
	 * .openPositions(rrf.getOpenPositions())
	 * .closedPositions(rrf.getClosedPositions())
	 * .candidateCount(rrf.getCandidateCount()) .publishedOn(rrf.getPublishedOn() !=
	 * null ? rrf.getPublishedOn().toString() : null) .build();
	 * 
	 * candidateDtos.add(candidateDto); }
	 * 
	 * dto.setCandidates(candidateDtos); list.add(dto); } } catch (Exception e) { //
	 * Log the exception for debugging purposes e.printStackTrace(); // Optionally,
	 * you can handle specific types of exceptions here }
	 * 
	 * return list; }
	 * 
	 */	
	  
//=======================stream line
	public List<GetAllCandidatesDto> getAllRRFValues() {
	    List<GetAllCandidatesDto> list = new ArrayList<>();
	    
	    try {
	        // Fetch all RRFs where visibility is 1 and sort them in reverse order by ID
	        List<RRF> allRRFs = rrfRepo.findAll().stream()
	            .filter(rrf -> rrf != null && rrf.getVisibility() == 1)
	            .sorted(Comparator.comparing(RRF::getId).reversed())
	            .collect(Collectors.toList());
	        
	        for (RRF rrf : allRRFs) {
	            GetAllCandidatesDto dto = new GetAllCandidatesDto();
	            
	            // Fetch all candidates associated with the current RRF where visibility is 1
	            List<Candidate> candidatesByRrf = candidateRepo.findByRrf(rrf).stream()
	                .filter(candidate -> candidate != null && candidate.getVisibility() == 1)
	                .collect(Collectors.toList());
	            
	            // Map RRF fields to DTO
	            dto.setRrfId(rrf.getId());
	            dto.setJobDescription(rrf.getJobDescription());
	            dto.setCustomerName(rrf.getCustomerName());
	            dto.setPrimarySkills(rrf.getPrimarySkills());
	            dto.setHiringType(rrf.getHiringType());
	            dto.setJobLevel(rrf.getJobLevel());
	            dto.setMinExp(rrf.getMinExp());
	            dto.setMaxExp(rrf.getMaxExp());
	            dto.setBudget(rrf.getBudget());
	            dto.setSecondarySkills(rrf.getSecondarySkills());
	            dto.setEmailAddress(rrf.getEmailAddress());
	            dto.setEmployeeId(rrf.getEmployeeId());
	            dto.setJobTitle(rrf.getJobTitle());
	            dto.setJobType(rrf.getJobType());
	            dto.setPriority(rrf.getPriority());
	            dto.setModeOfWork(rrf.getModeOfWork());
	            dto.setTitle(rrf.getTitle());
	            dto.setOwnerOfRequirement(rrf.getOwnerOfRequirement());
	            dto.setCity(rrf.getCity());
	            dto.setState(rrf.getState());
	            dto.setLocation(rrf.getLocation());
	            dto.setExperience(rrf.getExperience());
	            dto.setRequirementName(rrf.getRequirementName());
	            dto.setVisibility(rrf.getVisibility());
	            dto.setOpenPositions(rrf.getOpenPositions());
	            dto.setClosedPositions(rrf.getClosedPositions());
	            dto.setCandidateCount(rrf.getCandidateCount());
	            dto.setPublishedOn(rrf.getPublishedOn() != null ? rrf.getPublishedOn().toString() : null);
	            dto.setCreatedBy(rrf.getCreatedBy());
	            dto.setTotalPositions(rrf.getTotalPositions());
	            dto.setRequirementStatus(rrf.getRequirementStatus());

	            // Map candidates to CandidateDto and add to dto
	            List<CandidateDto> candidateDtos = new ArrayList<>();
	            for (Candidate candidate : candidatesByRrf) {
	                CandidateDto candidateDto = CandidateDto.builder()
	                    .cid(candidate.getCid())
	                    .fullName(candidate.getFullName())
	                    .mobileNo(candidate.getMobileNo())
	                    .emailId(candidate.getEmailId())
	                    .totalExperience(candidate.getTotalExperience())
	                    .currentlyWorkingAs(candidate.getCurrentlyWorkingAs())
	                    .currentlyWorkingAt(candidate.getCurrentlyWorkingAt())
	                    .currentLocation(candidate.getCurrentLocation())
	                    .preferredLocation(candidate.getPreferredLocation())
	                    .ctcPA(candidate.getCtcPA())
	                    .expectedCtcPa(candidate.getExpectedCtcPa())
	                    .noticePeriod(candidate.getNoticePeriod())
	                    .currentlyServingNoticePeriod(candidate.getCurrentlyServingNoticePeriod())
	                    .comments(candidate.getComments())
	                    .availability(candidate.getAvailability())
	                    .employeeId(candidate.getEmployeeId())
	                    .resourceType(candidate.getResourceType())
	                    .requirementName(candidate.getRequirementName())
	                    .skillSet(candidate.getSkillSet())
	                    .rrfLink(candidate.getRrfLink())
	                    .status(candidate.getStatus())
	                    .offered_CTC(candidate.getOffered_CTC())
	                    .visibility(candidate.getVisibility())
	                    .recordAuthor(candidate.getRecordAuthor())
	                    .ipAddress(candidate.getIpAddress())
	                    .vendor(candidate.getVendor())
	                    .creationDate(candidate.getCreationDate())
	                    .source(candidate.getSource())
	                    .hiringType(rrf.getHiringType())
	                    .primarySkills(rrf.getPrimarySkills())
	                    .customerName(rrf.getCustomerName())
	                    .additionalSkills(candidate.getAdditionalSkills())
	                    .subStatus(candidate.getSubStatus())
	                    .interviews(candidate.getInterviews())
	                    .tagExecutive(rrf.getCreatedBy())
	                    .priority(rrf.getPriority())
	                    .ownerOfRequirement(rrf.getOwnerOfRequirement())
	                    .totalPositions(rrf.getTotalPositions())
	                    .openPositions(rrf.getOpenPositions())
	                    .closedPositions(rrf.getClosedPositions())
	                    .candidateCount(rrf.getCandidateCount())
	                    .publishedOn(rrf.getPublishedOn() != null ? rrf.getPublishedOn().toString() : null)
	                    .build();
	                
	                candidateDtos.add(candidateDto);
	            }
	            
	            dto.setCandidates(candidateDtos);
	            list.add(dto);
	        }
	    } catch (Exception e) {
	        // Log the exception for debugging purposes
	        e.printStackTrace();
	        // Optionally, you can handle specific types of exceptions here
	    }
	    
	    return list;
	}

	
	//=========================================================================================================================================
	
	@Override
	public ResponseEntity<String> getInterviewsByTechnologyAndDates(LocalDate startDate, LocalDate endDate) {
//		List<TecResponse> tecResponse = new ArrayList<TechResponse>();

		Map<String, String> map = new HashMap<>();
		List<Object[]> result = candidateRepo.getInterviewsByTechnologiesAndDates(startDate, endDate);
		for (Object[] objects : result) {
			map.put(objects[0].toString(), objects[1].toString());
		}
		System.out.print(map);
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@Override
	public Map<String, List<GetAllCandidatesDtoInfo>> getBDMDetails() {
	    Map<String, List<GetAllCandidatesDtoInfo>> map = new LinkedHashMap<>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    // Step 1: Fetch all owners at once and handle possible null or empty list
	    List<String> bdmDetails = rrfRepo.getOwnersdata();
	    System.out.println(bdmDetails + "=============================");
	    if (bdmDetails == null || bdmDetails.isEmpty()) {
	        return map; // Return empty map if no BDM details found
	    }

	    // Step 2: Fetch RRF data for all owners in one go
	    List<Object[]> result = rrfRepo.getRRFBDMSpecificInfo(bdmDetails);
	    if (result == null || result.isEmpty()) {
	        return map; // Return empty map if no RRF data found
	    }

	    // Step 3: Manually map each result row to GetAllCandidatesDtoInfo
	    List<GetAllCandidatesDtoInfo> allRRFCandidates = result.stream().map(row -> {
	        // Convert Timestamp to String
	        String publishedOn = null;
	        if (row[8] != null && row[8] instanceof Timestamp) {
	            publishedOn = dateFormat.format((Timestamp) row[8]);
	        }

	        return GetAllCandidatesDtoInfo.builder()
	            .rrfId(((Number) row[0]).longValue())
	            .customerName((String) row[1])
	            .primarySkills((String) row[2])
	            .ownerOfRequirement((String) row[3])
	            .requirementName((String) row[4])
	            .openPositions(((Number) row[5]).intValue())
	            .closedPositions(((Number) row[6]).intValue())
	            .candidateCount(((Number) row[7]).intValue())  // Map candidate_count
	            .publishedOn(publishedOn)  // Assign the formatted date string
	            .totalPositions(((Number) row[9]).intValue())
	            .build();
	    }).collect(Collectors.toList());

	    // Step 4: Group data by owner using parallel processing and handle possible null values
	    map = allRRFCandidates.parallelStream()
	            .filter(Objects::nonNull) // Filter out null objects
	            .collect(Collectors.groupingBy(dto -> 
	                Optional.ofNullable(dto.getOwnerOfRequirement()).orElse("Unknown"), 
	                LinkedHashMap::new, Collectors.toList()));

	    return map;
	}

	//perfectly working code if required we can use 
	/*
	 * @Override public Map<String, List<GetAllCandidatesDto>> getBDMDetails() { //
	 * TODO Auto-generated method stub List<String> bdmDetails = null; Map<String,
	 * List<GetAllCandidatesDto>> map = new LinkedHashMap<>();
	 * 
	 * bdmDetails = rrfRepo.getOwnersdata(); for (String ownerName : bdmDetails) {
	 * if (ownerName != null) { List<GetAllCandidatesDto> list = new ArrayList<>();
	 * 
	 * List<RRF> all = rrfRepo.getRRFBDMSpecificInfo(ownerName);
	 * 
	 * for (RRF rrf : all) { List<CandidateDto> candidateDtos = null;
	 * 
	 * if (rrf == null) { continue; // Skip to the next iteration if `rrf` is null }
	 * GetAllCandidatesDto dto = new GetAllCandidatesDto(); // List<Candidate> byRrf
	 * = //
	 * candidateRepo.findByRrf(rrf).stream().filter(c->c.getVisibility()==1).collect
	 * (Collectors.toList()); if (rrf != null) { // byRrf = //
	 * candidateRepo.findByRrf(rrf).stream().filter(c->c.getVisibility()==1).collect
	 * (Collectors.toList());
	 * 
	 * System.out.println(rrf.getId()); dto.setRrfId(rrf.getId());
	 * dto.setJobDescription(rrf.getJobDescription());
	 * dto.setCustomerName(rrf.getCustomerName());
	 * dto.setPrimarySkills(rrf.getPrimarySkills());
	 * dto.setHiringType(rrf.getHiringType()); dto.setJobLevel(rrf.getJobLevel());
	 * dto.setMinExp(rrf.getMinExp()); dto.setMaxExp(rrf.getMaxExp());
	 * dto.setBudget(rrf.getBudget());
	 * dto.setSecondarySkills(rrf.getSecondarySkills());
	 * dto.setEmailAddress(rrf.getEmailAddress());
	 * dto.setEmployeeId(rrf.getEmployeeId()); dto.setJobTitle(rrf.getJobTitle());
	 * dto.setJobType(rrf.getJobType()); dto.setPriority(rrf.getPriority());
	 * dto.setModeOfWork(rrf.getModeOfWork()); dto.setTitle(rrf.getTitle());
	 * dto.setOwnerOfRequirement(rrf.getOwnerOfRequirement());
	 * dto.setCity(rrf.getCity()); dto.setState(rrf.getState());
	 * dto.setLocation(rrf.getLocation()); dto.setExperience(rrf.getExperience());
	 * dto.setRequirementName(rrf.getRequirementName());
	 * dto.setVisibility(rrf.getVisibility());
	 * dto.setOpenPositions(rrf.getOpenPositions());
	 * dto.setClosedPositions(rrf.getClosedPositions());
	 * dto.setCandidateCount(rrf.getCandidateCount());
	 * dto.setPublishedOn(rrf.getPublishedOn() + ""); // Convert Date to String
	 * dto.setCreatedBy(rrf.getCreatedBy());
	 * dto.setTotalPositions(rrf.getTotalPositions());
	 * dto.setRequirementStatus(rrf.getRequirementStatus()); // List<Candidate>
	 * byRrfId = candidateRepo.getByRrfIdInfo(rrf.getId());
	 * 
	 * 
	 * candidateDtos = new ArrayList<>(); if(byRrfId!=null) { for (Candidate
	 * candidate : byRrfId) {
	 * 
	 * if(candidate!=null) { candidate.getInterviews();
	 * 
	 * CandidateDto candidateDto = CandidateDto.builder() .cid(candidate.getCid())
	 * .fullName(candidate.getFullName()) .mobileNo(candidate.getMobileNo())
	 * .emailId(candidate.getEmailId())
	 * .totalExperience(candidate.getTotalExperience())
	 * .currentlyWorkingAs(candidate.getCurrentlyWorkingAs())
	 * .currentlyWorkingAt(candidate.getCurrentlyWorkingAt())
	 * .currentLocation(candidate.getCurrentLocation())
	 * .preferredLocation(candidate.getPreferredLocation())
	 * .ctcPA(candidate.getCtcPA()) .expectedCtcPa(candidate.getExpectedCtcPa())
	 * .noticePeriod(candidate.getNoticePeriod())
	 * .currentlyServingNoticePeriod(candidate.getCurrentlyServingNoticePeriod())
	 * .comments(candidate.getComments()) .availability(candidate.getAvailability())
	 * .employeeId(candidate.getEmployeeId())
	 * .resourceType(candidate.getResourceType())
	 * .requirementName(candidate.getRequirementName())
	 * .skillSet(candidate.getSkillSet()) .rrfLink(candidate.getRrfLink())
	 * .status(candidate.getStatus()) .offered_CTC(candidate.getOffered_CTC())
	 * .visibility(candidate.getVisibility())
	 * .recordAuthor(candidate.getRecordAuthor())
	 * .ipAddress(candidate.getIpAddress()) //
	 * .interviews(candidate.getInterviews()) .vendor(candidate.getVendor())
	 * .creationDate(candidate.getCreationDate()) .source(candidate.getSource())
	 * .hiringType(rrf.getHiringType()) .primarySkills(rrf.getPrimarySkills())
	 * .customerName(rrf.getCustomerName())
	 * .additionalSkills(candidate.getAdditionalSkills()).subStatus(candidate.
	 * getSubStatus()) .interviews(candidate.getInterviews()) //
	 * .primarySkills(rrf.getPrimarySkills()).tagExecutive(rrf.getCreatedBy()). //
	 * customerName(rrf.getCustomerName()).priority(rrf.getPriority()).
	 * ownerOfRequirement(rrf.getOwnerOfRequirement()) //
	 * .totalPositions(rrf.getTotalPositions()).openPositions(rrf.getOpenPositions()
	 * ).closedPositions(rrf.getClosedPositions()) //
	 * .candidateCount(rrf.getCandidateCount()).publishedOn(String.valueOf(
	 * rrf.getPublishedOn())) .build(); candidateDtos.add(candidateDto); }}//end of
	 * innerfor loop }//end of if // dto.setCandidates(candidateDtos);
	 * list.add(dto); } // end of rrffor} } map.put(ownerName, list); } // end of
	 * bdm } return map; }
	 */
	/*
	 * @Override public Map<String, List<CandidateDetailsDto>>
	 * getCandidateDetailsDto() { Map<String, List<CandidateDetailsDto>> map = new
	 * LinkedHashMap<>();
	 * 
	 * // Specify the format you want SimpleDateFormat formatter = new
	 * SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * List<String> candidateNames = candidateRepo.getCandidateNames();
	 * 
	 * if (candidateNames == null || candidateNames.isEmpty()) { return map; }
	 * 
	 * for (String candidateName : candidateNames) { if (candidateName != null) {
	 * List<Candidate> candidateInfo =
	 * candidateRepo.getCandidateInfo(candidateName);
	 * 
	 * List<CandidateDetailsDto> candidateAddDetailsDto = new ArrayList<>(); for
	 * (Candidate candidate : candidateInfo) { if (candidate != null &&
	 * candidate.getCreationDate() != null) { String formattedDate =
	 * formatter.format(candidate.getCreationDate());
	 * 
	 * CandidateDetailsDto candidateDto1 = CandidateDetailsDto.builder()
	 * .fullName(candidate.getFullName()).emailId(candidate.getEmailId())
	 * .subStatus(candidate.getSubStatus()).customerName(candidate.getRrf().
	 * getCustomerName()) // .creationDate(candidate.getCreationDate()) // Store raw
	 * timestamp // Convert Date to String .creationDate(formattedDate).build();
	 * 
	 * candidateAddDetailsDto.add(candidateDto1); } } map.put(candidateName,
	 * candidateAddDetailsDto); } } return map; }
	 */

	public Map<String, List<CandidateDetailsDto>> getCandidateDetails() {
		Map<String, List<CandidateDetailsDto>> map = new LinkedHashMap<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		List<Object[]> candidateData = candidateRepo.getCandidateDetailsInfo();

		if (candidateData == null || candidateData.isEmpty()) {
			return map; // Return empty map if there's no data
		}

		for (Object[] row : candidateData) {
			if (row == null || row.length < 5) {
				continue; // Skip processing if the row is null or doesn't have enough elements
			}

			String candidateName = (String) row[0];
			if (candidateName != null) {
				String emailId = (String) row[1];
				String subStatus = (String) row[2];
				String customerName = (String) row[3];
				Date creationDate = (Date) row[4];

				String formattedDate = null;
				if (creationDate != null) {
					formattedDate = formatter.format(creationDate);
				}

				CandidateDetailsDto candidateDto1 = CandidateDetailsDto.builder().fullName(candidateName)
						.emailId(emailId).subStatus(subStatus).customerName(customerName).creationDate(formattedDate) 					//can be null if creationDate is null
						.build();

				map.computeIfAbsent(candidateName, k -> new ArrayList<>()).add(candidateDto1);
			}
		}
		return map;
	}

}