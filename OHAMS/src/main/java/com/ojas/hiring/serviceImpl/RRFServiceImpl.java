package com.ojas.hiring.serviceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ojas.hiring.Exception.BadRequestException;
import com.ojas.hiring.Exception.DataException;
import com.ojas.hiring.ExceptionConstants.ExceptionConstants;
import com.ojas.hiring.commonutility.CommonConstants;
import com.ojas.hiring.entity.AnalyticalDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.entity.Uploads;
import com.ojas.hiring.exceptions.RrfIdNotFound;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.repo.UploadsRepo;
import com.ojas.hiring.service.RRFService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RRFServiceImpl implements RRFService {
	
	private static final Logger logger = LogManager.getLogger(RRFServiceImpl.class);

    private RRFRepo rrfRepo;
    
    @Autowired
    public void setRRFRepo(RRFRepo rrfRepo) {
    	this.rrfRepo = rrfRepo;
    }
  
    private RRFService rrfService;
    
    @Autowired
    public void setRRFService(RRFService rrfService) {
    	this.rrfService = rrfService;
    }

    private EntityManager entityManager;
    
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
    	this.entityManager = entityManager;
    }

    private ObjectMapper mapper;
    
    @Autowired
    public void setObjectMapper(ObjectMapper mapper) {
    	this.mapper = mapper;
    }

    private UploadsRepo uploadsRepo;

    @Autowired
    public void setUploadsRepo(UploadsRepo uploadsRepo) {
    	this.uploadsRepo = uploadsRepo;
    }
    
    private CandidateRepo candidateRepo;
    
    @Autowired
    public void setCandidateRepo(CandidateRepo candidateRepo) {
    	this.candidateRepo = candidateRepo;
    }
    
    @Value("${db.name}")
    private String dbName;
    
    @Override
	public List<AnalyticalDto> getCustomDateInfo(LocalDate startDate, LocalDate endDate) {
		return null;
	}
    
  
	public String findUploadsByID(String table, String cols, int id) {
    	logger.debug("start of findUploadsByID");
        String query = String.format("SELECT %s FROM %s WHERE link=%d", cols, table, id);
        String[] columnNames = cols.split(",");

        String preQuery = String.format(
            "SELECT COLUMN_NAME, COLUMN_TYPE, TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='%s'",
            dbName
        );
        logger.debug("Query is {}", preQuery);
        List<String> columns = new ArrayList<>();
        List<String> columnDataTypes = new ArrayList<>();

        List<Object[]> columnInfo = preQuery(preQuery, columns, columnDataTypes);

        List<String> filteredColumns = new ArrayList<>();
        List<String> filteredColumnTypes = new ArrayList<>();

        for (int i = 0; i < columns.size(); i++) {
            if (table.equalsIgnoreCase(columnInfo.get(i)[2].toString().trim()) && cols.toLowerCase().contains(columns.get(i).toLowerCase())) {
                filteredColumns.add(columns.get(i));
                filteredColumnTypes.add(columnDataTypes.get(i));
            }
        }

        StringBuilder res = postQuery(query, columnNames, filteredColumns, filteredColumnTypes);
        logger.debug("End of findUploadsByID");
        return res.toString();
    }
	
	@SuppressWarnings("unchecked")
	private StringBuilder postQuery(String query, String[] columnNames, List<String> filteredColumns,
			List<String> filteredColumnTypes) {
		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
        StringBuilder res = new StringBuilder("[");

        for (int j = 0; j < valuePairs.size(); j++) {
            StringBuilder jsonRow = new StringBuilder("{");
            for (int k = 0; k < filteredColumns.size(); k++) {
                String value = formatValue(filteredColumnTypes.get(k), valuePairs.get(j)[k]);
                jsonRow.append(String.format("\"%s\":\"%s\",", columnNames[k], value));
            }
            jsonRow.setLength(jsonRow.length() - 1);
            jsonRow.append("}");
            if (j < valuePairs.size() - 1) {
                jsonRow.append(",");
            }
            res.append(jsonRow);
        }
        res.append("]");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> preQuery(String preQuery, List<String> columns, List<String> columnDataTypes) {
		List<Object[]> columnInfo = entityManager.createNativeQuery(preQuery).getResultList();

        columnInfo.stream()
            .filter(record -> !((String) record[0]).trim().equals("next_val"))
            .forEach(record -> {
                columns.add((String) record[0]);
                columnDataTypes.add((String) record[1]);
            });
		return columnInfo;
	}

    private String formatValue(String columnType, Object value) {
    	 logger.debug("Start of formatValue");
        if (value == null) return "";

        switch (columnType.toLowerCase()) {
            case "varchar":
            case "text":
            case "bigint":
            case "int":
            case "smallint":
            case "double":
                return value.toString();
            case "date":
            case "datetime":
            case "timestamp":
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return sdf.format(sdf.parse(value.toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            default:
            	 logger.debug("deafault value is %d", value.toString());
                return value.toString();
        }
    }

    @Override
    public RRF updateRRF(long id, RRF rrf) throws Exception {
        List<RRF> findByRrfs = rrfRepo.findRRFById(id);
        RRF obj = findByRrfs.get(0);
        try {
            if (rrf.getBudget() > 0.0) {
                obj.setBudget(rrf.getBudget());
            }
            if (rrf.getCreatedBy() != null) {
                obj.setCreatedBy(rrf.getCreatedBy());
            }
            if (rrf.getCustomerName() != null) {
                obj.setCustomerName(rrf.getCustomerName());
            }
            if (rrf.getHiringType() != null) {
                obj.setHiringType(rrf.getHiringType());
            }
            if (rrf.getId() > 0) {
                obj.setId(rrf.getId());
            }
            if (rrf.getJobLevel() != null) {
                obj.setJobLevel(rrf.getJobLevel());
            }
            if (rrf.getMaxExp() > 0) {
                obj.setMaxExp(rrf.getMaxExp());
            }
            if (rrf.getMinExp() > 0) {
                obj.setMinExp(rrf.getMinExp());
            }
            if (rrf.getPrimarySkills() != null) {
                obj.setPrimarySkills(rrf.getPrimarySkills());
            }
            if (rrf.getPublishedOn() != null) {
                obj.setPublishedOn(rrf.getPublishedOn());
            }
            if (rrf.getSecondarySkills() != null) {
                obj.setSecondarySkills(rrf.getSecondarySkills());
            }
            if (rrf.getOpenPositions() > 0) {
                obj.setOpenPositions(rrf.getOpenPositions());
            }
            if (rrf.getJobType() != null) {
                obj.setJobType(rrf.getJobType());
            }
            if (rrf.getPriority() != null) {
                obj.setPriority(rrf.getPriority());
            }
            if (rrf.getModeOfWork() != null) {
                obj.setModeOfWork(rrf.getModeOfWork());
            }
            if (rrf.getTitle() != null) {
                obj.setTitle(rrf.getTitle());
            }
            if (rrf.getOwnerOfRequirement() != null) {
                obj.setOwnerOfRequirement(rrf.getOwnerOfRequirement());
            }
            if (rrf.getCity() != null) {
                obj.setCity(rrf.getCity());
            }
//            if(rrf.getCountry() != null) {
//            	obj.setCountry(rrf.getCountry());
//            }
//            if(rrf.getState() != null) {
//            	obj.setState(rrf.getState());
//            }
            if (rrf.getTotalPositions() > 0) {
                obj.setTotalPositions(rrf.getTotalPositions());
            }

            rrfRepo.save(obj);
        } catch (Exception e) {
            throw new DataException("Data base Exception");
        }
        return obj;
    }

    @Override
    public List<Map<String, Object>> getRRFByVisibility() {
        List<Map<String, Object>> rrfByVisibility = rrfRepo.getRRFByVisibility(1);
        List<Map<String, Object>> list = new ArrayList<>();

        for (Map<String, Object> map : rrfByVisibility) {
            Map<String, Object> modifiedMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("published_on")) {
                    Object value = entry.getValue();
                    if (value != null) {
                        if (value instanceof java.sql.Timestamp) {
                            java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
                            long re = timestamp.getTime(); // Convert Timestamp to long
                            Instant ofEpochMilli = Instant.ofEpochMilli(re);
                            // Assuming the timestamps are in the system's default time zone
                            ZoneId defaultZoneId = ZoneId.systemDefault();
                            LocalDateTime dateTime = LocalDateTime.ofInstant(ofEpochMilli, defaultZoneId);
                            // Format to the desired output
                            String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            modifiedMap.put(entry.getKey(), formattedDate);
                        } else {
                            System.out.println("Error: Value for 'published_on' is not a Timestamp.");
                            modifiedMap.put(entry.getKey(), "Invalid date"); // or handle the error as appropriate
                        }
                    } else {
                        System.out.println("Warning: No value provided for 'published_on'.");
                        modifiedMap.put(entry.getKey(), null); // or provide a default value
                    }
                } else {
                    modifiedMap.put(entry.getKey(), entry.getValue());
                }
            }
            list.add(modifiedMap);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getAssignedRRF(long empId) {
        List<Map<String, Object>> assignedRRFList = rrfRepo.getAssignedRRFList(empId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> map : assignedRRFList) {
            Map<String, Object> modifiedMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("published_on")) {
                    Object value = entry.getValue();
                    java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
                    long re = timestamp.getTime(); // Convert Timestamp to long
                    Instant ofEpochMilli = Instant.ofEpochMilli(re);
                    LocalDateTime dateTime = LocalDateTime.ofInstant(ofEpochMilli, ZoneOffset.UTC);
                    String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    modifiedMap.put(entry.getKey(), formattedDate);
                } else {
                    modifiedMap.put(entry.getKey(), entry.getValue());
                }

            }
            list.add(modifiedMap);
        }
        return list;
    }

    @Override
    public List<AnalyticalDto> getCustomDateInfo(LocalDate startDate) {
        List<Object[]> result = rrfRepo.getInterviewData(startDate);

        List<AnalyticalDto> interviewDataList = new ArrayList<>();

        for (Object[] o : result) {
            String customer = o[0].toString();
            Long openPositions = Long.valueOf(o[1].toString());
            Long closedPositions = Long.valueOf(o[2].toString());
            String tecnology = o[3].toString();
            Long cid = Long.valueOf(o[4].toString());
            Long inProgress = Long.valueOf(o[5].toString());
            Long hold = Long.valueOf(o[6].toString());
            Long reject = Long.valueOf(o[7].toString());
            Long selected = Long.valueOf(o[8].toString());
            String onboarded = o[9].toString();
            String offered = o[10].toString();

            AnalyticalDto analyticalDto = new AnalyticalDto();
            analyticalDto.setCustomer(customer);
            analyticalDto.setTechnology(tecnology);
            analyticalDto.setOpenPositions(openPositions);
            analyticalDto.setClosedPositions(closedPositions);
            analyticalDto.setInterviews(cid);
            analyticalDto.setInprogress(inProgress);
            analyticalDto.setHold(hold);
            analyticalDto.setReject(reject);
            analyticalDto.setSelected(selected);
            analyticalDto.setOnboarded(onboarded);
            analyticalDto.setOfferred(offered);
            interviewDataList.add(analyticalDto);
        }

        return interviewDataList;

    }

    @Override
    public List<AnalyticalDto> getInterviewsByTechnologyAndDates(String technology, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = rrfRepo.getInterviewsByTechnologyAndDates(technology, startDate, endDate);

        List<Map<String, Object>> mappedResults = results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("customer", row[0]);
            map.put("openPositions", row[1]);
            map.put("closedPositions", row[2]);
            map.put("technology", row[3]);
            map.put("interviews", row[4]);
            map.put("inprogress", row[5]);
            map.put("hold", row[6]);
            map.put("reject", row[7]);
            map.put("selected", row[8]);
            map.put("onboarded", row[9]);
            map.put("offerred", row[10]);
            return map;
        }).collect(Collectors.toList());

        List<AnalyticalDto> interviewDataList = new ArrayList<>();
        if (mappedResults != null) {
            for (Map<String, Object> map : mappedResults) {
                if (map != null) {
                    AnalyticalDto dto = new AnalyticalDto();
                    dto.setCustomer(map.get("customer") != null ? map.get("customer").toString() : null);
                    dto.setOpenPositions(map.get("openPositions") != null ? Long.valueOf(map.get("openPositions").toString()) : null);
                    dto.setClosedPositions(map.get("closedPositions") != null ? Long.valueOf(map.get("closedPositions").toString()) : null);
                    dto.setTechnology(map.get("technology") != null ? map.get("technology").toString() : null);
                    dto.setInterviews(map.get("interviews") != null ? Long.valueOf(map.get("interviews").toString()) : null);
                    dto.setInprogress(map.get("inprogress") != null ? Long.valueOf(map.get("inprogress").toString()) : null);
                    dto.setHold(map.get("hold") != null ? Long.valueOf(map.get("hold").toString()) : null);
                    dto.setReject(map.get("reject") != null ? Long.valueOf(map.get("reject").toString()) : null);
                    dto.setSelected(map.get("selected") != null ? Long.valueOf(map.get("selected").toString()) : null);
                    dto.setOnboarded(map.get("onboarded") != null ? map.get("onboarded").toString() : null);
                    dto.setOfferred(map.get("offerred") != null ? map.get("offerred").toString() : null);

                    interviewDataList.add(dto);
                }
            }
        }

        return interviewDataList;
    }



    @Override
    public List<AnalyticalDto> getInterviewDataForOneMonth() {
        List<Object[]> result = rrfRepo.getInterviewDataForOneMonth();

        List<AnalyticalDto> interviewDataList = new ArrayList<>();

        for (Object[] o : result) {
            String customer = o[0].toString();
            Long openPositions = Long.valueOf(o[1].toString());
            Long closedPositions = Long.valueOf(o[2].toString());
            String tecnology = o[3].toString();
            Long cid = Long.valueOf(o[4].toString());
            Long inProgress = Long.valueOf(o[5].toString());
            Long hold = Long.valueOf(o[6].toString());
            Long reject = Long.valueOf(o[7].toString());
            Long selected = Long.valueOf(o[8].toString());
            String onboarded = o[9].toString();
            String offered = o[10].toString();

            AnalyticalDto analyticalDto = new AnalyticalDto();
            analyticalDto.setCustomer(customer);
            analyticalDto.setTechnology(tecnology);
            analyticalDto.setOpenPositions(openPositions);
            analyticalDto.setClosedPositions(closedPositions);
            analyticalDto.setInterviews(cid);
            analyticalDto.setInprogress(inProgress);
            analyticalDto.setHold(hold);
            analyticalDto.setReject(reject);
            analyticalDto.setSelected(selected);
            analyticalDto.setOnboarded(onboarded);
            analyticalDto.setOfferred(offered);
            interviewDataList.add(analyticalDto);
        }

        return interviewDataList;
    }

    @Override
    public List<AnalyticalDto> getInterviewDataForOneWeek() {
        List<Object[]> result = rrfRepo.getInterviewDataForOneWeek();

        List<AnalyticalDto> interviewDataList = new ArrayList<>();

        for (Object[] o : result) {

            String customer = o[0].toString();
            Long openPositions = Long.valueOf(o[1].toString());
            Long closedPositions = Long.valueOf(o[2].toString());
            String tecnology = o[3].toString();
            Long cid = Long.valueOf(o[4].toString());
            Long inProgress = Long.valueOf(o[5].toString());
            Long hold = Long.valueOf(o[6].toString());
            Long reject = Long.valueOf(o[7].toString());
            Long selected = Long.valueOf(o[8].toString());
            String onboarded = o[9].toString();
            String offered = o[10].toString();

            AnalyticalDto analyticalDto = new AnalyticalDto();
            analyticalDto.setCustomer(customer);
            analyticalDto.setTechnology(tecnology);
            analyticalDto.setOpenPositions(openPositions);
            analyticalDto.setClosedPositions(closedPositions);
            analyticalDto.setInterviews(cid);
            analyticalDto.setInprogress(inProgress);
            analyticalDto.setHold(hold);
            analyticalDto.setReject(reject);
            analyticalDto.setSelected(selected);
            analyticalDto.setOnboarded(onboarded);
            analyticalDto.setOfferred(offered);
            interviewDataList.add(analyticalDto);
        }

        return interviewDataList;
    }

	@Override
	public List<AnalyticalDto> getInterviewsByCustomerAndDates(String customer, LocalDate startDate, LocalDate endDate) {
	    List<Object[]> result = rrfRepo.getInterviewsByCustomerAndDates(customer, startDate, endDate);

	    List<AnalyticalDto> interviewDataList = new ArrayList<>();

	    if (result != null) {
	        for (Object[] row : result) {
	            if (row != null) {
	                AnalyticalDto analyticalDto = new AnalyticalDto();
	                analyticalDto.setCustomer(customer);
	                analyticalDto.setOpenPositions(row[0] != null ? Long.valueOf(row[0].toString()) : null);
	                analyticalDto.setClosedPositions(row[1] != null ? Long.valueOf(row[1].toString()) : null);
	                analyticalDto.setTechnology(row[2] != null ? row[2].toString() : null);
	                analyticalDto.setInterviews(row[3] != null ? Long.valueOf(row[3].toString()) : null);
	                analyticalDto.setInprogress(row[4] != null ? Long.valueOf(row[4].toString()) : null);
	                analyticalDto.setHold(row[5] != null ? Long.valueOf(row[5].toString()) : null);
	                analyticalDto.setReject(row[6] != null ? Long.valueOf(row[6].toString()) : null);
	                analyticalDto.setSelected(row[7] != null ? Long.valueOf(row[7].toString()) : null);
	                analyticalDto.setOnboarded(row[8] != null ? row[8].toString() : null);
	                analyticalDto.setOfferred(row[9] != null ? row[9].toString() : null);

	                interviewDataList.add(analyticalDto);
	            }
	        }
	    }

	    return interviewDataList;
	}


