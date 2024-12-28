package com.ojas.hiring.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.PrimarySkills;
import com.ojas.hiring.entity.Vendor;
import com.ojas.hiring.repo.PrimarySkillsRepo;

@Service
public class PrimaryService {
	@Autowired
	private PrimarySkillsRepo primaryRepository;

//	public boolean updateTechnology(int id, PrimarySkills updatedTechnlogy) {//kava
//		System.out.println("hiiii"+updatedTechnlogy.getPrimarySkills());
//
//		Optional<PrimarySkills> optionalTechnology = primaryRepository.getId(id);//50
//		System.out.println("hiiii"+optionalTechnology.get().getPrimarySkills());
//
//		LocalDate now = LocalDate.now();
//		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
//		String format = now.format(ofPattern);
//		if (optionalTechnology.isPresent()) {
//			PrimarySkills existingTechnology = optionalTechnology.get();
//			System.out.println("hiiii"+existingTechnology.getPrimarySkills());
//			existingTechnology.setPrimarySkills(updatedTechnlogy.getPrimarySkills());
//			primaryRepository.save(existingTechnology);
//			return true;
//		} else {
//			return false;
//		}
//	}
	 public boolean updatedPrimarySkills(int id, PrimarySkills updatedPrimarySkills) {
	        Optional<PrimarySkills> optionalPrimarySkills = primaryRepository.getId(id);

	        if (optionalPrimarySkills.isPresent()) {
	            PrimarySkills existingPrimarySkill = optionalPrimarySkills.get();
	            existingPrimarySkill.setPrimarySkills(updatedPrimarySkills.getPrimarySkills());
	            primaryRepository.save(existingPrimarySkill);
	            return true;
	        } else {
	            return false;
	        }
	    }
}
