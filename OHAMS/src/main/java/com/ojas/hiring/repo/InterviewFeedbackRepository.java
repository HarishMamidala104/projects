package com.ojas.hiring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.InterviewFeedback;
import com.ojas.hiring.entity.Interviews;

@Repository
public interface InterviewFeedbackRepository extends JpaRepository<InterviewFeedback, Long> {

//	@Query(value="select * from data_interviews_feedback where interview_id=?1",nativeQuery = true)
//	public InterviewFeedback getByInterviewId(long interviewId);
	
	
	@Query(value="select * from data_interviews_feedback AS UL1 where UL1.interview_id=?1 and UL1.id IN \r\n"
			+ "(SELECT MAX(UL.id) FROM data_interviews_feedback AS UL GROUP BY UL.interview_id)", nativeQuery = true)
	public InterviewFeedback getByInterviewId(long interviewId);	
	
	@Query(value="select * from data_interviews_feedback where interview_id=?1",nativeQuery = true)
	public List<InterviewFeedback> getByInyerviewId(long id);
	
	@Query(value="select * from data_interviews_feedback AS UL1 where UL1.id IN (SELECT MAX(UL.id) "
			+ "FROM data_interviews_feedback AS UL GROUP BY UL.interview_id)", nativeQuery = true)
	public List<InterviewFeedback> getInterviewFeedBack();
}
