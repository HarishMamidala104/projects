package com.ojas.hiring.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.serviceImpl.CandidateServiceImpl;
import com.ojas.hiring.serviceImpl.RRFServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DashBoardController {

	@Autowired
	CandidateRepo candidateRepo;

	@Autowired
	RRFServiceImpl rrfService;

	@Autowired
	CandidateServiceImpl candidateServiceImpl;

	@Autowired
	RRFRepo rrfrepo;

	@GetMapping("/getAllDashBoardDetails")
	public Map<String, Map<String, Integer>> getDashBoardDetails() {
		List<Candidate> listOfCandidates = candidateRepo.findAll();
		Map<String, Map<String, Integer>> dataMap = new HashMap<>();
		for (Candidate candidate : listOfCandidates) {
			Map<String, Integer> map = dataMap.get(candidate.getStatus());
			if (map != null) {
				Integer integer = map.get(candidate.getSubStatus());

				if (integer == null) {
//					Map<String, Integer> subStatusMap  = new HashMap<>();
					Integer count = new Integer(0);
					count++;
					map.put(candidate.getSubStatus(), count);
					dataMap.put(candidate.getStatus(), map);
				} else {
					int count = integer;
					count++;
					Integer count1 = new Integer(count);
					map.put(candidate.getSubStatus(), count1);
					dataMap.put(candidate.getStatus(), map);
				}
			} else {
				Map<String, Integer> statusMap = new HashMap<>();
				statusMap.put(candidate.getSubStatus(), 1);
				dataMap.put(candidate.getStatus(), statusMap);
			}

		}

		System.out.println("Map " + dataMap);
		return dataMap;
	}

	@GetMapping("/dashBoard/aggregate-data")
	public ResponseEntity<?> getStatusData(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		Map<String, Integer> map = new HashMap<>();
		Map<String, Integer> map1 = new HashMap<>();
		List<Object[]> allCandidatesForStatus = candidateRepo.getAllCandidatesForStatus(startDate, endDate);

		for (Object[] objects : allCandidatesForStatus) {

			map.put(objects[0].toString(), Integer.parseInt(objects[1].toString()));
		}

		List<Object[]> interviewsByTechnologiesAndDates = candidateRepo.getInterviewsByTechnologiesAndDates(startDate,
				endDate);

		for (Object[] objects : interviewsByTechnologiesAndDates) {

			map1.put(objects[0].toString(), Integer.parseInt(objects[1].toString()));
		}
		Map<String, Map<String, Integer>> customerMap = new HashMap<>();

		List<Object[]> interviewsByCustomersAndDates = candidateRepo.getInterviewsByCustomersAndDates(startDate,
				endDate);
		for (Object[] objects : interviewsByCustomersAndDates) {
			Map<String, Integer> positions = new HashMap<String, Integer>();
			positions.put("OpenPositions", Integer.parseInt(objects[1].toString()));
			positions.put("closedPositions", Integer.parseInt(objects[2].toString()));
			customerMap.put(objects[0].toString(), positions);
		}
		Map<String, Map> overallData = new HashMap<String, Map>();
		overallData.put("Status", map);
		overallData.put("Technology", map1);
		overallData.put("CustomerData", customerMap);
		return new ResponseEntity<>(overallData, HttpStatus.OK);
	}

//    @GetMapping("/dashBoard/aggregate-data")
//    public ResponseEntity<?> getAggregatedInterviewData(
//            @RequestParam(required = false,name = "technology") String technology,
//            @RequestParam(required = false, name="customerName") String customer,
//            @RequestParam(required = false, name = "status") String status,
//            @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//            @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
//    ) {
//        List<AnalyticalDto> responseList = null;
//        ResponseEntity<String> interviewsByTechnologyAndDates = null;
//        ResponseEntity<String> interviewsByCustomerAndDates = null;
//        ResponseEntity<String> statusResponse = null;
//        HttpStatus httpStatus = HttpStatus.OK; // Default status
//
//        if (technology != null) {
//        	interviewsByTechnologyAndDates = candidateServiceImpl.getInterviewsByTechnologyAndDates(technology, startDate, endDate);
//        	return interviewsByTechnologyAndDates;
//        
//        } else if (customer != null) {
//        	interviewsByCustomerAndDates= candidateServiceImpl.getInterviewsByCustomerAndDates(customer, startDate, endDate);
//        	return interviewsByCustomerAndDates;
//        }
//        else if(status != null) {
//        	Map<String, Map<String, Integer>> statusProcess = statusProcess(startDate, endDate);
//        	 return new ResponseEntity<>(statusProcess, HttpStatus.OK);
////        responseList = candidateServiceImpl.getInterviewsByStatusAndDates(startDate, endDate);
//           
//        } else if (startDate != null && endDate != null) {
//            if (isLastWeek(startDate, endDate)) {
//                responseList = rrfService.getInterviewDataForOneWeek();
//            } else if (isLastMonth(startDate, endDate)) {
//                responseList = rrfService.getInterviewDataForOneMonth();
//            } else {
//                responseList = rrfService.getCustomDateInfo(startDate, endDate);
//            }
//        } else if(startDate != null && endDate == null) {
//        	responseList = rrfService.getCustomDateInfo(startDate, endDate);
//        }
//
//        if (responseList != null && !responseList.isEmpty()) {
//            return new ResponseEntity<>(responseList, HttpStatus.OK); // If data is found, return with OK status
//        } else {
//            String message = "No data found for the specified criteria.";
//            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND); // If no data is found, return with NOT_FOUND status
//        }
//        
//    }

	private boolean isLastWeek(LocalDate startDate, LocalDate endDate) {
		LocalDate currentDate = LocalDate.now();
		LocalDate lastWeekStartDate = currentDate.minusWeeks(1);

		// Check if the provided start and end dates are within the last week.
		return !startDate.isBefore(lastWeekStartDate) && !endDate.isAfter(currentDate);
	}

	private boolean isLastMonth(LocalDate startDate, LocalDate endDate) {
		LocalDate currentDate = LocalDate.now();
		LocalDate lastMonthStartDate = currentDate.minusMonths(1);

		// Check if the provided start and end dates are within the last month.
		return !startDate.isBefore(lastMonthStartDate) && !endDate.isAfter(currentDate);
	}

//	public Map<String, Map<String, Integer>> statusProcess(LocalDate startDate, LocalDate endDate) {
////        List<Candidate> listOfCandidates = candidateRepo.findAll();
////    	List<Candidate> listOfCandidates = candidateServiceImpl.getInterviewsByStatusAndDates(startDate, endDate);
//		List<Candidate> listOfCandidates = candidateRepo.getAllCandidatesForStatus(startDate, endDate);
//		Map<String, Map<String, Integer>> dataMap = new HashMap<>();
//		for (Candidate candidate : listOfCandidates) {
//			Map<String, Integer> map = dataMap.get(candidate.getStatus());
//			if (map != null) {
//				Integer integer = map.get(candidate.getSubStatus());
//
//				if (integer == null) {
////    				Map<String, Integer> subStatusMap  = new HashMap<>();
//					Integer count = new Integer(0);
//					count++;
//					map.put(candidate.getSubStatus(), count);
//					dataMap.put(candidate.getStatus(), map);
//				} else {
//					int count = integer;
//					count++;
//					Integer count1 = new Integer(count);
//					map.put(candidate.getSubStatus(), count1);
//					dataMap.put(candidate.getStatus(), map);
//				}
//			} else {
//				Map<String, Integer> statusMap = new HashMap<>();
//				statusMap.put(candidate.getSubStatus(), 1);
//				dataMap.put(candidate.getStatus(), statusMap);
//			}
//		}
//		System.out.println("Map " + dataMap);
//		return dataMap;
//
//	}

	@GetMapping("/getData")
	public ResponseEntity<?> getData() {
		List<Object[]> assignedData = rrfrepo.getAssignedData();
		Map<String, Map<String, Integer>> map = new HashMap<>();

		for (int i = 0; i < assignedData.size(); i++) {
			Map<String, Integer> map1 = new HashMap<String, Integer>();
			Object[] objects = assignedData.get(i);
			map1.put("Total_Requirements", Integer.parseInt(objects[1].toString()));
			map1.put("total_positions", Integer.parseInt(objects[2].toString()));
			map1.put("open_positions", Integer.parseInt(objects[3].toString()));
			map1.put("closed_positions", Integer.parseInt(objects[4].toString()));
			map.put(objects[0].toString(), map1);

		}
		return new ResponseEntity<>(map, HttpStatus.OK);

	}

//	@GetMapping("/getDataByCustomer")
//	public ResponseEntity getDataByCustomer(@RequestParam(required = false,defaultValue = "all") String customer,
//			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//		Map<String,Integer> map = new HashMap<>();
//		if(!customer.equals("all")) {
//			endDate = endDate.plusDays(1);
//			List<Object[]> customerData = rrfrepo.getCustomizedCustomerData(customer, startDate, endDate);
//			if(customerData!=null) {
//			for (Object[] objects : customerData) {
//				map.put("TotalPositions", Integer.parseInt(objects[0].toString()));
//				map.put("Closed Positions", Integer.parseInt(objects[1].toString()));
//			}
//			return new ResponseEntity(map,HttpStatus.OK);
//			}
//			throw new NoRecordFoundException("No Records Found");
//		}
//		else {
//			endDate = endDate.plusDays(1);
//			List<Object[]> allCutomerData = rrfrepo.getAllCutomerData(startDate, endDate);
//			if(allCutomerData.get(0)!=null) {
//			Object[] result  = allCutomerData.get(0);
//			if (result[0] != null && result[1] != null) {
//			int totalPositions = 0;
//	        int closedPositions = 0;
//			for (Object[] objects : allCutomerData) {
//				map.put("TotalPositions", Integer.parseInt(objects[0].toString()));
//				map.put("Closed Positions", Integer.parseInt(objects[1].toString()));
//			}
//			return new ResponseEntity(map,HttpStatus.OK);
//			}
//			}
//			throw new NoRecordFoundException("No Records Found");
//		}
//	}

	@GetMapping("/getDataByCustomerToDonut")
	public ResponseEntity<Map<String, Integer>> getDataByCustomer(@RequestParam(required = false) String customer,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		// Adjust end date to include the day fully
		endDate = endDate.plusDays(1);
		List<Object[]> dataResults;

		if (!customer.equals("all")) {
			dataResults = rrfrepo.getCustomizedCustomerDatatoDonut(customer, startDate, endDate);
		} else {
			dataResults = rrfrepo.getAllCutomerDatatoDonut(startDate, endDate);
		}
		Map<String, Integer> resultMap = new HashMap<>();
		if (dataResults.isEmpty() || dataResults.get(0)[0] == null) {
			resultMap.put("TotalPositions", 0);
			resultMap.put("ClosedPositions", 0);
		}
		Object[] result = dataResults.get(0);
		resultMap.put("TotalPositions", result[0] != null ? Integer.parseInt(result[0].toString()) : 0);
		resultMap.put("ClosedPositions", result[1] != null ? Integer.parseInt(result[1].toString()) : 0);

		return ResponseEntity.ok(resultMap);
	}

//	@GetMapping("/getAllInformationToBar")
//	public ResponseEntity<Map<String, Map<String, Object >>> getAllInformation(@RequestParam(required = false, defaultValue = "all") String customer,
//			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//		endDate = endDate.plusDays(1);
//		List<Object[]> dataResults = null;
//
//		if (!customer.equals("all")) {
//			dataResults = rrfrepo.getCustomizedCustomerData(customer, startDate, endDate);
//		} else {
//			dataResults = rrfrepo.getAllCutomerData(startDate, endDate);
//		}
//		
//		 Map<String, Map<String, Object >> resultMap = new LinkedHashMap<>();
//		    String[] keys = {
//		        "Total_Positions", "Closed_Positions", "Open_Positions", "Stage_1", "Stage_2","Stage_3","Onboarded","BDMHold","client_hold","Yet_To_Schedule","Dropped","FeedbackPending",
//		        "NoShow","Client_Rejected" ,"Duplicate","Rejected",
//		        
//		    };
//		    String[] innerKeys = {
//			        "Count", "Candidates", "Percentage"
//			    };
//		    for (String key : keys) {
//		    	Map<String, Object > values = new LinkedHashMap<>();
//		    	for (String k : innerKeys) {
//		    		values.put(k, 0);
//		    	}
//		    	
//		        resultMap.put(key, values);
//		    }

//		    for (Object[] result : dataResults) {
//		      //  if (result.length == 2 && result[0] instanceof String && result[1] instanceof Integer) {
//		            String dbKey = (String) result[0];
//		            Integer value = Integer.parseInt(result[1].toString());
//		            Integer candidates = Integer.parseInt(result[2].toString());
//		            double percentage = Double.parseDouble(result[3].toString());
//		            
//
//		            switch (dbKey) {
//		            case "total_positions":
//		                Map<String, Object> tp = resultMap.get("Total_Positions");
//		                tp.put(innerKeys[0], value);
//		                tp.put(innerKeys[1], candidates);
//		                tp.put(innerKeys[2], percentage);
//		                resultMap.put("Total_Positions", tp);
//		                break;
//		            case "closed_positions":
//		                Map<String, Object> cp = resultMap.get("Closed_Positions");
//		                cp.put(innerKeys[0], value);
//		                cp.put(innerKeys[1], candidates);
//		                cp.put(innerKeys[2], percentage);
//		                resultMap.put("Closed_Positions", cp);
//		                break;
//		            case "open_positions":
//		                Map<String, Object> op = resultMap.get("Open_Positions");
//		                op.put(innerKeys[0], value);
//		                op.put(innerKeys[1], candidates);
//		                op.put(innerKeys[2], percentage);
//		                resultMap.put("Open_Positions", op);
//		                break;
//		            case "STAGE_1":
//		                Map<String, Object> stage1 = resultMap.get("Stage_1");
//		                stage1.put(innerKeys[0], value);
//		                stage1.put(innerKeys[1], candidates);
//		                stage1.put(innerKeys[2], percentage);
//		                resultMap.put("Stage_1", stage1);
//		                break;
//		            case "STAGE_2":
//		                Map<String, Object> stage2 = resultMap.get("Stage_2");
//		                stage2.put(innerKeys[0], value);
//		                stage2.put(innerKeys[1], candidates);
//		                stage2.put(innerKeys[2], percentage);
//		                resultMap.put("Stage_2", stage2);
//		                break;
//		            case "STAGE_3":
//		                Map<String, Object> stage3 = resultMap.get("Stage_3");
//		                stage3.put(innerKeys[0], value);
//		                stage3.put(innerKeys[1], candidates);
//		                stage3.put(innerKeys[2], percentage);
//		                resultMap.put("Stage_3", stage3);
//		                break;
//		            case "Onboarded":
//		                Map<String, Object> onboarded = resultMap.get("Onboarded");
//		                onboarded.put(innerKeys[0], value);
//		                onboarded.put(innerKeys[1], candidates);
//		                onboarded.put(innerKeys[2], percentage);
//		                resultMap.put("Onboarded", onboarded);
//		                break;
//		            case "BDM_HOLD":
//		                Map<String, Object> bdmHold = resultMap.get("BDMHold");
//		                bdmHold.put(innerKeys[0], value);
//		                bdmHold.put(innerKeys[1], candidates);
//		                bdmHold.put(innerKeys[2], percentage);
//		                resultMap.put("BDMHold", bdmHold);
//		                break;
//		            case "CLIENT_HOLD":
//		                Map<String, Object> clientHold = resultMap.get("Client_hold");
//		                clientHold.put(innerKeys[0], value);
//		                clientHold.put(innerKeys[1], candidates);
//		                clientHold.put(innerKeys[2], percentage);
//		                resultMap.put("Client_hold", clientHold);
//		                break;
//		            case "YET_TO_SCHEDULE":
//		                Map<String, Object> yetToSchedule = resultMap.get("Yet_To_Schedule");
//		                yetToSchedule.put(innerKeys[0], value);
//		                yetToSchedule.put(innerKeys[1], candidates);
//		                yetToSchedule.put(innerKeys[2], percentage);
//		                resultMap.put("Yet_To_Schedule", yetToSchedule);
//		                break;
//		            case "DROPPED":
//		                Map<String, Object> dropped = resultMap.get("Dropped");
//		                dropped.put(innerKeys[0], value);
//		                dropped.put(innerKeys[1], candidates);
//		                dropped.put(innerKeys[2], percentage);
//		                resultMap.put("Dropped", dropped);
//		                break;
//		            case "Feedbackpending":
//		                Map<String, Object> feedbackPending = resultMap.get("FeedbackPending");
//		                feedbackPending.put(innerKeys[0], value);
//		                feedbackPending.put(innerKeys[1], candidates);
//		                feedbackPending.put(innerKeys[2], percentage);
//		                resultMap.put("FeedbackPending", feedbackPending);
//		                break;
//		            case "NOSHOW":
//		                Map<String, Object> noshow = resultMap.get("Noshow");
//		                noshow.put(innerKeys[0], value);
//		                noshow.put(innerKeys[1], candidates);
//		                noshow.put(innerKeys[2], percentage);
//		                resultMap.put("Noshow", noshow);
//		                break;
//		            case "CLIENT_REJECTED":
//		                Map<String, Object> clientRejected = resultMap.get("Client_Rejected");
//		                clientRejected.put(innerKeys[0], value);
//		                clientRejected.put(innerKeys[1], candidates);
//		                clientRejected.put(innerKeys[2], percentage);
//		                resultMap.put("Client_Rejected", clientRejected);
//		                break;
//		            case "DUPLICATE":
//		                Map<String, Object> duplicate = resultMap.get("Duplicate");
//		                duplicate.put(innerKeys[0], value);
//		                duplicate.put(innerKeys[1], candidates);
//		                duplicate.put(innerKeys[2], percentage);
//		                resultMap.put("Duplicate", duplicate);
//		                break;
//		            case "REJECTED":
//		                Map<String, Object> rejected = resultMap.get("Rejected");
//		                rejected.put(innerKeys[0], value);
//		                rejected.put(innerKeys[1], candidates);
//		                rejected.put(innerKeys[2], percentage);
//		                resultMap.put("Rejected", rejected);
//		                break;
//		            default:
//		                break;
//		        }
//
//		       // }
//		    }
	// ===============================
	/*
	 * for (Object[] result : dataResults) { if (result.length >= 4) { // Ensure the
	 * array has at least 4 elements String dbKey = (String) result[0];
	 * 
	 * // Ensure the key is not null if (dbKey == null) { continue; // Skip this
	 * iteration if the key is null }
	 * 
	 * // Parse values safely Integer value = parseInteger(result[1]); Integer
	 * candidates = parseInteger(result[2]); Double percentage =
	 * parseDouble(result[3]);
	 * 
	 * // Check if values are not null before proceeding if (value == null ||
	 * candidates == null || percentage == null) { continue; // Skip this iteration
	 * if any value is null }
	 * 
	 * // Use a method to avoid repetition and improve readability
	 * updateResultMap(dbKey, value, candidates, percentage, resultMap, innerKeys);
	 * } }
	 */

//=======================================================================================================================		
//		for (Object[] row:dataResults ) {
//			System.out.println(row[0].toString());
//			System.out.println(row[1].toString());
//			resultMap.put(row[0].toString(), Integer.parseInt(row[1].toString()));
//			
//		}
//		System.out.println(resultMap);
//		if(dataResults.isEmpty() || dataResults.get(0)[0]==null) {
//			resultMap.put("TotalPositions", 0);
//			resultMap.put("ClosedPositions", 0);
//		}
//		Object[] result = dataResults.get(0);
//		resultMap.put("Total", result[0] != null ? Integer.parseInt(result[0].toString()) : 0);
//		resultMap.put("Closed",  result[1] != null ? Integer.parseInt(result[1].toString()) : 0);
//		resultMap.put("Open",  result[2] != null ? Integer.parseInt(result[2].toString()) : 0);
//		resultMap.put("Candidate", result[3] != null ? Integer.parseInt(result[3].toString()) : 0);
//		resultMap.put("InProgress", result[4]!=null ? Integer.parseInt(result[4].toString()):0);
//		resultMap.put("Hold", result[5]!=null ? Integer.parseInt(result[5].toString()):0);
//		resultMap.put("Reject", result[6]!=null ? Integer.parseInt(result[6].toString()):0);
//		resultMap.put("Onboard", result[7]!=null ? Integer.parseInt(result[7].toString()):0);
//		resultMap.put("Offered", result[8]!=null ? Integer.parseInt(result[8].toString()):0);
//		resultMap.put("Stage_1", result[9]!=null ? Integer.parseInt(result[9].toString()):0);
//		resultMap.put("Stage_2", result[10]!=null ? Integer.parseInt(result[10].toString()):0);
//		resultMap.put("Stage_3", result[11]!=null ? Integer.parseInt(result[11].toString()):0);
//		resultMap.put("Manager", result[12]!=null ? Integer.parseInt(result[12].toString()):0);
//		resultMap.put("Client", result[13]!=null ? Integer.parseInt(result[13].toString()):0);
//		resultMap.put("HR", result[14]!=null ? Integer.parseInt(result[14].toString()):0);

//		return ResponseEntity.ok(resultMap);

	// }

	/*
	 * @GetMapping("/getBDMDetails") public ResponseEntity<Map<String, Integer>>
	 * getBDMData(
	 * 
	 * @RequestParam(required = false, defaultValue = "all") String
	 * ownerOfRequirement,
	 * 
	 * @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")
	 * LocalDate startDate,
	 * 
	 * @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")
	 * LocalDate endDate) { List<Object[]> bdmDetails = null; Map<String, Integer>
	 * map = new LinkedHashMap<>(); if (ownerOfRequirement.equals("all")) {
	 * bdmDetails = rrfrepo.getAllBDMDetails(startDate, endDate); } else {
	 * bdmDetails = rrfrepo.getBDMDetails(ownerOfRequirement, startDate, endDate); }
	 * if (bdmDetails.isEmpty() || bdmDetails.get(0)[0] == null) { map.put("Total",
	 * 0); map.put("open", 0); map.put("Closed", 0); } Object[] result =
	 * bdmDetails.get(0); map.put("Total", result[0] != null ?
	 * Integer.parseInt(result[0].toString()) : 0); map.put("open", result[1] !=
	 * null ? Integer.parseInt(result[1].toString()) : 0); map.put("Closed",
	 * result[2] != null ? Integer.parseInt(result[2].toString()) : 0); return
	 * ResponseEntity.ok(map); }
	 */
	@GetMapping("/getExperienceData")
	public ResponseEntity<Map<String, Integer>> getExperienceData() {
		Map<String, Integer> map = new LinkedHashMap<>();
		List<Object[]> experienceData = candidateRepo.getExperienceData();
		if (experienceData.isEmpty() || experienceData.get(0)[0] == null) {
			map.put("0-3", 0);
			map.put("3-6", 0);
			map.put("6-9", 0);
			map.put("9-12", 0);
			map.put("12-15", 0);
		}
		for (Object[] objects : experienceData) {
			Object[] resultmap = experienceData.get(0);
			map.put("0-3", Integer.parseInt(resultmap[0].toString()));
			map.put("3-6", Integer.parseInt(resultmap[1].toString()));
			map.put("6-9", Integer.parseInt(resultmap[2].toString()));
			map.put("9-12", Integer.parseInt(resultmap[3].toString()));
			map.put("12-15", Integer.parseInt(resultmap[4].toString()));
			map.put(">15", Integer.parseInt(resultmap[5].toString()));
		}
		return ResponseEntity.ok(map);

	}

	@GetMapping("/getownerOfRequirement")
	public List<String> getOwners() {
		List<String> ownersdata = rrfrepo.getOwnersdata();
		return ownersdata;
	}

	private Integer parseInteger(Object obj) {
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	private Double parseDouble(Object obj) {
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	private void updateResultMap(String dbKey, Integer value, Integer candidates, Double percentage,
			Map<String, Map<String, Object>> resultMap, String[] innerKeys) {
		Map<String, Object> mapToUpdate = null;

		switch (dbKey) {
		case "total_positions":
			mapToUpdate = resultMap.get("Total_Positions");
			break;
		case "closed_positions":
			mapToUpdate = resultMap.get("Closed_Positions");
			break;
		case "open_positions":
			mapToUpdate = resultMap.get("Open_Positions");
			break;
		case "STAGE_1":
			mapToUpdate = resultMap.get("Stage_1");
			break;
		case "STAGE_2":
			mapToUpdate = resultMap.get("Stage_2");
			break;
		case "STAGE_3":
			mapToUpdate = resultMap.get("Stage_3");
			break;
		case "Onboarded":
			mapToUpdate = resultMap.get("Onboarded");
			break;
		case "BDM_HOLD":
			mapToUpdate = resultMap.get("BDMHold");
			break;
		case "CLIENT_HOLD":
			mapToUpdate = resultMap.get("CLIENT_HOLD");
			break;
		case "YET_TO_SCHEDULE":
			mapToUpdate = resultMap.get("Yet_To_Schedule");
			break;
		case "DROPPED":
			mapToUpdate = resultMap.get("Dropped");
			break;
		case "Feedbackpending":
			mapToUpdate = resultMap.get("FeedbackPending");
			break;
		case "NOSHOW":
			mapToUpdate = resultMap.get("noshow");
			break;
		case "CLIENT_REJECTED":
			mapToUpdate = resultMap.get("Client_Rejected");
			break;
		case "DUPLICATE":
			mapToUpdate = resultMap.get("Duplicate");
			break;
		case "REJECTED":
			mapToUpdate = resultMap.get("Rejected");
			break;
		default:
			return; // Do nothing if the key doesn't match
		}

		if (mapToUpdate != null) {
			mapToUpdate.put(innerKeys[0], value);
			mapToUpdate.put(innerKeys[1], candidates);
			mapToUpdate.put(innerKeys[2], percentage);
			resultMap.put(dbKey, mapToUpdate);
		}
	}

	@GetMapping("/getAllInformationToBar")
	public ResponseEntity<Map<String, Map<String, Object>>> getAllInformation(
			@RequestParam(required = false, defaultValue = "all") String customer,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		if (endDate != null) {
			endDate = endDate.plusDays(1);
		}

		List<Object[]> dataResults = null;

		if (!customer.equals("all")) {
			dataResults = rrfrepo.getCustomizedCustomerData(customer, startDate, endDate);
		} else {
			dataResults = rrfrepo.getAllCutomerData(startDate, endDate);
		}

		if (dataResults == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyMap());
		}

		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<>();
		String[] keys = { "Total_Positions", "Closed_Positions", "Open_Positions", "Stage_1", "Stage_2", "Stage_3",
				"Onboarded", "BDMHold", "CLIENT_HOLD", "Yet_To_Schedule", "Dropped", "FeedbackPending", "NoShow",
				 "Client","Client","Duplicate", "Rejected", };
		String[] innerKeys = { "Count", "Candidates", "Percentage" };
		for (String key : keys) {
			Map<String, Object> values = new LinkedHashMap<>();
			for (String k : innerKeys) {
				values.put(k, 0);
			}

			resultMap.put(key, values);
		}

		for (Object[] result : dataResults) {
			if (result != null && result.length >= 4 && result[0] instanceof String && result[1] instanceof Number
					&& result[2] instanceof Number && result[3] instanceof Number) {
				String dbKey = (String) result[0];
				Integer value = ((Number) result[1]).intValue();
				Integer candidates = ((Number) result[2]).intValue();
				double percentage = ((Number) result[3]).doubleValue();

				String resultKey = getResultKey(dbKey);
				if (resultKey != null && resultMap.containsKey(resultKey)) {
					Map<String, Object> mapValues = resultMap.get(resultKey);
					mapValues.put(innerKeys[0], value);
					mapValues.put(innerKeys[1], candidates);
					mapValues.put(innerKeys[2], percentage);
					resultMap.put(resultKey, mapValues);
				}
			}
		}

		return ResponseEntity.ok(resultMap);
	}

	private String getResultKey(String dbKey) {
		switch (dbKey.toLowerCase()) {
		case "total_positions":
			return "Total_Positions";
		case "closed_positions":
			return "Closed_Positions";
		case "open_positions":
			return "Open_Positions";
		case "stage_1":
			return "Stage_1";
		case "stage_2":
			return "Stage_2";
		case "stage_3":
			return "Stage_3";
		case "onboarded":
			return "Onboarded";
		case "bdm_hold":
			return "BDMHold";
		case "client_hold":
			return "CLIENT_HOLD";
		case "yet_to_schedule":
			return "Yet_To_Schedule";
		case "dropped":
			return "Dropped";
		case "feedbackpending":
			return "FeedbackPending";
		case "noshow":
			return "NoShow";
		case "client":
			return "Client";
				
//		case "HR_REJECTED":
//			return "Rejected";
//		  case "CLIENT_REJECTED": 			return "Rejected";

		 
		case "duplicate" :
			return "Duplicate";
		case "rejected" :
			return "Rejected";
		default:
			return null;
		}
	}

}