package com.ojas.hiring.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.ojas.hiring.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.exceptions.NoRecordFoundException;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.InterviewFeedbackRepository;
import com.ojas.hiring.repo.InterviewsRepository;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.service.InterviewService;

@Service
public class InterviewsServiceImpl implements InterviewService {

    @Autowired
    private InterviewsRepository interviewsRepo;
    @Autowired
    private InterviewFeedbackRepository interviewFeeedBackRepo;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    CandidateRepo candidateRepo;

    @Autowired
    RRFRepo rrfRepo;

	@Override
	public Interviews createInterview(Interviews interview) {
//		
//		List<Candidate> findById = candidateRepo.findById(interview.getCandidate().getCid());
//		Optional<Candidate> findById = candidateRepo.findById(interview.getCid());
//		if(!findById.isPresent()) {
//			throw new NoRecordFoundException("No Record is Existed with the candateId :" + interview.getCid());
//		}
//		Candidate candidate = findById.get();
//		interview.setCandidates(candidate);
		
//		interview.setCandidates(candidate);
//		interview.setHiringType(candidate.getRrf().getHiringType());
//		interview.setInterviewStatus(InterviewStage.fromString(interview.getInterviewRound()));
		return interviewsRepo.save(interview);
	}

	@Override
	public List<Interviews> getAllInterviews() {

		return interviewsRepo.getAllInterviewData();
	}

	@Override
	public Interviews getInterviewById(Long id) {
		Optional<Interviews> optionalInterview = interviewsRepo.findById(id);

		return optionalInterview.get();
	}

	@Override
	public List<Interviews> getInterviewsByCandidateId(Long id) {
//		return interviewsRepo.findByCidOrderByScheduledOnDesc(id);
		return null;
	}

	@Override
	public Interviews updateInterview(Interviews interview) {
		Interviews existInterview = interviewsRepo.findById(interview.getId()).get();
//		existInterview.setCa(interview.getCandidate().getCid());
		existInterview.setInterviewOn(interview.getInterviewOn());

		existInterview.setInterviewRound(interview.getInterviewRound());
		existInterview.setInterviewerName(interview.getInterviewerName());
		existInterview.setInterviewType(interview.getInterviewType());

		existInterview.setCandidateNotification(interview.getCandidateNotification());
		existInterview.setInterviewerNotification(interview.getCandidateNotification());

		existInterview.setInterviewStatus(interview.getInterviewStatus());
		existInterview.setInterviewStatusComment(interview.getInterviewStatusComment());

		existInterview.setInterviewerGmail(interview.getInterviewerGmail());
		existInterview.setScheduledOn(interview.getScheduledOn());
		existInterview.setTeamsLink(interview.getTeamsLink());

//		existInterview.setPublishedOn(date);
		Interviews updatedInterview = interviewsRepo.save(existInterview);
		return updatedInterview;
	}

	@Override
	public void deleteInterview(Long id) {

		interviewsRepo.deleteById(id);
	}

