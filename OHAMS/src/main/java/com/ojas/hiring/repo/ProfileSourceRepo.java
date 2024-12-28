package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.hiring.entity.ProfileSource;

//@Author Rajendar.Baswaraju
public interface ProfileSourceRepo extends JpaRepository<ProfileSource, Integer> {

	ProfileSource save(ProfileSource profileSource);

}