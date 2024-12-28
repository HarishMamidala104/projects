package com.ojas.hiring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


import com.ojas.hiring.entity.AnalyticalDto;
import com.ojas.hiring.entity.RRF;

public interface RRFService {

    RRF updateRRF(long id, RRF rrf) throws Exception;
    
    
    
    List<Map<String, Object>> getRRFByVisibility();
    
    List<Map<String, Object>> getAssignedRRF(long empId);


    List<AnalyticalDto> getCustomDateInfo(LocalDate startDate, LocalDate endDate);
    
    List<AnalyticalDto> getCustomDateInfo(LocalDate startDate);

    List<AnalyticalDto> getInterviewsByTechnologyAndDates(String technology, LocalDate startDate, LocalDate endDate);

    List<AnalyticalDto> getInterviewsByCustomerAndDates(String customer, LocalDate startDate, LocalDate endDate);

    List<AnalyticalDto> getInterviewDataForOneMonth();

    List<AnalyticalDto> getInterviewDataForOneWeek();
    
    List<RRF> getAllDetails(String requirement_status, String priority);
    
    String saveBulkUpload(List<RRF> details);


//    List<AnalyticalDto> getInterviewsByCustomerAndDates(String customer, LocalDate startDate, LocalDate endDate);
//
//	List<AnalyticalDto> getInterviewsByStatusAndDates(String status, LocalDate startDate, LocalDate endDate);

}