	public String getAllInterviewsAndInterviewScedule() {
		String cols = "i.id,i.candidate_notification,i.cid,i.interview_on,i.interview_round,i.interview_status,i.interview_status_comment,i.interview_type,i.interviewer_name,i.interviewer_notification,i.published_on,i.scheduled_on";
		cols += ",(select fb.id from data_interviews_feedback fb where fb.int_id=i.id) as curRound,";
//		String query = "";
//		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
//		String data[] = new String[valuePairs.size()];
//		for (int j = 0; j < data.length; j++) {
//			data[j] = "";
//		}
//
//		for (int j = 0; j < t_columns.size(); j++) {
//			int count = 0;
//			for (Object[] dat : valuePairs) {
//				String str = "";
//				System.out.println(t_columnDataTypes.get(j) + "----------------------" + dat[j]);
//				if (t_columnDataTypes.get(j).startsWith("varchar")) {
//					str = "" + dat[j];
//				}
//				if (t_columnDataTypes.get(j).contains("text")) {
//					str = "" + dat[j];
//				}
//				if (t_columnDataTypes.get(j).startsWith("bigint")) {
//					str = "" + dat[j];
//				}
//				if (t_columnDataTypes.get(j).startsWith("int")) {
//					str = "" + dat[j];
//				}
//				if (t_columnDataTypes.get(j).startsWith("smallint")) {
//					str = "" + dat[j];
//				}
//				if (t_columnDataTypes.get(j).startsWith("date")) {
//					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//					try {
//						Date date = formatter.parse("" + (dat[j]));
//						str = (formatter.format(date));
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				if (t_columnDataTypes.get(j).startsWith("datetime")) {
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					try {
//						Date date = sdf.parse("" + (dat[j]));
//						str = date.toString();
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				if (t_columnDataTypes.get(j).startsWith("timestamp")) {
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					try {
//						Date date = sdf.parse("" + (dat[j]));
//						str = date.toString();
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				if (t_columnDataTypes.get(j).startsWith("double")) {
//					str = "" + dat[j];
//				}
//				// str = t_columns.get(j)+":"+t_columnDataTypes.get(j)+":"+str;
//				str = "\"" + columnNames[j] + "\":\"" + str + "\",";
//				data[count] += str;
//				count++;
//			}
//			// String str = (String) valuePairs.get(j);
//			// System.out.println(str);
//		}
//		String res = "[";
//		for (int j = 0; j < data.length; j++) {
//			data[j] = data[j].substring(0, data[j].length() - 1);
//			if ((j + 1) == data.length) {
//				res += "{" + data[j] + "}";
//			} else {
//				res += "{" + data[j] + "},";
//			}
//
//		}
////		
//
//		res += "]";
//		// System.out.println(res);
//		return res;
		return "";
	}

	@Override
	@Transactional
	public void updateInterviewStatus(long intId, String overallFeedback) {
		// String query = "update data_interviews_schedule set interview_status='" +
		// overallFeedback + "' where id="+ intId;
		// System.out.println(query);
		// entityManager.createNativeQuery(query).executeUpdate();
//		interviewsRepo.updateInterviewStatus(overallFeedback, intId);
    }

    @Override
    public List<CandidateDTO> getAllInterviewsWithCandidateName() {
        List<Object[]> data = interviewsRepo.findAllInterviewsWithCandidates();
        
        List<CandidateDTO> list = new ArrayList<>();
        for (Object[] o : data) {
            CandidateDTO candidateDTO = new CandidateDTO();
            Long cid = Long.valueOf(o[0].toString());
            String fullName = o[1].toString();
            String currentLocation = o[2].toString();
            String currentlyWorkingAs = o[3].toString();
            String curentlyWorkingAt = o[4].toString();
            String email = o[5].toString();
            Long rrfid = Long.valueOf(o[6].toString());
            Long id = Long.valueOf(o[7].toString());
            String interviewName = o[8].toString();
            String feedbackStatus = o[9].toString();
            String candidateStatus = o[10].toString();
            String candidateSubStatus = o[11].toString();
            
            Long rrf_id = Long.valueOf(o[12].toString());
            String hiringType = o[13].toString();
            String primarySkills = o[14].toString();
            String customerName = o[15].toString();           
            
            
            candidateDTO.setCid(cid);
            candidateDTO.setFullName(fullName);
            candidateDTO.setCurrentLocation(currentLocation);
            //    candidateDTO.setCurrentLocation(currentLocation);
            candidateDTO.setCurrentlyWorkingAs(currentlyWorkingAs);
            candidateDTO.setCurrentlyWorkingAt(curentlyWorkingAt);
            candidateDTO.setEmailId(email);
            candidateDTO.setStatus(candidateStatus);
            candidateDTO.setSubStatus(candidateSubStatus);
            
            RrfDTO rrfdto = new RrfDTO();
            rrfdto.setId(rrfid);
            rrfdto.setHiringType(hiringType);
            rrfdto.setCustomerName(customerName);
            rrfdto.setPrimarySkills(primarySkills);
            
            InterviewDto interviewDto = new InterviewDto();
            interviewDto.setId(id);
            interviewDto.setInterviewerName(interviewName);
            interviewDto.setInterviewStatus(feedbackStatus);

            candidateDTO.setInterviewDto(interviewDto);
            candidateDTO.setRrfdto(rrfdto);
            
            list.add(candidateDTO);
        }
        return list;

    }


//	@Override
//	public List<InterviewDto> getAllInterviewsWithCandidates() {
//
//		List<Interviews> interviews = interviewsRepo.findAll(); // Replace with your repository method
//
//		List<InterviewDto> interviewDTOs = new ArrayList<>();
//		for (Interviews interview : interviews) {
//			InterviewDto interviewDTO = new InterviewDto();
//			interviewDTO.setId(interview.getId());
//
//			// Fetch and populate candidate information
//			Candidate candidate = interview.getCandidate();
//			if (candidate != null) {
//				CandidateDTO candidateDTO = new CandidateDTO();
//				candidateDTO.setCid(candidate.getCid());
//				candidateDTO.setFullName(candidate.getFullName());
//				candidateDTO.setEmailId(candidate.getEmailId());
//				candidateDTO.setComments(candidate.getComments());
//
//
//				// Add other relevant candidate properties to the CandidateDTO
//
//				interviewDTO.setCandidate(candidateDTO);
//			}
//
//			// Populate other interview properties
//			interviewDTO.setInterviewRound(interview.getInterviewRound());
//			interviewDTO.setInterviewerName(interview.getInterviewerName());
//			interviewDTO.setInterviewType(interview.getInterviewType());
//			// Set other interview properties
//
//			interviewDTOs.add(interviewDTO);
//		}
//
//		return interviewDTOs;
//
//	}

}
