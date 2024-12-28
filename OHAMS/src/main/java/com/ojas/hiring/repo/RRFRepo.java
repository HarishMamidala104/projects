package com.ojas.hiring.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.GetAllCandidatesDtoInfo;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.RRF;

@Repository
public interface RRFRepo extends JpaRepository<RRF, Long> {

	
	 @Query(value="SELECT DISTINCT owner_of_requirement FROM data_rrf WHERE visibility=1 AND requirement_status='Active'", nativeQuery = true)
	    List<String> getOwnersdata();

	 @Query(value = "SELECT " +
             "rrf.id, " +
             "rrf.customer_name, " +
             "rrf.primary_skills, " +
             "rrf.owner_of_requirement, " +
             "rrf.requirement_name, " +
             "rrf.open_positions, " +
             "rrf.closed_positions, " +
             "rrf.candidate_count, " +  // Added candidate_count
             "rrf.published_on, " +
             "rrf.total_positions " +
             "FROM data_rrf rrf " +
             "WHERE rrf.owner_of_requirement IN (:owners) " +
             "AND rrf.visibility = 1 " +
             "AND rrf.requirement_status = 'Active';",
             nativeQuery = true)
List<Object[]> getRRFBDMSpecificInfo(@Param("owners") List<String> owners);


	
	
	
	
	
	
	
	
	
	
	
	
	
	Optional<RRF> findById(Long ids);

	Optional<RRF> findById(Candidate candidate);
	
	@Query(value="select * from data_rrf where id=?1",nativeQuery = true)
	RRF getById(long id);

	@Query(value = "select * from data_rrf where id=?1", nativeQuery = true)
	RRF getByRRFDataById(long id);

	List<RRF> findRRFById(long id);

//    @Query(value = " select * from data_rrf dr where dr.visibility=1  order by case when dr.priority='High' and dr.requirement_status='New' \r\n"
//    		+ " then 1  when dr.priority='Medium' and dr.requirement_status='New' then 2 \r\n"
//    		+ " when dr.priority='Low' and dr.requirement_status='New' then 3 \r\n"
//    		+ " when dr.priority='High' and dr.requirement_status='Inprogress' then 4\r\n"
//    		+ " when dr.priority='Medium' and dr.requirement_status='Inprogress' then 5\r\n"
//    		+ " when dr.priority='Low' and dr.requirement_status='Inprogress' then 6 end ", nativeQuery = true)
//    @Query(value = "SELECT \r\n"
//    		+ "    dr.requirement_Name,\r\n"
//    		+ "    dr.id,\r\n"
//    		+ "    dr.customer_name,\r\n"
//    		+ "    dr.priority,\r\n"
//    		+ "    dr.owner_of_requirement,\r\n"
//    		+ "    dr.created_by,\r\n"
//    		+ "    dr.total_positions,\r\n"
//    		+ "    dr.open_positions,\r\n"
//    		+ "    dr.closed_positions,\r\n"
//    		+ "    dr.published_on,\r\n"
//    		 + "    dr.email,\r\n"
//    		+ "    dr.requirement_status,\r\n"
//    		+ "    (SELECT COUNT(*) FROM data_candidate WHERE requirement_Name = dr.requirement_Name) AS candidates_count\r\n"
//    		+ "FROM \r\n"
//    		+ "    (\r\n"
//    		+ "        SELECT \r\n"
//    		+ "            dr.*, \r\n"
//    		+ "            CASE \r\n"
//    		+ "                WHEN dr.priority='High' AND dr.requirement_status='New' THEN 1\r\n"
//    		+ "                WHEN dr.priority='Medium' AND dr.requirement_status='New' THEN 2\r\n"
//    		+ "                WHEN dr.priority='Low' AND dr.requirement_status='New' THEN 3\r\n"
//    		+ "                WHEN dr.priority='High' AND dr.requirement_status='Inprogress' THEN 4\r\n"
//    		+ "                WHEN dr.priority='Medium' AND dr.requirement_status='Inprogress' THEN 5\r\n"
//    		+ "                WHEN dr.priority='Low' AND dr.requirement_status='Inprogress' THEN 6\r\n"
//    		+ "            END AS priority_order\r\n"
//    		+ "        FROM \r\n"
//    		+ "            data_rrf dr\r\n"
//    		+ "        WHERE \r\n"
//    		+ "            dr.visibility = 1\r\n"
//    		+ "    ) dr\r\n"
//    		+ "ORDER BY \r\n"
//    		+ "    priority_order",nativeQuery = true)
//	@Query(value = "select dr.id,dr.requirement_Name,dr.customer_name,dr.priority,dr.owner_of_requirement,dr.created_by,dr.total_positions, dr.open_positions\r\n"
//			+ "    		,dr.closed_positions,dr.published_on,dr.email,dr.requirement_status,(SELECT COUNT(*) FROM data_candidate WHERE requirement_Name = dr.requirement_Name) AS candidates_count from data_rrf dr where visibility=1 Order By id desc;", nativeQuery = true)
	@Query(value="select dr.id,dr.requirement_Name,dr.customer_name,dr.priority,dr.owner_of_requirement,dr.created_by,dr.total_positions, dr.open_positions\r\n"
			+ ",dr.closed_positions,dr.published_on,dr.email,dr.requirement_status,dr.candidate_count,"
			+ "dr.hiring_type,dr.job_level,dr.primary_skills,dr.secondary_skills,dr.budget,dr.job_type,dr.mode_of_work,dr.state,dr.job_description,dr.experience from data_rrf dr where visibility=1 Order By id desc;",nativeQuery = true)
	public List<Map<String, Object>> getRRFByVisibility(long visibility);

