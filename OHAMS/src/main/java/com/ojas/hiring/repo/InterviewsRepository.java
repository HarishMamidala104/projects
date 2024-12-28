package com.ojas.hiring.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.InterviewFeedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.entity.User;

@Repository
public interface InterviewsRepository extends JpaRepository<Interviews, Long> {
    //List<Interviews> findAllInterviewsWithCandidates();

//	@Query(value = "select data_candidate from data_candidate dc inner join data_interviews_schedule dis on dc.cid = dis.cid where dis.id = ?1", nativeQuery = true)
//	@Query(value = "Select dc from Candidate dc INNER JOIN Interviews dis \r\n"
//			+ "on dc.cid = dis.cid \r\n"
//			+ "where dis.id = ?1")
//	public Candidate getCandidateDataByInyerviewId(long id);

//	List<Interviews> findByCidOrderByScheduledOnDesc(long cid);

//    @Query("SELECT i FROM Interviews i JOIN FETCH i.candidate")
//    List<Interviews> findAllInterviewsWithCandidates();

    //@Query("SELECT i FROM Interviews i LEFT JOIN FETCH i.candidate")


	String s="select c.cid,c.full_name,c.current_location,c.currently_working_as,c.currently_working_at,c.email_id, c.rrf_id, i.id,i.interviewer_name,f.status,c.status as candidate_status ,c.sub_status , dr.id as rrfId , dr.hiring_type, dr.primary_skills, dr.customer_name from data_interviews_schedule i inner join data_candidate c on i.cid=c.cid inner join data_interviews_feedback f on f.interview_id=i.id inner join data_rrf dr on dr.id = c.rrf_id";

    //String s="select c.cid,c.email,i.interview_on,i.interviewRound, i.interviewerName,interviewType,i.candidateNotification,i.interviewerNotification,i.scheduledOn, i.interviewStatus, i.interviewStatusComment, i.publishedOn, i.interviewFeedback from data_interviews_schedule i inner join data_candidate c on i.id=c.cid ";
   // String s="select c.cid,c.email_id, c.rrf_id, i.id,i.interviewer_name from data_interviews_schedule i inner join data_candidate c on i.cid=c.cid ";
    @Query(value=s,nativeQuery = true)
    //List<Interviews> findAllInterviewsWithCandidates();
    List<Object[]> findAllInterviewsWithCandidates();
    @Query(value="select * from candidate where cid=?1",nativeQuery = true)
    public Candidate findByCId(Long cid);


	//List<Interviews> findByCidOrderByScheduledOnDesc(long cid);
//
//	@Modifying
//	@Transactional
//	@Query(value = "update data_interviews_schedule set interview_status=?1 where id=?2", nativeQuery = true)
//	public void updateInterviewStatus(String overallFeedback, long interviewId);
    
    
    @Query(value = "Select intv from Interviews intv where intv.id=?1")
	public Interviews getInterviewByFeedBackId(Long interview_id);

    
	@Query(value = "Select * from data_interviews_schedule where interview_status !='Rejected'",nativeQuery = true)
	public List<Interviews> getInterviewByFeedBackData();
	
	@Query(value = "select * from data_interviews_schedule where id = ?1 ORDER BY id DESC ",nativeQuery = true)
	public Interviews getById(long id);
	
	@Query(value = "select * from data_interviews_schedule where employee_id=?1 order by id desc",nativeQuery = true)
	public List<Interviews> getByEmployeeId(long employeeId);
	
//	@Query(value = "select * from data_interviews_schedule where cid = ?1",nativeQuery = true)
//	public Optional<Interviews> getByCid(long Cid);
	@Query(value = "select * from data_interviews_schedule where id = ?1",nativeQuery = true)
	public Optional<Interviews> getByInterviewId(long candidate_id);

	 @Query(value="select * from data_interviews_schedule where interviewer_gmail=?1",nativeQuery = true)
	 List<Interviews> getByinterviewerGmail(String interviewerGmail);

	 @Query(value="select i.* from data_interviews_schedule i LEFT JOIN data_candidate c ON i.candidate_id = c.cid WHERE c.visibility = 1 order by id desc",nativeQuery = true)
	 List<Interviews> getAllInterviewData();
	 
	 
	 @Query(value="SELECT dis.id,dis.candidate_id,dis.interviewer_name,dis.interview_type,dis.scheduled_on,dis.interview_on,dis.interview_round,dis.interview_status,dif.overall_rating,dif.overall_feedback FROM data_interviews_schedule dis left join data_interviews_feedback dif on dis.id=dif.interview_id where dis.candidate_id=:id",nativeQuery = true)
	 List<Object[]> getAllInterviewData(@Param("id") long candidate_id);
}