//    @Override
//	public List<AnalyticalDto> getInterviewsByStatusAndDates(String status, LocalDate startDate, LocalDate endDate) {
//    	   List<Object[]> result = rrfRepo.getInterviewsByStatusAndDates(status,startDate, endDate);
//    	   
//           List<AnalyticalDto> interviewDataList = new ArrayList<>();
//
//           for (Object[] o : result) {
//           	String customer = o[0].toString();
//               String tecnology = o[1].toString();
//               Long cid = Long.valueOf(o[2].toString());
//               Long inProgress = Long.valueOf(o[3].toString());
//               Long hold = Long.valueOf(o[4].toString());
//               Long reject = Long.valueOf(o[5].toString());
//               Long selected = Long.valueOf(o[6].toString());
//
//
//               AnalyticalDto analyticalDto = new AnalyticalDto();
//               analyticalDto.setCustomer(customer);
//               analyticalDto.setTechnology(tecnology);
//               analyticalDto.setInterviews(cid);
//               analyticalDto.setInprogress(inProgress);
//               analyticalDto.setHold(hold);
//               analyticalDto.setReject(reject);
//               analyticalDto.setSelected(selected);
//               interviewDataList.add(analyticalDto);
//           }
//
//           return interviewDataList;
//	}

    public List<RRF> getAllDetails(String requirement_status, String priority) {
        List<RRF> allRRFDetails = rrfRepo.getAllRRFDetails();
        List<RRF> details = new ArrayList<>();
        for (RRF rrf : allRRFDetails) {
            if (rrf.getRequirementStatus().equals(requirement_status)) {
                details.add(rrf);
            }
            if (rrf.getPriority().equals(priority)) {
                details.add(rrf);
            }
        }
        return details;
    }

    @Override
    public String saveBulkUpload(List<RRF> details) {
        String result;
        if (details.isEmpty()) {
            return "No data to upload.";
        }
        try {
            details.forEach(i -> {
                RRF rrf = new RRF();
                rrf.setRequirementName(i.getRequirementName());
                rrf.setCustomerName(i.getCustomerName());
                rrf.setPrimarySkills(i.getPrimarySkills());
                rrf.setSecondarySkills(i.getSecondarySkills());
                rrf.setOwnerOfRequirement(i.getOwnerOfRequirement());
                rrf.setHiringType(i.getHiringType());
                rrf.setTotalPositions(i.getTotalPositions());
                rrf.setModeOfWork(i.getModeOfWork());
                rrf.setJobType(i.getJobType());
                rrf.setJobLevel(i.getJobLevel());
                rrf.setPublishedOn(i.getPublishedOn());
                rrf.setJobDescription(i.getJobDescription());
                rrf.setOpenPositions(i.getOpenPositions());
                rrf.setClosedPositions(i.getClosedPositions());
                rrf.setMinExp(i.getMinExp());
                rrf.setMaxExp(i.getMaxExp());
                rrf.setBudget(i.getBudget());
                rrf.setCandidateCount(i.getCandidateCount());
                rrf.setVisibility(i.getVisibility());
                rrf.setEmailAddress(i.getEmailAddress());
                rrf.setCreatedBy(i.getCreatedBy());
                rrf.setIpAddress(i.getIpAddress());
                rrf.setEmployeeId(i.getEmployeeId());
                rrf.setCandidate(i.getCandidate());
                rrf.setJobTitle(i.getJobTitle());
                rrf.setPriority(i.getPriority());
                rrf.setTitle(i.getTitle());
                rrf.setCity(i.getCity());
                rrf.setState(i.getState());
                rrf.setRequirementStatus(i.getRequirementStatus());
                rrf.setLocation(i.getLocation());
                rrf.setExperience(i.getExperience());
                rrfRepo.save(rrf);
            });
            int size = details.size();
            if (size > 1) {
                result = size + "records have been uploaded successfully.";
            } else {
                result = size + " record has been uploaded successfully.";
            }
            return result;

        } catch (Exception e) {
            result = "Error saving data to the database: " + e.getMessage();
        }
        return result;
    }

    private Workbook checkWorkbook(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        if (fileName != null && (fileName.endsWith(".xlsx") || fileName.endsWith(".XLSX") || fileName.endsWith(".xlsb")
                || fileName.endsWith(".XLSB"))) {
            // Handle XLSX
            return new XSSFWorkbook(file.getInputStream());
        } else if (fileName != null && (fileName.endsWith(".xls") || fileName.endsWith(".XLS"))) {
            // Handle XLS
            return new HSSFWorkbook(file.getInputStream());
        } else {
            // Throw an exception or handle the unsupported file format
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }
    }

    public String saveDataFromExcel(MultipartFile file, HttpServletRequest request) throws IOException {
        List<RRF> rrfList = new ArrayList<>();
        Workbook workbook = checkWorkbook(file);
        String[] expectedHeaders = {
                "Requirement", "No of Positions", "Hire Type", "Customer Name", "Skill",
                "Additional Skill", "Owner of Requirement", "Budget (LPA)", "Mode of Work",
                "Location", "Job Type", "Job Level", "Priority", "Experience", "Job Description",
                "Date", "Attachment"
        };
        Map<String, Integer> headerIndexMap = new HashMap<>();

        try {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = null;

            // Iterate through rows to find the header row
            for (Row currentRow : sheet) {
                if (!isRowEmpty(currentRow)) {
                    boolean headerRowFound = true;
                    for (String expectedHeader : expectedHeaders) {
                        boolean headerPresent = false;
                        for (int cellNum = 0; cellNum < currentRow.getLastCellNum(); cellNum++) {
                            Cell cell = currentRow.getCell(cellNum);
                            String cellValue = getCellValueAsString(cell);
                            if (expectedHeader.equals(cellValue)) {
                                headerIndexMap.put(expectedHeader, cellNum);
                                headerPresent = true;
                                break;
                            }
                        }
                        if (!headerPresent) {
                            headerRowFound = false;
                            headerIndexMap.clear();
                            break;
                        }
                    }
                    if (headerRowFound) {
                        headerRow = currentRow;
                        break;
                    }
                }
            }

            if (headerRow == null) {
                throw new IOException("Header row not found or missing expected headers");
            }

            // Process data rows starting after the header row
            for (int rowIndex = headerRow.getRowNum() + 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null && !isRowEmpty(row)) {
                    RRF rrf = new RRF();
                    for (String header : expectedHeaders) {
                        Cell cell = row.getCell(headerIndexMap.get(header));
                        switch (header) {
                            case "Requirement":
                                rrf.setRequirementName(getCellValueAsString(cell));
                                break;
                            case "No of Positions":
                                rrf.setTotalPositions(Integer.parseInt(getCellValueAsString(cell)));
                                break;
                            case "Hire Type":
                                rrf.setHiringType(getCellValueAsString(cell));
                                break;
                            case "Customer Name":
                                rrf.setCustomerName(getCellValueAsString(cell));
                                break;
                            case "Skill":
                                rrf.setPrimarySkills(getCellValueAsString(cell));
                                break;
                            case "Additional Skill":
                                rrf.setSecondarySkills(getCellValueAsString(cell));
                                break;
                            case "Owner of Requirement":
                                rrf.setOwnerOfRequirement(getCellValueAsString(cell));
                                break;
                            case "Budget (LPA)":
                                rrf.setBudget(Double.parseDouble(getCellValueAsString(cell)));
                                break;
                            case "Mode of Work":
                                rrf.setModeOfWork(getCellValueAsString(cell));
                                break;
                            case "Location":
                                rrf.setLocation(getCellValueAsString(cell));
                                break;
                            case "Job Type":
                                rrf.setJobType(getCellValueAsString(cell));
                                break;
                            case "Job Level":
                                rrf.setJobLevel(getCellValueAsString(cell));
                                break;
                            case "Priority":
                                rrf.setPriority(getCellValueAsString(cell));
                                break;
                            case "Experience":
                                rrf.setExperience(getCellValueAsString(cell));
                                break;
                            case "Job Description":
                                rrf.setJobDescription(getCellValueAsString(cell));
                                break;
                            case "Date":
                                if (cell != null && DateUtil.isCellDateFormatted(cell)) {
                                    rrf.setPublishedOn(cell.getDateCellValue());
                                }
                                break;
                            case "Attachment":
                                // Handle attachment logic if needed
                                break;
                        }
                    }
                    rrf.setIpAddress(request.getRemoteAddr());
                    rrfList.add(rrf);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Replace with appropriate error handling
        }

        return saveBulkUpload(rrfList);
    }

    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Handle Date format if needed
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());

                } else {
                    // Convert numeric value to integer
                    return String.valueOf((int) cell.getNumericCellValue());
                }

            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                CellValue formulaCellValue = evaluator.evaluate(cell);

            case Cell.CELL_TYPE_ERROR:
                return "Error: " + cell.getErrorCellValue();
            default:
                return "";
        }
    }
    
 
	
	
	public RRF setRrfFields(String createdBy,String requirementName, String experience, String location, double budget, String primarySkills,
			String secondarySkills, String jobDescription, String jobLevel, int openPositions, int closedPositions,
			String customerName, String hiringType, String jobTitle, Date publishedOn, String jobType, String priority,
			String modeOfWork, String title, String ownerOfRequirement, String city, String state, int totalPositions, String requirementStatus) {
		logger.debug("start of setRrfFields");
		RRF rrf = new RRF();
		rrf.setId(0);
		rrf.setCreatedBy(createdBy);
		rrf.setRequirementName(requirementName);
		rrf.setCustomerName(customerName);
		rrf.setBudget(budget);
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
		rrf.setRequirementStatus(requirementStatus);
		rrf.setOwnerOfRequirement(ownerOfRequirement);
		rrf.setCity(city);
		rrf.setState(state);
		rrf.setTotalPositions(totalPositions);
		rrf.setPublishedOn(publishedOn);
		logger.debug("End of setRrfFields");
		return rrf;
	}

	public RRF inputParameters(Map<String, String> parameters) throws IOException {
		logger.debug("Start of inputParameters");
		
		// Initialize variables with default values
		String createdBy = null;
		String experience = null;
		String location = null;
		double budget = 0.0;
		String primarySkills = null;
		String secondarySkills = null;
		String jobDescription = null;
		String jobLevel = null;
		int openPositions = 0;
		int closedPositions = 0;
		String customerName = null;
		String hiringType = null;
		String jobTitle = null;
		Date publishedOn = null;
		String jobType = null;
		String priority = null;
		String modeOfWork = null;
		String title = null;
		String ownerOfRequirement = null;
		String city = null;
		String state = null;
		String requirementStatus = null;
		String requirementName=null;
		int totalPositions = 0;

		for (Map.Entry<String, String> pair : parameters.entrySet()) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = pair.getKey();
			String value = pair.getValue();
			logger.debug(String.format("Key (name) is: %s, Value is: %s", key, value));

			switch (key) {
			case "createdBy":
				createdBy = value;
				break;
			case "requirementName":
				requirementName=value;
				break;
			case "experience":
				experience = value;
				break;
			case "budget":
				budget = parseDouble(value, 0.0);
				break;
			case "location":
				location = value;
				break;
			case "interviewModes":
				break;
			case "primarySkills":
				primarySkills = value;
				break;
			case "secondarySkills":
				secondarySkills = value;
				break;
			case "jobLevel":
				jobLevel = value;
				break;
			case "jobDescription":
				jobDescription = value;
				break;
			case "customerName":
				customerName = value;
				break;
			case "customerDetails":
				break;
			case "hiringType":
				hiringType = value;
				break;
			case "hiringObjective":
				break;
			case "jobTitle":
				jobTitle = value;
				break;
			case "openPositions":
				openPositions = parseInt(value, 0);
				break;
			case "closedPositions":
				closedPositions = parseInt(value, 0);
				break;
			case "jobType":
				jobType = value;
				break;
			case "priority":
				priority = value;
				break;
			case "modeOfWork":
				modeOfWork = value;
				break;
			case "title":
				title = value;
				break;
			case "ownerOfRequirement":
				ownerOfRequirement = value;
				break;
			case "city":
				city = value;
				break;
			case "country":
				break;
			case "state":
				state = value;
				break;
			case "requirementStatus":
				requirementStatus = value;
				break;
			case "totalPositions":
				totalPositions = parseInt(value, 0);
				break;
			case "publishedOn":

				publishedOn = parseDate(value, sdf);
				System.out.println(publishedOn);
				break;
			case "sheduledOn":
				break;
			default:
				logger.error(String.format("Unhandled inputParameter key: %s", key));
				break;
			}
		}

		checkMandatoryFields(experience,requirementName, budget, primarySkills, secondarySkills, jobDescription, jobLevel,
				openPositions, customerName, hiringType, jobType, priority, modeOfWork, ownerOfRequirement,
				requirementStatus);
		
		logger.debug("End  of inputParameters");
		return setRrfFields(createdBy,requirementName, experience, location, budget, primarySkills, secondarySkills, jobDescription,
				jobLevel, openPositions, closedPositions, customerName, hiringType, jobTitle, publishedOn, jobType,
				priority, modeOfWork, title, ownerOfRequirement, city, state, totalPositions, requirementStatus);
	}

	private void checkMandatoryFields(String experience,String requirementName, double budget, String primarySkills, String secondarySkills,
			String jobDescription, String jobLevel, int openPositions, String customerName, String hiringType,
			String jobType, String priority, String modeOfWork, String ownerOfRequirement, String requirementStatus) {
		logger.debug("Start  of checkMandatoryFields");
		List<String> nullOrEmptyFields = new ArrayList<>();
		 if (ObjectUtils.isEmpty(hiringType)) {
		        nullOrEmptyFields.add("hiringType");
		    }
		        
		            
		        if (ObjectUtils.isEmpty(experience)) {
		            nullOrEmptyFields.add("experience");
		        }
		        
		        if (customerName == null || customerName.isBlank()) {
		            nullOrEmptyFields.add("customerName");
		        }
		        if (primarySkills == null || primarySkills.isBlank()) {
		            nullOrEmptyFields.add("primarySkills");
		        }
		        
		        if(requirementName == null || requirementName.isBlank()) {
		            nullOrEmptyFields.add("requirementName");
		        }
		        
		         if (budget <= 0) { // Assuming 0 or negative budget is invalid
		                nullOrEmptyFields.add("budget");
		            }
		        
		        if (ObjectUtils.isEmpty(jobLevel)) {
		            nullOrEmptyFields.add("jobLevel");
		        }

		        if (!nullOrEmptyFields.isEmpty()) {
		            String fields = String.join(", ", nullOrEmptyFields);
		            JsonNode json = mapper.convertValue(fields, JsonNode.class);
		            logger.debug("End of checkMandatoryFields and missing fields are {}", json);
		            throw new BadRequestException(ExceptionConstants.CHECK_MANDATORY_FIELDS, "Missing or Empty Fields/Field",
		                    "The following Field/fields are missing or empty:" + json);
		        }
		    	}

	private double parseDouble(String value, double defaultValue) {
		logger.debug("start of parseDouble");
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private int parseInt(String value, int defaultValue) {
		logger.debug("start of parseInt");
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private Date parseDate(String value, SimpleDateFormat sdf) {
		logger.debug("start of parseDate");
		try {
			sdf= new SimpleDateFormat("MM-dd-yyyy");
			return sdf.parse(value);
		} catch (ParseException e) {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public String processRrfAndFiles(RRF rrf, MultipartFile[] rrqDoc) {
		logger.debug("start of processRrfAndFiles");
		try {
			String requirementName = null;
			String returnMessages = null;
			if (StringUtils.isEmpty(returnMessages)) {
				logger.debug("--------RRF Entry ----------");

				Date date = new Date();
				String timestamp = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(date);
				logger.debug("time is :{}", timestamp);
				logger.debug("RRF data is {}", mapper.convertValue(rrf, JsonNode.class));

				RRF savedRrf = rrfRepo.saveAndFlush(rrf);
				logger.debug("saved RRF date is :{}", mapper.convertValue(savedRrf, JsonNode.class));
				if (!StringUtils.isEmpty(requirementName)) {
					rrf.setRequirementName(requirementName);
					rrfRepo.save(rrf);
				}

				if (rrqDoc != null && rrqDoc.length > 0) {
					List<Uploads> uploads = Arrays.stream(rrqDoc).filter(doc -> doc != null && doc.getSize() > 0)
							.map(doc -> {
								String fileName = doc.getOriginalFilename();
								String fileExtension = FilenameUtils.getExtension(fileName);
								double kilobytes = doc.getSize() / 1024.0;
								byte[] fileContent = new byte[0];

								try {
									fileContent = doc.getBytes();
								} catch (IOException e) {
									logger.error("Error reading file content: {}", fileName, e);
								}

								return new Uploads(0, fileContent, fileName, fileExtension, kilobytes, timestamp, "RRF",
										savedRrf.getId());
							}).collect(Collectors.toList());

					uploads.forEach(uploadsRepo::saveAndFlush);
				}
				logger.debug("End of processRrfAndFiles <------> RRF Successfully Created");
				return new JSONObject().put("status", "RRF Successfully Created").toString();
			} else {
				logger.debug("End of processRrfAndFiles <-------> RRF Not Created");
				return new JSONObject().put("status", "RRF Not Created=" + returnMessages).toString();
			}
		} catch (Exception e) {
			throw new BadRequestException(ExceptionConstants.PROCESS_RRF_DATA_FILES,"Exception occured at processRrfAndFiles","Error occured at saving the rrf data of uploading a file");
		}
	}

	public List<AnalyticalDto> fetchAggregatedInterviewData(String technology, String customer, LocalDate startDate,
			LocalDate endDate) {
		logger.debug("Start of fetchAggregatedInterviewData");
		List<AnalyticalDto> responseList = null;
		if (technology != null) {
			responseList = rrfService.getInterviewsByTechnologyAndDates(technology, startDate, endDate);
			logger.debug("End of getInterviewsByTechnologyAndDates and responseList is {}",
					mapper.convertValue(responseList, JsonNode.class));
		} else if (customer != null) {
			responseList = rrfService.getInterviewsByCustomerAndDates(customer, startDate, endDate);
			logger.debug("End of getInterviewsByCustomerAndDates and responseList is {}",
					mapper.convertValue(responseList, JsonNode.class));
		} else if (startDate != null) {
			responseList = rrfService.getCustomDateInfo(startDate);
			if (isLastWeek(startDate, endDate)) {
				responseList = rrfService.getInterviewDataForOneWeek();
			} else if (isLastMonth(startDate, endDate)) {
				responseList = rrfService.getInterviewDataForOneMonth();
			} else {
				responseList = rrfService.getCustomDateInfo(startDate);
			}
		}
		logger.debug("End of fetchAggregatedInterviewData and responseList is {}",
				mapper.convertValue(responseList, JsonNode.class));
		return responseList;
	}

	private boolean isWithinRange(LocalDate startDate, LocalDate endDate, LocalDate rangeStart, LocalDate rangeEnd) {
	    logger.debug("Checking if dates are within range: {} to {}", rangeStart, rangeEnd);
	    return !startDate.isBefore(rangeStart) && !endDate.isAfter(rangeEnd);
	}

	private boolean isLastWeek(LocalDate startDate, LocalDate endDate) {
		logger.debug("End of isLastWeek");
	    return isWithinRange(startDate, endDate, LocalDate.now().minusWeeks(1), LocalDate.now());
	}

	private boolean isLastMonth(LocalDate startDate, LocalDate endDate) {
		logger.debug("End of isLastMonth");
	    return isWithinRange(startDate, endDate, LocalDate.now().minusMonths(1), LocalDate.now());
	}
	
	public ResponseEntity<List<Map<String, String>>> showMoreData(long rrfId) {
	    logger.debug("Start of showMoreData");

	    RRF rrf = rrfRepo.findById(rrfId)
	        .orElseThrow(() -> new RrfIdNotFound("Invalid RRFID"));

	    int[] experienceRange = parseExperienceRange(rrf.getExperience());
	    logger.debug("Experience range is {}", experienceRange);

	    List<Object[]> rmgDetails = getRmgDetails(rrf, experienceRange);
	    if (rmgDetails == null || rmgDetails.isEmpty()) {
	        logger.debug("RMG details are null or empty");
	       throw new BadRequestException(ExceptionConstants.RMG_DETAILS_NULL_EMPTY,"No RMG details found after filtering. The result list is empty.",
	    	        "Ensure that there are valid RMG details and that they match the criteria.");
	    }
	    logger.debug("RMG Details is {}", mapper.convertValue(rmgDetails, JsonNode.class));

	    // Get candidates by RRF ID
	    List<Integer> candidatesByRRFId = getCandidatesByRRFId(rrfId);
	    if (candidatesByRRFId == null || candidatesByRRFId.isEmpty()) {
	        logger.debug("Candidates by RRF ID are null or empty");
	        throw new BadRequestException(ExceptionConstants.CANDIDATES_BY_RRF_ID_NULL_EMPTY,"Candidates by RRF ID are null or empty","Candidates by RRF ID cannot be null");
	    }
	    logger.debug("Candidates By RRFId is {}", mapper.convertValue(candidatesByRRFId, JsonNode.class));

	    List<Map<String, String>> resultList = filterAndMapRmgDetails(rmgDetails, candidatesByRRFId);
	    if (resultList == null || resultList.isEmpty()) {
	        logger.debug("Result list is null or empty");
	        throw new BadRequestException(
	        	    ExceptionConstants.MAPPING_RMG_DETAILS,
	        	    "Filtering and mapping of RMG details resulted in an empty or null list.",
	        	    "Ensure that RMG details are correctly filtered and mapped according to the criteria."
	        	);

	    }
	    logger.debug("End of showMoreData and result is {}", mapper.convertValue(resultList, JsonNode.class));

	    return new ResponseEntity<>(resultList, HttpStatus.OK);
	}


	@SuppressWarnings("deprecation")
	private int[] parseExperienceRange(String experience) {
		logger.debug("Start of parseExperienceRange");
		 if (StringUtils.isEmpty(experience)) {
			 String errorMessage = String.format("experince cannot be null or empty form RRF  %d", experience);
				throw new BadRequestException(ExceptionConstants.EXPERIENCE_CANNOT_BE_NULL_EMPTY, "experince is null/empty from RRF data",
						errorMessage);
			}
	    String[] parts = experience.split("-");
	    int minExp = Integer.parseInt(parts[0].trim());
	    logger.debug("min is Exp %s", minExp);
	    int maxExp = Integer.parseInt(parts[1].trim());
	    logger.debug("max is Exp %s", maxExp);
	    logger.debug("End of parseExperienceRange");
	    return new int[]{minExp, maxExp};
	}

	private List<Object[]> getRmgDetails(RRF rrf, int[] experienceRange) {
		logger.debug("End of getRmgDetails");
	    return rrfRepo.getRMGDetailsForShowMore(experienceRange[0], experienceRange[1], rrf.getPrimarySkills());
	}

	private List<Integer> getCandidatesByRRFId(long rrfId) {
		logger.debug("End of getCandidatesByRRFId and rrfId is %s", rrfId);
	    return candidateRepo.getCandidatesByRRFId(rrfId);
	}

	private List<Map<String, String>> filterAndMapRmgDetails(List<Object[]> rmgDetails, List<Integer> candidatesByRRFId) {
		logger.debug("Start of filterAndMapRmgDetails");
	    return rmgDetails.stream()
	        .filter(objects -> !candidatesByRRFId.contains((int) objects[0]))
	        .map(objects -> {
	            Map<String, String> map = new LinkedHashMap<>();
	            map.put(CommonConstants.ID, objects[0].toString());
	            map.put(CommonConstants.NAME, objects[1].toString());
	            map.put(CommonConstants.TECHNOLOGY, objects[2].toString());
	            map.put(CommonConstants.EXPERIENCE, objects[3].toString());
	            logger.debug(String.format("End of filterAndMapRmgDetails and details is {}", map));
	            return map;
	        })
	        .collect(Collectors.toList());
	}
	
	
	public String fetchRRFDetails(long id, String jsonString) throws JsonProcessingException {
		logger.debug("Start of fetchRRFDetails");
		Optional<RRF> rrfData = rrfRepo.findById(id);
		logger.debug("RRF Data is %d", mapper.convertValue(rrfData, JsonNode.class));
		if (rrfData.isPresent()) {
			RRF rrf = rrfData.get();
			rrfRepo.save(rrf);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rrf);
			logger.info("/hiring/api/getRRFDetails/" + id + " --> Result : " + jsonString);
		} else {
			logger.error("RRF with id " + id + " not found.");
			throw new RrfIdNotFound("Invalid RRFID" + id);
		}
		return jsonString;
	}
	
	
	public String deleteRecordById(long id) {
		logger.debug("Start of fetchRRFDetails");
		RRF byId = rrfRepo.getById(id);
		if (byId == null) {
			throw new RrfIdNotFound("Record not found for ID: " + id);
		}

		byId.setVisibility(0);

		List<Candidate> candidates = byId.getCandidate();
		if (candidates != null && !candidates.isEmpty()) {
			for (Candidate candidate : candidates) {
				if (candidate != null) {
					candidate.setVisibility(0);
				}
			}
		}
		rrfRepo.save(byId);
		logger.debug("End of fetchRRFDetails");
		return "Deleted Successfully";
	}



}
