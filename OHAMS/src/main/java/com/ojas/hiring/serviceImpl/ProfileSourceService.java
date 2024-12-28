package com.ojas.hiring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.HireType;
import com.ojas.hiring.entity.ProfileSource;
import com.ojas.hiring.repo.ProfileSourceRepo;

@Service
//@Author Rajendar.Baswaraju
public class ProfileSourceService {
	@Autowired
	ProfileSourceRepo profileSourceRepo;

	public ResponseEntity<Object> saveProfileSource(ProfileSource profileSource) {
		Optional<ProfileSource> findId = profileSourceRepo.findById(profileSource.getProfileid());
		ProfileSource addProfile = null;

		List<ProfileSource> getall = profileSourceRepo.findAll();

		for (ProfileSource get : getall) {
			if (get.getProfilename().equals(profileSource.getProfilename())) {
				return ResponseEntity.badRequest().body(" Given Profile Name is Already exit");
			}
		}

		if (findId.isPresent()) {
			addProfile = profileSourceRepo.save(profileSource);
		}
		return new ResponseEntity<Object>(addProfile, HttpStatus.OK);

	}

	public List<ProfileSource> getAllById() {
		return profileSourceRepo.findAll();

	}

	public void deleteProfileSource(int profileid) {
		profileSourceRepo.deleteById(profileid);
	}

	public ResponseEntity<Object> profileupdate(ProfileSource profileSource, int id) {

		Optional<ProfileSource> findId = profileSourceRepo.findById(id);

		if (findId.isPresent()) {
			return ResponseEntity.badRequest().body(" Given ID is not exit or Already deleted");
		} else {
			ProfileSource addProfile = profileSourceRepo.findById(id).get();
			if (profileSource.getProfilename() == null) {
				String name = findId.get().getProfilename();
				addProfile.setProfilename(name);
			} else {
				addProfile.setProfilename(profileSource.getProfilename());
			}

			if (profileSource.getProfileid() == 0) {
				int addId = findId.get().getProfileid();
				addProfile.setProfileid(addId);
			} else {
				addProfile.setProfileid(profileSource.getProfileid());
			}
			profileSourceRepo.saveAndFlush(addProfile);
			return new ResponseEntity<Object>(addProfile, HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> updateEmployee(HireType emp, int id) {

		Optional<ProfileSource> findId = profileSourceRepo.findById(id);

		if (findId.isPresent()) {
			return ResponseEntity.badRequest().body(" Given ID is not exit or Already deleted");
		} else {
			ProfileSource db = profileSourceRepo.findById(id).get();

			if (emp.getHireType() == null) {
				String name = findId.get().getProfilename();
				db.setProfilename(name);
			} else {
				db.setProfilename(emp.getHireType());
			}
			profileSourceRepo.saveAndFlush(db);
			return new ResponseEntity<Object>(db, HttpStatus.OK);
		}
	}
}