	@Query(value = "select dr.customer_name,dr.open_positions,dr.closed_positions from data_rrf as dr where \r\n"
			+ "data_candidate.Creation_Date between ?1 and ?2", nativeQuery = true)
	List<Object[]> getInterviewsByCustomersAndDates(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	@Query(value = "select \r\n" + "    		        rr.customer_name AS customer,\r\n"
			+ "    		     rr.open_positions As openedPositions,\r\n"
			+ "    		          rr.closed_positions As closedPositions,\r\n"
			+ "    		            rr.primary_skills AS technology,\r\n"
			+ "    					COUNT(DISTINCT ci.cid) AS interviews,\r\n"
			+ "    					SUM(CASE WHEN ci.status = 'Inprogress' THEN 1 ELSE 0 END) AS inprogress,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Hold' THEN 1 ELSE 0 END) AS hold,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Rejected' THEN 1 ELSE 0 END) AS rejected,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Selected' THEN 1 ELSE 0 END) AS selected,\r\n"
			+ "                       sum(CASE WHEN ci.sub_status = 'onboarded' THEN 1 ELSE 0 END) AS onboarded,\r\n"
			+ "                       sum(CASE WHEN ci.sub_status = 'offered' THEN 1 ELSE 0 END) AS offerred\r\n"
			+ "    		          FROM\r\n" + "    		               data_rrf rr\r\n" + "    		           JOIN\r\n"
			+ "    		            data_candidate ci ON rr.id = ci.rrf_id\r\n" + "    		           LEFT JOIN\r\n"
			+ "    		         data_interviews_schedule dis ON ci.cid = dis.candidate_id\r\n"
			+ "    		          WHERE\r\n" + "    		    dis.scheduled_on >= scheduled_on\r\n"
			+ "    		           AND dis.scheduled_on NOT REGEXP '^[0-9]{4}-[0-9]{2}-W[0-9]{2}$'\r\n"
			+ "    		          GROUP BY\r\n" + "    				customer, technology", nativeQuery = true)
	List<Object[]> getInterviewDataForOneWeek();

//    @Query(value = "select \r\n"
//    		+ "    		        rr.customer_name AS customer,\r\n"
//    		+ "    		     rr.open_positions As openedPositions,\r\n"
//    		+ "    		          rr.closed_positions As closedPositions,\r\n"
//    		+ "    		            rr.primary_skills AS technology,\r\n"
//    		+ "    					COUNT(DISTINCT ci.cid) AS interviews,\r\n"
//    		+ "    					SUM(CASE WHEN ci.status = 'Inprogress' THEN 1 ELSE 0 END) AS inprogress,\r\n"
//    		+ "    		           SUM(CASE WHEN ci.status = 'Hold' THEN 1 ELSE 0 END) AS hold,\r\n"
//    		+ "    		           SUM(CASE WHEN ci.status = 'Rejected' THEN 1 ELSE 0 END) AS rejected,\r\n"
//    		+ "    		           SUM(CASE WHEN ci.status = 'Selected' THEN 1 ELSE 0 END) AS selected,\r\n"
//    		+ "                       sum(CASE WHEN ci.sub_status = 'onboarded' THEN 1 ELSE 0 END) AS onboarded,\r\n"
//    		+ "                       sum(CASE WHEN ci.sub_status = 'offered' THEN 1 ELSE 0 END) AS offerred\r\n"
//    		+ "    		          FROM\r\n"
//    		+ "    		               data_rrf rr\r\n"
//    		+ "    		           JOIN\r\n"
//    		+ "    		            data_candidate ci ON rr.id = ci.rrf_id\r\n"
//    		+ "    		           LEFT JOIN\r\n"
//    		+ "    		         data_interviews_schedule dis ON ci.cid = dis.candidate_id\r\n"
//    		+ "    		          WHERE\r\n"
//    		+ "    		    dis.scheduled_on >= scheduled_on\r\n"
//    		+ "    		           AND dis.scheduled_on NOT REGEXP '^[0-9]{4}-[0-9]{2}-W[0-9]{2}$'\r\n"
//    		+ "    		          GROUP BY\r\n"
//    		+ "    				customer, technology", nativeQuery = true)
	@Query(value = "SELECT \r\n" + "    rr.customer_name AS customer,\r\n"
			+ "    SUM(rr.open_positions) AS openedPositions,\r\n"
			+ "    SUM(rr.closed_positions) AS closedPositions,\r\n" + "    rr.primary_skills AS technology,\r\n"
			+ "    COUNT(DISTINCT ci.cid) AS interviews,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Inprogress' THEN 1 ELSE 0 END) AS inprogress,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Hold' THEN 1 ELSE 0 END) AS hold,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Reject' THEN 1 ELSE 0 END) AS rejected,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Selected' THEN 1 ELSE 0 END) AS selected,\r\n"
			+ "    SUM(CASE WHEN ci.sub_status = 'onboarded' THEN 1 ELSE 0 END) AS onboarded,\r\n"
			+ "    SUM(CASE WHEN ci.sub_status = 'offered' THEN 1 ELSE 0 END) AS offered\r\n" + "FROM\r\n"
			+ "    data_rrf rr\r\n" + "JOIN\r\n" + "    data_candidate ci ON rr.id = ci.rrf_id\r\n" + "LEFT JOIN\r\n"
			+ "    data_interviews_schedule dis ON ci.cid = dis.candidate_id\r\n" + "WHERE\r\n"
			+ "    dis.scheduled_on >= scheduled_on\r\n"
			+ "    AND dis.scheduled_on NOT REGEXP '^[0-9]{4}-[0-9]{2}-W[0-9]{2}$'\r\n" + "GROUP BY\r\n"
			+ "    customer, technology", nativeQuery = true)
	List<Object[]> getInterviewDataForOneMonth();

	@Query(value = "select \r\n" + "    		        rr.customer_name AS customer,\r\n"
			+ "    		     rr.open_positions As openedPositions,\r\n"
			+ "    		          rr.closed_positions As closedPositions,\r\n"
			+ "    		            rr.primary_skills AS technology,\r\n"
			+ "    					COUNT(DISTINCT ci.cid) AS interviews,\r\n"
			+ "    					SUM(CASE WHEN ci.status = 'Inprogress' THEN 1 ELSE 0 END) AS inprogress,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Hold' THEN 1 ELSE 0 END) AS hold,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Rejected' THEN 1 ELSE 0 END) AS rejected,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Selected' THEN 1 ELSE 0 END) AS selected,\r\n"
			+ "                       sum(CASE WHEN ci.sub_status = 'onboarded' THEN 1 ELSE 0 END) AS onboarded,\r\n"
			+ "                       sum(CASE WHEN ci.sub_status = 'offered' THEN 1 ELSE 0 END) AS offerred\r\n"
			+ "    		          FROM\r\n" + "    		               data_rrf rr\r\n" + "    		           JOIN\r\n"
			+ "    		            data_candidate ci ON rr.id = ci.rrf_id\r\n" + "    		           LEFT JOIN\r\n"
			+ "    		         data_interviews_schedule dis ON ci.cid = dis.candidate_id\r\n"
			+ "    		          where \r\n" + "    		    dis.scheduled_on >= rr.scheduled_on\r\n"
			+ "    		           AND dis.scheduled_on NOT REGEXP '^[0-9]{4}-[0-9]{2}-W[0-9]{2}$'\r\n"
			+ "                       AND primary_skills = ?1\r\n" + "    		          GROUP BY\r\n"
			+ "    				customer, technology,rr.open_positions,rr.closed_positions", nativeQuery = true)
	List<Object[]> getInterviewsByTechnologyAndDates(@Param("technology") String technology,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query(value = "SELECT\n" + "    rr.customer_name AS customer,\n" + "    rr.primary_skills AS technology,\n"
			+ "    COUNT(DISTINCT ci.cid) AS interviews,\n"
			+ "    SUM(CASE WHEN ci.status = 'Inprogress' THEN 1 ELSE 0 END) AS inprogress,\n"
			+ "    SUM(CASE WHEN ci.status = 'Hold' THEN 1 ELSE 0 END) AS hold,\n"
			+ "    SUM(CASE WHEN ci.status = 'Reject' THEN 1 ELSE 0 END) AS reject,\n"
			+ "    SUM(CASE WHEN ci.status = 'Selected' THEN 1 ELSE 0 END) AS selected\n" + "FROM\n"
			+ "    data_rrf rr\n" + "JOIN\n" + "    data_candidate ci ON rr.id = ci.rrf_id\n" + "LEFT JOIN\n"
			+ "    data_interviews_schedule dis ON ci.cid = dis.candidate_id\n" + "WHERE\n"
			+ "    dis.scheduled_on BETWEEN :startDate AND :endDate\n"
			+ "    AND dis.scheduled_on NOT REGEXP '^[0-9]{4}-[0-9]{2}-W[0-9]{2}$'\n" + "GROUP BY\n"
			+ "    customer, technology", nativeQuery = true)
	List<Object[]> getInterviewData(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query(value = "select \r\n" + "    		        rr.customer_name AS customer,\r\n"
			+ "    		     rr.open_positions As openedPositions,\r\n"
			+ "    		          rr.closed_positions As closedPositions,\r\n"
			+ "    		            rr.primary_skills AS technology,\r\n"
			+ "    					COUNT(DISTINCT ci.cid) AS interviews,\r\n"
			+ "    					SUM(CASE WHEN ci.status = 'Inprogress' THEN 1 ELSE 0 END) AS inprogress,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Hold' THEN 1 ELSE 0 END) AS hold,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Rejected' THEN 1 ELSE 0 END) AS rejected,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Selected' THEN 1 ELSE 0 END) AS selected,\r\n"
			+ "    		           SUM(CASE WHEN ci.status = 'Backout' THEN 1 ELSE 0 END) AS NoShow,\r\n"

			+ "                       sum(CASE WHEN ci.sub_status = 'onboarded' THEN 1 ELSE 0 END) AS onboarded,\r\n"
			+ "                       sum(CASE WHEN ci.sub_status = 'offered' THEN 1 ELSE 0 END) AS offerred\r\n"
			+ "    		          FROM\r\n" + "    		               data_rrf rr\r\n" + "    		           JOIN\r\n"
			+ "    		            data_candidate ci ON rr.id = ci.rrf_id\r\n" + "    		           LEFT JOIN\r\n"
			+ "    		         data_interviews_schedule dis ON ci.cid = dis.candidate_id\r\n"
			+ "    		          where \r\n" + "    		    dis.scheduled_on >= rr.scheduled_on\r\n"
			+ "    		           AND dis.scheduled_on NOT REGEXP '^[0-9]{4}-[0-9]{2}-W[0-9]{2}$'\r\n"
			+ "                       AND rr.customer_name = ?1\r\n" + "    		          GROUP BY\r\n"
			+ "    				customer, technology,rr.open_positions,rr.closed_positions", nativeQuery = true)
	List<Object[]> getInterviewsByCustomerAndDates(@Param("customer") String customer,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query(value = "select distinct customer_name from data_rrf", nativeQuery = true)
	public List<String> getCustomerName();

	@Query(value = "select \r\n" + "    rr.customer_name AS customer,\r\n"
			+ "    rr.open_positions AS openedPositions,\r\n" + "    rr.closed_positions AS closedPositions,\r\n"
			+ "    rr.primary_skills AS technology,\r\n" + "    COUNT(DISTINCT ci.cid) AS interviews,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Inprogress' THEN 1 ELSE 0 END) AS inprogress,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Hold' THEN 1 ELSE 0 END) AS hold,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Rejected' THEN 1 ELSE 0 END) AS rejected,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Selected' THEN 1 ELSE 0 END) AS selected,\r\n"
			+ "    SUM(CASE WHEN ci.status = 'Backout' THEN 1 ELSE 0 END) AS NoShow,\r\n"
			+ "    SUM(CASE WHEN ci.sub_status = 'onboarded' THEN 1 ELSE 0 END) AS onboarded,\r\n"
			+ "    SUM(CASE WHEN ci.sub_status = 'offered' THEN 1 ELSE 0 END) AS offered\r\n" + "FROM\r\n"
			+ "    data_rrf rr\r\n" + "JOIN\r\n" + "    data_candidate ci ON rr.id = ci.rrf_id\r\n" + "GROUP BY\r\n"
			+ "    rr.customer_name, rr.open_positions, rr.closed_positions, rr.primary_skills", nativeQuery = true)
	List<Object[]> getInterviewData(@Param("startDate") LocalDate startDate);

//    @Query(value="",nativeQuery = true)
//	List<Object[]> getInterviewsByStatusAndDates( @Param("status")String status, @Param("startDate") LocalDate startDate,  @Param("endDate") LocalDate endDate);
//	
//	@Query(value="select dr.customer_name, count(*) from  data_candidate dc JOIN data_rrf dr ON dr.id = dc.rrf_id where dc.published_on between '2023-11-01' and '2023-11-15'\r\n"
//	 		+ "group by dr.customer_name;",nativeQuery = true)
//	List<Object[]> getInterviewsByCustomersAndDates(@Param("customer") String customer, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//	

//	@Query(value="select dr.primary_skills, count(*) from  data_candidate dc JOIN data_rrf dr ON dr.id = dc.rrf_id where dc.published_on between '2023-11-01' and '2023-11-15'\r\n"
//	 		+ "group by dr.primary_skills;",nativeQuery = true)
//    List<Object[]> getInterviewsByTechnologiesAndDates(@Param("technology") String technology, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	// learning purpose
//    @Query(value = "select DISTINCT ard.assigned_by as assigned_by,ard.assigned_on as assigned_on, ard.closed_positions as closed_positions, \r\n"
//    		+ "ard.customer_name as customer_name, ard.expirence as expirence, ard.id as id, ard.job_role as job_role, ard.open_positions as open_positions, ard.rrf_id as rrf_id\r\n"
//    		+ "from ohamsdb.assigned_rrf_data ard inner join ohamsdb.data_rrf dr on \r\n"
//    		+ "dr.id = ard.rrf_id  where dr.employee_id = ?1",nativeQuery = true)
//    @Query(value = "select ard.assigned_by,ard.assigned_on,ard.closed_positions,ard.customer_name,ard.expirence,ard.id,\r\n"
//    		+ "ard.job_role,ard.open_positions,ard.rrf_id\r\n"
//    		+ " from ohamsdb.assigned_rrf_data ard join ohamsdb.data_rrf dr on \r\n"
//    		+ "ard.rrf_id = dr.id where dr.employee_id = ?1",nativeQuery = true)

//    @Query(value = "select distinct ard.*\r\n"
//    		+ " from assigned_rrf_data ard join data_rrf dr on \r\n"
//    		+ "ard.rrf_id = dr.id where dr.employee_id = ?1",nativeQuery = true)
	@Query(value = "select dr.requirement_Name,dr.customer_name,dr.priority,dr.owner_of_requirement,dr.created_by,\r\n"
			+ "dr.total_positions,dr.open_positions,dr.closed_positions,dr.published_on from data_rrf \r\n"
			+ "dr where dr.employee_id=?1", nativeQuery = true)
	public List<Map<String, Object>> getrrfData(long employeeId);

	@Query(value = "select * from data_rrf dr where case when ?1 is null then true else "
			+ "dr.customer_name = ?1 end and case when ?2 is null then true else "
			+ "dr.primary_skills = ?2 end and case when ?3 is null then true else "
			+ "dr.hiring_type = ?3 end", nativeQuery = true)
	public List<RRF> getDetails(String customerName, String primarySkills, String hiringType);

//	@Query(value = "SELECT \r\n" + "    dr.requirement_Name,\r\n" + "    dr.customer_name,\r\n" + "    dr.id,\r\n"
//			+ "    dr.priority,\r\n" + "    dr.owner_of_requirement,\r\n" + "    dr.created_by,\r\n"
//			+ "    dr.total_positions,\r\n" + "    dr.open_positions,\r\n" + "    dr.closed_positions,\r\n"
//			+ "    dr.published_on,\r\n" + "    dr.requirement_status,\r\n"
//			+ "    (SELECT COUNT(*) FROM data_candidate WHERE requirement_Name = dr.requirement_Name) AS candidates_count\r\n"
//			+ "FROM \r\n" + "    (\r\n" + "        SELECT \r\n" + "            dr.*, \r\n" + "            CASE \r\n"
//			+ "                WHEN dr.priority='High' AND dr.requirement_status='New' THEN 1\r\n"
//			+ "                WHEN dr.priority='Medium' AND dr.requirement_status='New' THEN 2\r\n"
//			+ "                WHEN dr.priority='Low' AND dr.requirement_status='New' THEN 3\r\n"
//			+ "                WHEN dr.priority='High' AND dr.requirement_status='Inprogress' THEN 4\r\n"
//			+ "                WHEN dr.priority='Medium' AND dr.requirement_status='Inprogress' THEN 5\r\n"
//			+ "                WHEN dr.priority='Low' AND dr.requirement_status='Inprogress' THEN 6\r\n"
//			+ "            END AS priority_order\r\n" + "        FROM \r\n" + "            data_rrf dr\r\n"
//			+ "        WHERE \r\n" + "            dr.visibility = 1\r\n" + "    ) dr\r\n" + "WHERE \r\n"
//			+ "    dr.employee_id = ?1\r\n" + "ORDER BY \r\n" + "    priority_order", nativeQuery = true)
	@Query(value="select * from data_rrf where employee_id=?1 order by id desc",nativeQuery = true)
	List<Map<String, Object>> getAssignedRRFList(long empId);

	@Query(value = "select email,COUNT(*) as total_requirement,sum(total_positions),sum(open_positions),sum(closed_positions) \r\n"
			+ "from data_rrf where visibility=1  group by email ", nativeQuery = true)
	List<Object[]> getAssignedData();

	@Query(value = "select * from data_rrf", nativeQuery = true)
	public List<RRF> getAllRRFDetails();
	
	@Query(value = "SELECT SUM(total_positions), SUM(closed_positions) FROM data_rrf " +
		    "WHERE customer_name = :customer AND published_on >= :startDate AND published_on <= :endDate AND visibility = 1", nativeQuery = true)
		public List<Object[]> getCustomizedCustomerDatatoDonut(@Param("customer") String customer,
		        @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
 
	@Query(value = "select SUM(total_positions),SUM(closed_positions) from data_rrf where \r\n"
			+ "published_on BETWEEN :startDate AND :endDate AND visibility=1", nativeQuery = true)
	public List<Object[]> getAllCutomerDatatoDonut(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	
//	@Query(value="SELECT \r\n"
//			+ "    (SELECT SUM(total_positions) FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS total,\r\n"
//			+ "    (SELECT SUM(closed_positions) FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS closed,\r\n"
//			+ "    (SELECT SUM(open_positions) FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS open,\r\n"
//			+ "    (SELECT SUM(candidate_count) FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS candidate_count,\r\n"
//			+ "    (SELECT SUM(IF(status = 'InProgress', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS Inprogress,\r\n"
//			+ "    (SELECT SUM(IF(status = 'Hold', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS Hold,\r\n"
//			+ "    (SELECT SUM(IF(status = 'Reject', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS Rejected,\r\n"
//			+ "    (SELECT SUM(IF(sub_status = 'Onboarded', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS onboarded_count,\r\n"
//			+ "    (SELECT SUM(IF(sub_status = 'Offered', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS offered_count,\r\n"
//			+ "    (Select Sum(If(interview_round='STAGE_1',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf where published_on between :startDate AND :endDate AND visibility = 1)) as Stage1_count,\r\n"
//			+ "	(select sum(if(interview_round='STAGE_2',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf where published_on between :startDate AND :endDate AND visibility = 1)) as Stage2_count,\r\n"
//			+ "    (select sum(if(interview_round='STAGE_3',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf where published_on between :startDate AND :endDate AND visibility = 1)) as Stage3_count,\r\n"
//			+ "    (select sum(if(interview_round='MANAGER',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf where published_on between :startDate AND :endDate AND visibility = 1)) as Manager_count,\r\n"
//			+ "    (select sum(if(interview_round='CLIENT',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf where published_on between :startDate AND :endDate AND visibility = 1)) as Client_count,\r\n"
//			+ "    (select sum(if(interview_round='HR',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf where published_on between :startDate AND :endDate AND visibility = 1)) as HR_Count;",nativeQuery = true)
//	public List<Object[]> getAllCutomerData(@Param("startDate") LocalDate startDate,
//			@Param("endDate") LocalDate endDate);
	
	
	@Query(value = "WITH PositionSummary AS (\r\n"
			+ "    SELECT \r\n"
			+ "        COALESCE(SUM(closed_positions), 0) AS closed_positions,\r\n"
			+ "        COALESCE(SUM(open_positions), 0) AS open_positions,\r\n"
			+ "        COALESCE(SUM(total_positions), 0) AS total_positions\r\n"
			+ "    FROM\r\n"
			+ "        data_rrf\r\n"
			+ "    WHERE\r\n"
			+ "        published_on BETWEEN :startDate AND :endDate \r\n"
			+ "        AND visibility = 1 \r\n"
			+ "        AND requirement_status = 'Active'\r\n"
			+ "),\r\n"
			+ "StageSummary AS (\r\n"
			+ "    SELECT \r\n"
			+ "        CASE\r\n"
			+ "            WHEN dc.sub_status IN ('STAGE_1_REJECTED', 'STAGE_2_REJECTED', 'STAGE_3_REJECTED', 'CLIENT_REJECTED', 'HR_REJECTED') THEN 'REJECTED'\r\n"
			+ "            WHEN dc.sub_status LIKE 'STAGE_1%' OR dc.sub_status = 'STAGE_3_SELECTED' THEN 'STAGE_1'\r\n"
			+ "            WHEN dc.sub_status LIKE 'STAGE_2%' THEN 'STAGE_2'\r\n"
			+ "            WHEN dc.sub_status LIKE 'STAGE_3%' THEN 'STAGE_3'\r\n"
			+ "            WHEN dc.sub_status = 'client' OR dc.sub_status = 'client_SELECTED' THEN 'Client'\r\n"
			+ "            WHEN dc.sub_status = 'client hold%' OR dc.sub_status = 'client_HOld' THEN 'Client_Hold'\r\n"

			+ "            WHEN dc.sub_status LIKE '%REJECTED%' THEN 'REJECTED'\r\n"
			+ "            ELSE dc.sub_status\r\n"
			+ "        END AS stage,\r\n"
			+ "        COUNT(*) AS count,\r\n"
			+ "        (SELECT COUNT(cid) \r\n"
			+ "         FROM data_candidate dc \r\n"
			+ "         INNER JOIN data_rrf dr ON dc.rrf_id = dr.id \r\n"
			+ "         WHERE dr.visibility = 1 \r\n"
			+ "         AND dr.requirement_status = 'Active' \r\n"
			+ "         AND dr.published_on BETWEEN :startDate AND :endDate) AS candidateCount\r\n"
			+ "    FROM\r\n"
			+ "        data_candidate dc\r\n"
			+ "    INNER JOIN data_rrf dr ON dr.id = dc.rrf_id\r\n"
			+ "    WHERE\r\n"
			+ "        dr.published_on BETWEEN :startDate AND :endDate \r\n"
			+ "        AND dr.visibility = 1 \r\n"
			+ "        AND dr.requirement_status = 'Active'\r\n"
			+ "    GROUP BY stage\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "    'closed_positions' AS num,\r\n"
			+ "    closed_positions AS count,\r\n"
			+ "    0 AS candidateCount,\r\n"
			+ "    0 AS percentage\r\n"
			+ "FROM PositionSummary\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT \r\n"
			+ "    'open_positions' AS num,\r\n"
			+ "    open_positions AS count,\r\n"
			+ "    0 AS candidateCount,\r\n"
			+ "    0 AS percentage\r\n"
			+ "FROM PositionSummary\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT \r\n"
			+ "    'total_positions' AS num,\r\n"
			+ "    total_positions AS count,\r\n"
			+ "    0 AS candidateCount,\r\n"
			+ "    0 AS percentage\r\n"
			+ "FROM PositionSummary\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT \r\n"
			+ "    stage AS num,\r\n"
			+ "    count,\r\n"
			+ "    candidateCount,\r\n"
			+ "    CASE WHEN candidateCount > 0 THEN (count * 100.0) / candidateCount ELSE 0 END AS percentage\r\n"
			+ "FROM StageSummary\r\n"
			+ "ORDER BY percentage DESC\r\n"
			+ "", nativeQuery = true)


	public List<Object[]> getAllCutomerData(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);	
//	@Query(value = "SELECT \r\n"
//			+ "    (SELECT SUM(total_positions) FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS total_positions,\r\n"
//			+ "    (SELECT SUM(closed_positions) FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS closed_positions,\r\n"
//			+ "    (SELECT SUM(open_positions) FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS open_positions,\r\n"
//			+ "    (SELECT SUM(candidate_count) FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1) AS candidate_count,\r\n"
//			+ "    (SELECT SUM(IF(status = 'InProgress', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS Inprogress,\r\n"
//			+ "    (SELECT SUM(IF(status = 'Hold', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS Hold,\r\n"
//			+ "    (SELECT SUM(IF(status = 'Reject', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS Rejected,\r\n"
//			+ "    (SELECT SUM(IF(sub_status = 'Onboarded', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS onboarded_count,\r\n"
//			+ "    (SELECT SUM(IF(sub_status = 'Offered', 1, 0)) FROM data_candidate WHERE rrf_id IN (SELECT id FROM data_rrf WHERE customer_name = :customer AND published_on BETWEEN :startDate AND :endDate AND visibility = 1)) AS offered_count,\r\n"
//			+ "    (Select Sum(If(interview_round='STAGE_1',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf WHERE customer_name = :customer AND published_on between :startDate AND :endDate AND visibility = 1)) as Stage1_count,\r\n"
//			+ "	(select sum(if(interview_round='STAGE_2',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf WHERE customer_name = :customer AND published_on between :startDate AND :endDate AND visibility = 1)) as Stage2_count,\r\n"
//			+ "    (select sum(if(interview_round='STAGE_3',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf WHERE customer_name = :customer AND published_on between :startDate AND :endDate AND visibility = 1)) as Stage3_count,\r\n"
//			+ "    (select sum(if(interview_round='MANAGER',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf WHERE customer_name = :customer AND published_on between :startDate AND :endDate AND visibility = 1)) as Manager_count,\r\n"
//			+ "    (select sum(if(interview_round='CLIENT',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf WHERE customer_name = :customer AND published_on between :startDate AND :endDate AND visibility = 1)) as Client_count,\r\n"
//			+ "    (select sum(if(interview_round='HR',1,0)) from data_interviews_schedule where rrf_id in (select id from data_rrf WHERE customer_name = :customer AND published_on between :startDate AND :endDate AND visibility = 1)) as HR_Count;",nativeQuery = true)
//	public List<Object[]> getCustomizedCustomerData(@Param("customer") String customer,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//	
	
	/*
	 * @Query(value = "SELECT \r\n" + "    'closed_positions' AS num,\r\n" +
	 * "    subquery1.closed_positions AS count\r\n" + "FROM\r\n" +
	 * "    (SELECT \r\n" + "        SUM(closed_positions) AS closed_positions,\r\n"
	 * + "            SUM(open_positions) AS open_positions,\r\n" +
	 * "            SUM(total_positions) AS total_positions\r\n" + "    FROM\r\n" +
	 * "        data_rrf\r\n" + "    WHERE\r\n" +
	 * "        customer_name = :customer\r\n" +
	 * "            AND published_on BETWEEN :startDate AND :endDate  and visibility=1) subquery1 \r\n"
	 * + "UNION ALL SELECT \r\n" +
	 * "    'open_positions' AS num, subquery1.open_positions AS count\r\n" +
	 * "FROM\r\n" + "    (SELECT \r\n" +
	 * "        SUM(closed_positions) AS closed_positions,\r\n" +
	 * "            SUM(open_positions) AS open_positions,\r\n" +
	 * "            SUM(total_positions) AS total_positions\r\n" + "    FROM\r\n" +
	 * "        data_rrf\r\n" + "    WHERE\r\n" +
	 * "        customer_name = :customer\r\n" +
	 * "            AND published_on BETWEEN :startDate AND :endDate  and visibility=1) subquery1 \r\n"
	 * + "UNION ALL SELECT \r\n" +
	 * "    'total_positions' AS num, subquery1.total_positions AS count\r\n" +
	 * "FROM\r\n" + "    (SELECT \r\n" +
	 * "        SUM(closed_positions) AS closed_positions,\r\n" +
	 * "            SUM(open_positions) AS open_positions,\r\n" +
	 * "            SUM(total_positions) AS total_positions\r\n" + "    FROM\r\n" +
	 * "        data_rrf\r\n" + "    WHERE\r\n" +
	 * "        customer_name = :customer\r\n" +
	 * "            AND published_on BETWEEN :startDate AND :endDate  and visibility=1) subquery1 \r\n"
	 * + "UNION ALL SELECT \r\n" + "    num, COUNT(num) AS count\r\n" + "FROM\r\n" +
	 * "    (SELECT \r\n" + "        CASE\r\n" +
	 * "                WHEN sub_status LIKE 'STAGE_1%' THEN 'STAGE_1'\r\n" +
	 * "                WHEN sub_status LIKE 'STAGE_2%' THEN 'STAGE_2'\r\n" +
	 * "                WHEN sub_status LIKE 'STAGE_3%' THEN 'STAGE_3'\r\n" +
	 * "                ELSE sub_status\r\n" + "            END AS num,\r\n" +
	 * "            sub_status,\r\n" + "            rrf_id\r\n" + "    FROM\r\n" +
	 * "        data_candidate) dc\r\n" + "        INNER JOIN\r\n" +
	 * "    data_rrf dr ON dr.id = dc.rrf_id\r\n" + "WHERE\r\n" +
	 * "    dr.customer_name = :customer\r\n" +
	 * "        AND dr.published_on BETWEEN :startDate AND :endDate  and visibility=1 \r\n"
	 * + "GROUP BY num;",nativeQuery = true)
	 */
	
	/*
	 * @Query(value="WITH PositionSummary AS (\r\n" + "    SELECT \r\n" +
	 * "        SUM(closed_positions) AS closed_positions,\r\n" +
	 * "        SUM(open_positions) AS open_positions,\r\n" +
	 * "        SUM(total_positions) AS total_positions\r\n" + "    FROM\r\n" +
	 * "        data_rrf\r\n" + "    WHERE\r\n" +
	 * "        customer_name = :customer\r\n" +
	 * "        AND published_on BETWEEN :startDate AND :endDate\r\n" +
	 * "        AND visibility = 1\r\n" + "),\r\n" + "StageSummary AS (\r\n" +
	 * "    SELECT \r\n" + "        CASE\r\n" +
	 * "            WHEN dc.sub_status LIKE 'STAGE_1%' THEN 'STAGE_1'\r\n" +
	 * "            WHEN dc.sub_status LIKE 'STAGE_2%' THEN 'STAGE_2'\r\n" +
	 * "            WHEN dc.sub_status LIKE 'STAGE_3%' THEN 'STAGE_3'\r\n" +
	 * "            ELSE dc.sub_status\r\n" + "        END AS stage,\r\n" +
	 * "        COUNT(*) AS count\r\n" + "    FROM\r\n" +
	 * "        data_candidate dc\r\n" +
	 * "    INNER JOIN data_rrf dr ON dr.id = dc.rrf_id\r\n" + "    WHERE\r\n" +
	 * "        dr.customer_name = :customer\r\n" +
	 * "        AND dr.published_on BETWEEN :startDate AND :endDate\r\n" +
	 * "        AND dr.visibility = 1\r\n" + "    GROUP BY stage\r\n" + ")\r\n" +
	 * "\r\n" + "SELECT \r\n" + "    'closed_positions' AS num,\r\n" +
	 * "    closed_positions AS count\r\n" + "FROM PositionSummary\r\n" + "\r\n" +
	 * "UNION ALL\r\n" + "\r\n" + "SELECT \r\n" + "    'open_positions' AS num,\r\n"
	 * + "    open_positions AS count\r\n" + "FROM PositionSummary\r\n" + "\r\n" +
	 * "UNION ALL\r\n" + "\r\n" + "SELECT \r\n" +
	 * "    'total_positions' AS num,\r\n" + "    total_positions AS count\r\n" +
	 * "FROM PositionSummary\r\n" + "\r\n" + "UNION ALL\r\n" + "\r\n" +
	 * "SELECT \r\n" + "    stage AS num,\r\n" + "    count\r\n" +
	 * "FROM StageSummary;\r\n" + "",nativeQuery = true)
	 */
	
	/*
	 * @Query(value="	WITH PositionSummary AS (\r\n" + "\r\n" + "    SELECT \r\n"
	 * + "\r\n" + "        SUM(closed_positions) AS closed_positions,\r\n" + "\r\n"
	 * + "        SUM(open_positions) AS open_positions,\r\n" + "\r\n" +
	 * "        SUM(total_positions) AS total_positions\r\n" + "\r\n" +
	 * "    FROM\r\n" + "\r\n" + "        data_rrf\r\n" + "\r\n" + "    WHERE\r\n" +
	 * "\r\n" + "        customer_name = :customer\r\n" + "\r\n" +
	 * "        AND published_on BETWEEN :startDate AND :endDate\r\n" + "\r\n" +
	 * "        AND visibility = 1 and requirement_status = 'Active'\r\n" + "\r\n" +
	 * "),\r\n" + "\r\n" + "StageSummary AS (\r\n" + "\r\n" + "    SELECT \r\n" +
	 * "\r\n" + "        CASE\r\n" + "\r\n" +
	 * "            WHEN dc.sub_status LIKE 'STAGE_1%' THEN 'STAGE_1'\r\n" + "\r\n"
	 * + "            WHEN dc.sub_status LIKE 'STAGE_2%' THEN 'STAGE_2'\r\n" +
	 * "\r\n" + "            WHEN dc.sub_status LIKE 'STAGE_3%' THEN 'STAGE_3'\r\n"
	 * + "\r\n" + "            ELSE dc.sub_status\r\n" + "\r\n" +
	 * "        END AS stage,\r\n" + "\r\n" + "        COUNT(*) AS count,\r\n" +
	 * "\r\n" +
	 * "        (select count(cid) from data_candidate dc inner join data_rrf dr on dc.rrf_id "
	 * +
	 * "= dr.id where dr.visibility = 1 and dr.requirement_status = 'Active') candidateCount,\r\n"
	 * + "\r\n" + "        0 percentage\r\n" + "\r\n" + "    FROM\r\n" + "\r\n" +
	 * "        data_candidate dc\r\n" + "\r\n" +
	 * "    INNER JOIN data_rrf dr ON dr.id = dc.rrf_id\r\n" + "\r\n" +
	 * "    WHERE\r\n" + "\r\n" + "        customer_name = :customer\r\n" + "\r\n" +
	 * "        AND dr.published_on BETWEEN :startDate AND :endDate\r\n" + "\r\n" +
	 * "        AND dr.visibility = 1 and dr.requirement_status = 'Active'\r\n" +
	 * "\r\n" + "    GROUP BY stage\r\n" + "\r\n" + ")\r\n" + "\r\n" + "SELECT \r\n"
	 * + "\r\n" + "    'closed_positions' AS num,\r\n" + "\r\n" +
	 * "    closed_positions AS count,\r\n" + "\r\n" +
	 * "        0 candidateCount,\r\n" + "\r\n" + "        0 percentage\r\n" +
	 * "\r\n" + "FROM PositionSummary\r\n" + "\r\n" + "UNION ALL\r\n" + "\r\n" +
	 * "SELECT \r\n" + "\r\n" + "    'open_positions' AS num,\r\n" + "\r\n" +
	 * "    open_positions AS count,\r\n" + "\r\n" + "        0 candidateCount,\r\n"
	 * + "\r\n" + "        0 percentage\r\n" + "\r\n" + "FROM PositionSummary\r\n" +
	 * "\r\n" + "UNION ALL\r\n" + "\r\n" + "SELECT \r\n" + "\r\n" +
	 * "    'total_positions' AS num,\r\n" + "\r\n" +
	 * "    total_positions AS count,\r\n" + "\r\n" +
	 * "        0 candidateCount,\r\n" + "\r\n" + "        0 percentage\r\n" +
	 * "\r\n" + "FROM PositionSummary\r\n" + "\r\n" + "UNION ALL\r\n" + "\r\n" +
	 * "SELECT \r\n" + "\r\n" + "    stage AS num,\r\n" + "\r\n" + "    count,\r\n"
	 * + "\r\n" + "    candidateCount,\r\n" + "\r\n" +
	 * "    (count/candidateCount)*100 as percentage\r\n" + "\r\n" +
	 * "FROM StageSummary order by 4 desc;\r\n" + " ",nativeQuery = true)
	 * 
	 * 
	 */
	@Query(value = "WITH PositionSummary AS (\r\n"
	        + "    SELECT \r\n"
	        + "        SUM(closed_positions) AS closed_positions,\r\n"
	        + "        SUM(open_positions) AS open_positions,\r\n"
	        + "        SUM(total_positions) AS total_positions\r\n"
	        + "    FROM\r\n"
	        + "        data_rrf\r\n"
	        + "    WHERE\r\n"
	        + "        customer_name = :customer\r\n"
	        + "        AND published_on BETWEEN :startDate AND :endDate \r\n"
	        + "        AND visibility = 1 AND requirement_status = 'Active'\r\n"
	        + "),\r\n"
	        + "StageSummary AS (\r\n"
	        + "    SELECT \r\n"
	        + "        CASE\r\n"
	        + "            WHEN dc.sub_status = 'STAGE_1_REJECTED' OR dc.sub_status = 'STAGE_2_REJECTED' OR dc.sub_status = 'STAGE_3_REJECTED' OR dc.sub_status = 'CLIENT_REJECTED' OR dc.sub_status = 'HR_REJECTED' THEN 'REJECTED'\r\n"
	        + "            WHEN dc.sub_status LIKE 'STAGE_1%' OR dc.sub_status = 'STAGE_3_SELECTED' THEN 'STAGE_1'\r\n"
	        + "            WHEN dc.sub_status LIKE 'STAGE_2%' THEN 'STAGE_2'\r\n"
	        + "            WHEN dc.sub_status LIKE 'STAGE_3%' THEN 'STAGE_3'\r\n"
	        + "            WHEN dc.sub_status = 'client' OR dc.sub_status = 'client_SELECTED' THEN 'Client'\r\n"
			+ "            WHEN dc.sub_status = 'client hold%' OR dc.sub_status = 'client_HOld' THEN 'Client_Hold'\r\n"

			+ "            WHEN dc.sub_status LIKE '%REJECTED%' THEN 'REJECTED'\r\n"
			
//	        + "            WHEN dc.sub_status = 'Client_SCHEDULED' THEN 'Client_SCHEDULED'\r\n"
//	        + "            WHEN dc.sub_status = 'NOShow' THEN 'NOShow'\r\n"
	        + "            WHEN dc.sub_status LIKE '%REJECTED%' THEN 'REJECTED'\r\n"
	        + "            ELSE dc.sub_status\r\n"
	        + "        END AS stage,\r\n"
	        + "        COUNT(*) AS count,\r\n"
	        + "        (SELECT COUNT(cid) \r\n"
	        + "         FROM data_candidate dc \r\n"
	        + "         INNER JOIN data_rrf dr ON dc.rrf_id = dr.id \r\n"
	        + "         WHERE dr.visibility = 1 AND dr.requirement_status = 'Active' \r\n"
	        + "         AND dr.customer_name = :customer\r\n"
	        + "         AND dr.published_on BETWEEN :startDate AND :endDate) AS candidateCount,\r\n"
	        + "        0 AS percentage\r\n"
	        + "    FROM\r\n"
	        + "        data_candidate dc\r\n"
	        + "    INNER JOIN data_rrf dr ON dr.id = dc.rrf_id\r\n"
	        + "    WHERE\r\n"
	        + "        dr.customer_name = :customer\r\n"
	        + "        AND dr.published_on BETWEEN :startDate AND :endDate \r\n"
	        + "        AND dr.visibility = 1 AND dr.requirement_status = 'Active'\r\n"
	        + "    GROUP BY stage\r\n"
	        + ")\r\n"
	        + "SELECT \r\n"
	        + "    'closed_positions' AS num,\r\n"
	        + "    closed_positions AS count,\r\n"
	        + "    0 AS candidateCount,\r\n"
	        + "    0 AS percentage\r\n"
	        + "FROM PositionSummary\r\n"
	        + "UNION ALL\r\n"
	        + "SELECT \r\n"
	        + "    'open_positions' AS num,\r\n"
	        + "    open_positions AS count,\r\n"
	        + "    0 AS candidateCount,\r\n"
	        + "    0 AS percentage\r\n"
	        + "FROM PositionSummary\r\n"
	        + "UNION ALL\r\n"
	        + "SELECT \r\n"
	        + "    'total_positions' AS num,\r\n"
	        + "    total_positions AS count,\r\n"
	        + "    0 AS candidateCount,\r\n"
	        + "    0 AS percentage\r\n"
	        + "FROM PositionSummary\r\n"
	        + "UNION ALL\r\n"
	        + "SELECT \r\n"
	        + "    stage AS num,\r\n"
	        + "    count,\r\n"
	        + "    candidateCount,\r\n"
	        + "    (count/candidateCount)*100 AS percentage\r\n"
	        + "FROM StageSummary\r\n"
	        + "ORDER BY percentage DESC;\r\n", nativeQuery = true)

	public List<Object[]> getCustomizedCustomerData(@Param("customer") String customer,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	
	@Query(value = "SELECT * FROM data_rrf where published_on between :startDate and :endDate",nativeQuery = true)
	public List<RRF> getRRFDetailsByCustomDate(@Param ("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endlDate);
	

	@Query(value = "SELECT * from data_rrf ",nativeQuery = true)
	public List<RRF> downloadRRFData();

	@Query(value="select sum(total_positions),sum(open_positions),sum(closed_positions) from data_rrf where owner_of_requirement = :ownerOfRequirement AND published_on BETWEEN :startDate AND :endDate  AND visibility=1"
			,nativeQuery = true)
	public List<Object[]> getBDMDetails(@Param("ownerOfRequirement") String ownerOfRequirement,@Param ("startDate") LocalDate startDate,@Param("endDate") LocalDate endlDate);
	
	
	@Query(value="select * from data_rrf where owner_of_requirement = :ownerOfRequirement AND published_on BETWEEN :startDate AND :endDate  AND visibility=1"
			,nativeQuery = true)
	public List<Object[]> getBDMDetailsInfo(@Param("ownerOfRequirement") String ownerOfRequirement,@Param ("startDate") LocalDate startDate,@Param("endDate") LocalDate endlDate);
	
	
	@Query(value="select * from data_rrf where published_on BETWEEN :startDate AND :endDate  AND visibility=1"
			,nativeQuery = true)
	public List<Object[]> getBDMDetailsInfo(@Param ("startDate") LocalDate startDate,@Param("endDate") LocalDate endlDate);
	
	/*
	 * @Query(
	 * value="SELECT * FROM data_rrf where owner_of_requirement=:ownerOfRequirement AND visibility=1 and requirement_status='Active'"
	 * ,nativeQuery = true) public List<RRF>
	 * getRRFBDMSpecificInfo(@Param("ownerOfRequirement") String
	 * ownerOfRequirement);
	 */
	@Query(value="select sum(total_positions),sum(open_positions),sum(closed_positions) from data_rrf where published_on BETWEEN :startDate AND :endDate AND visibility=1;\r\n"
			,nativeQuery = true)
	public List<Object[]> getAllBDMDetails(@Param ("startDate") LocalDate startDate,@Param("endDate") LocalDate endlDate);
	
	/*
	 * @Query(
	 * value="select owner_of_requirement from data_rrf where visibility=1 and requirement_status='Active'"
	 * ,nativeQuery = true) public List<String> getOwnersdata();
	 */
	
	@Query(value = "SELECT employee_id,emp_name,domain,experience FROM users u WHERE experience > :minExperience AND experience <= :maxExperience AND domain = :technology AND is_enabled = 1 AND role_id IN (194, 195)", nativeQuery = true)
	public List<Object[]> getRMGDetails(@Param("minExperience") int minExp, @Param("maxExperience") int maxExp,@Param("technology") String technology);
	
	
	@Query(value="select employee_id,email,phone_number from ots.users where emp_name=?1",nativeQuery = true)
	public Object[] getuserData(String emp_name);
	
	@Query(value = "select employee_id,email,phone_number,emp_name,experience from users where employee_id in (:ids)",nativeQuery = true)
	public List<Object[]> getUsersByemps(@Param("ids") List<Integer>employeeIds);

	@Query(value = "SELECT employee_id,emp_name,domain,experience FROM users u WHERE domain = :technology AND is_enabled = 1 AND role_id IN (194, 195) and  employee_id  not in "
			+ "(SELECT employee_id FROM users u WHERE experience > :minExperience AND experience <= :maxExperience AND domain = :technology AND is_enabled = 1 AND role_id IN (194, 195))", nativeQuery = true)
	public List<Object[]> getRMGDetailsForShowMore(@Param("minExperience") int minExp, @Param("maxExperience") int maxExp,@Param("technology") String technology);
	 

	
}

