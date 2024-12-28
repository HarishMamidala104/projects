package com.ojas.hiring.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.ojas.hiring.dto.CandidateDetailsDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.serviceImpl.GetAllCandidatesDtoOne;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {
//	   public List<Candidate> findById(long cid);

	public Candidate getById(long cid);
	
	@Query(value = "select full_name from data_candidate",nativeQuery = true)
	public List<String> getCandidateNames();
	
	@Query(value = "SELECT c.full_name, c.email_id, c.sub_status, r.customer_name, c.creation_date " +
            "FROM data_candidate c " +
            "JOIN data_rrf r ON c.rrf_id = r.id", nativeQuery = true)
List<Object[]> getCandidateDetailsInfo();
//@Query(value = "SELECT upload_id,file_name FROM data_uploads where link=:id")
//public Optional<Candidate> getCandidateUploading(@PathVariable Long id);

	
	
	
	
	@Query(value = "select * from data_candidate where full_name=:candiateName",nativeQuery = true)
	public List<Candidate> getCandidateInfo(@Param("candiateName")      String fullName);
	
	  @Query(value =
	  "select * from data_candidate order by cid limit 1,1",nativeQuery = true)
	  public Candidate getByRrfId(long id);
	 
	@Query(value = "select * from data_candidate where rrf_id=:id",nativeQuery = true)
	public List<Candidate> getByRrfIdInfo(@Param("id")long id);
	
	@Query(value = "select * from data_candidate dc  where dc.published_on between ?1 and ?2 ", nativeQuery = true)
	public List<Object[]> getAllCandidates(@Param("startDate")LocalDate startDate,@Param("endDate")LocalDate endDate);

//	@Query(value = "select c FROM data_candidate c WHERE c.rrf_id = :rrfId", nativeQuery = true)
	List<Candidate> findByRrfId(Optional<Long> rrf_id);


	@Query(value = "select * from data_candidate where visibility=?1 order by cid desc", nativeQuery = true)
	public List<Candidate> getByvisibility(int visibility);
	
	@Query(value = "select * from data_candidate where employee_id=?1 and visibility=1 order by cid desc",nativeQuery = true)
	public List<Candidate> getByEmployeeId(long employeeId);

	@Query(value = "select count(cid), sub_status from data_candidate where sub_status=?1", nativeQuery = true)
	public List<Object> countSubStatusByStatus(String status);

//	@Query(value = "Select dc from Candidate dc INNER JOIN Interviews dis \r\n"
//			+ "on dc.cid = dis.candidate_id INNER JOIN InterviewFeedback dif \r\n" + "on dif.interview_id = dis.id \r\n"
//			+ "where dif.interview_id=?1",nativeQuery = true)
	@Query(value = "Select * from data_candidate dc INNER JOIN data_interviews_schedule dis \r\n"
			+ "on dc.cid = dis.candidate_id INNER JOIN data_interviews_feedback dif on dif.interview_id = dis.id \r\n"
			+ "where dif.interview_id=?1",nativeQuery = true)
	public Candidate getCandidateByInterviewId(Long interviewId);

//	@Query(value = "Select dc from Candidate dc INNER JOIN Interviews dis \r\n" + "on dc.cid = dis.candidate_id \r\n"
//			+ "where dis.id = ?1")
	@Query(value = "select * from data_candidate dc inner join data_interviews_schedule  dis on dc.cid = dis.candidate_id where dis.id=?1",nativeQuery = true)
	public Candidate getCandidateDataByInyerviewId(long id);

	@Query(value = "select * from data_candidate where status!='Reject'", nativeQuery = true)
	public List<Candidate> getCandidateDetails();

	public Candidate getByCid(long cid);
	
	public Optional<Candidate> findByemailId(String emailId);
	
	public Optional<Candidate> findBymobileNo(String mobileNo);
	
//	@Query(value="select dr.customer_name, count(*) from  data_candidate dc JOIN data_rrf dr ON dr.id = dc.rrf_id where dc.Creation_Date between ?1 and ?2 \r\n"
//	 		+ "group by dr.customer_name;",nativeQuery = true)
//	@Query(value = "select dr.customer_name,dr.open_positions,dr.closed_positions from  data_candidate\r\n"
//			+ " dc JOIN data_rrf dr ON dr.id = dc.rrf_id where dc.Creation_Date between ?1 and ?2",nativeQuery = true)
	@Query(value="SELECT dr.customer_name, \r\n"
			+ "       SUM(dr.open_positions) AS total_open_positions, \r\n"
			+ "       SUM(dr.closed_positions) AS total_closed_positions\r\n"
			+ "FROM data_rrf dr  \r\n"
			+ "WHERE dr.published_on BETWEEN ?1 AND ?2 \r\n"
			+ "GROUP BY dr.customer_name",nativeQuery = true)
	List<Object[]> getInterviewsByCustomersAndDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query(value="select dr.primary_skills, count(*) from data_candidate dc JOIN data_rrf dr ON dr.id = dc.rrf_id where dc.Creation_Date between ?1 and ?2 \r\n"
	 		+ "group by dr.primary_skills",nativeQuery = true)
    List<Object[]> getInterviewsByTechnologiesAndDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    
//	@Query(value = "select * from data_candidate dc  where dc.Creation_Date between ?1 and ?2 ", nativeQuery = true)
    @Query(value = "select status, count(*)from data_candidate as dc where dc.Creation_Date between ?1 and ?2 group by dc.status",nativeQuery = true)
	public List<Object[]> getAllCandidatesForStatus(@Param("endDate")LocalDate endDate,@Param("startDate")LocalDate startDate);

	@Query(value = "select * from data_candidate where status='select' AND sub_status='Onboarded' AND cid=?1",nativeQuery = true)
	public Optional<Candidate> getByCidStatusANDSubStatus(long cid);
	
	@Query(value = "select *  from data_candidate where Creation_Date between :startDate and :endDate ",nativeQuery = true)
	public List<Candidate> getAllCandidateByCustom(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	@Query(value = " select * from data_candidate ",nativeQuery = true)
	public List<Candidate> downloadAllCandidateData();
	
	@Query(value="select sum(case when total_experience > 0 and total_experience <=3 AND visibility=1 then 1 else 0 end) as\r\n"
			+ " count_0_to_3,\r\n"
			+ " sum(case when total_experience > 3 and total_experience <= 6 and visibility=1 then 1 else 0 end) as count_3_to_6,\r\n"
			+ " sum(case when total_experience > 6 and total_experience <= 9 and visibility=1 then 1 else 0 end) as count_6_to_9,\r\n"
			+ " sum(case when total_experience > 9 and total_experience <= 12 and visibility=1 then 1 else 0 end) as count_9_to_12,\r\n"
			+ " sum(case when total_experience > 12 and total_experience <= 15 and visibility=1 then 1 else 0 end) as count_12_to_15,\r\n"
			+ " sum(case when total_experience > 15 and visibility=1 then 1 else 0 end) as count_15 from data_candidate",nativeQuery = true)
	public List<Object[]> getExperienceData();

	@Query(value = "SELECT rr.*, cd.hiring_type AS cd_hiring_type FROM data_rrf rr LEFT JOIN data_candidate cd ON rr.id = cd.rrf_id ", nativeQuery = true)
	List<Map<String, Object>> findAllCandidatesAndRrf();

//	@Query(value = "SELECT * FROM data_rrf where visibility = 1", nativeQuery = true)
	List<Candidate> findByRrf(RRF rrfId);

	@Query(value = "SELECT employee_id FROM data_candidate WHERE rrf_id = :rrfId", nativeQuery = true)
	List<Integer> getCandidatesByRRFId(@Param("rrfId") long rrfId);


	/*
	 * @Query(value = "SELECT " + "rrf.id AS rrfId, " +
	 * "rrf.customer_name AS customerName, " +
	 * "rrf.primary_skills AS primarySkills, " + "rrf.hiring_type AS hiringType, " +
	 * "rrf.job_level AS jobLevel, " + "rrf.min_exp AS minExp, " +
	 * "rrf.max_exp AS maxExp, " + "rrf.budget AS budget, " +
	 * "rrf.secondary_skills AS secondarySkills, " +
	 * "rrf.employee_id AS employeeId, " + "rrf.job_title AS jobTitle, " +
	 * "rrf.job_type AS jobType, " + "rrf.priority AS priority, " +
	 * "rrf.mode_of_work AS modeOfWork, " + "rrf.title AS title, " +
	 * "rrf.owner_of_requirement AS ownerOfRequirement, " + "rrf.city AS city, " +
	 * "rrf.state AS state, " + "rrf.location AS location, " +
	 * "rrf.experience AS experience, " +
	 * "rrf.requirement_name AS requirementName, " +
	 * "rrf.visibility AS visibility, " + "rrf.open_positions AS openPositions, " +
	 * "rrf.closed_positions AS closedPositions, " +
	 * "rrf.candidate_count AS candidateCount, " +
	 * "rrf.published_on AS publishedOn, " + "rrf.created_by AS createdBy, " +
	 * "rrf.total_positions AS totalPositions, " +
	 * "rrf.requirement_status AS requirementStatus, " + "c.cid AS candidateId, " +
	 * "c.full_name AS fullName, " + "c.mobile_no AS mobileNo, " +
	 * "c.email_id AS emailId, " + "c.total_experience AS totalExperience, " +
	 * "c.currently_working_as AS currentlyWorkingAs, " +
	 * "c.currently_working_at AS currentlyWorkingAt, " +
	 * "c.current_location AS currentLocation, " +
	 * "c.preferred_location AS preferredLocation, " +
	 * "c.expected_ctc_pa AS expectedCtcPa, " + "c.notice_period AS noticePeriod, "
	 * + "c.currently_serving_notice_period AS currentlyServingNoticePeriod, " +
	 * "c.comments AS comments, " + "c.availability AS availability, " +
	 * "c.employee_id AS candidateEmployeeId, " +
	 * "c.resource_type AS resourceType, " +
	 * "c.requirement_name AS candidateRequirementName, " +
	 * "c.rrf_link AS rrfLink, " + "c.status AS status, " +
	 * "c.offered_ctc AS offeredCTC, " + "c.visibility AS candidateVisibility, " +
	 * "c.record_author AS recordAuthor, " + "c.ip_address AS ipAddress, " +
	 * "c.vendor AS vendor, " + "c.creation_date AS creationDate, " +
	 * "c.source AS source, " + "c.sub_status AS subStatus, " +
	 * "c.additional_skills AS additionalSkills " + "FROM ots.data_rrf rrf " +
	 * "LEFT JOIN ots.data_candidate c ON c.rrf_id = rrf.id " +
	 * "WHERE rrf.visibility = 1 " + "AND c.visibility = 1 " +
	 * "ORDER BY rrf.id DESC", nativeQuery = true) List<GetAllCandidatesDtoOne>
	 * findAllRRFWithCandidates();
	 */
	
	@Query(value = "SELECT new com.ojas.hiring.dto.GetAllCandidatesDtoOne(" +
            "rrf.id, rrf.customer_name, rrf.primary_skills, rrf.hiring_type, rrf.job_level, " +
            "rrf.min_exp, rrf.max_exp, rrf.budget, rrf.secondary_skills, rrf.employee_id, " +
            "rrf.job_title, rrf.job_type, rrf.priority, rrf.mode_of_work, rrf.title, " +
            "rrf.owner_of_requirement, rrf.city, rrf.state, rrf.location, rrf.experience, " +
            "rrf.requirement_name, rrf.visibility, rrf.open_positions, rrf.closed_positions, " +
            "rrf.candidate_count, rrf.published_on, rrf.created_by, rrf.total_positions, " +
            "rrf.requirement_status, c.cid, c.full_name, c.mobile_no, c.email_id, c.total_experience, " +
            "c.currently_working_as, c.currently_working_at, c.current_location, c.preferred_location, " +
            "c.expected_ctc_pa, c.notice_period, c.currently_serving_notice_period, c.comments, " +
            "c.availability, c.employee_id, c.resource_type, c.requirement_name, c.rrf_link, c.status, " +
            "c.offered_ctc, c.visibility, c.record_author, c.ip_address, c.vendor, c.creation_date, " +
            "c.source, c.sub_status, c.additional_skills) " +
            "FROM ots.data_rrf rrf " +
            "LEFT JOIN ots.data_candidate c ON c.rrf_id = rrf.id " +
            "WHERE rrf.visibility = 1 " +
            "AND c.visibility = 1 " +
            "ORDER BY rrf.id DESC", nativeQuery = true)
    List<GetAllCandidatesDtoOne> findAllRRFWithCandidates();
}










	
	
	
	

