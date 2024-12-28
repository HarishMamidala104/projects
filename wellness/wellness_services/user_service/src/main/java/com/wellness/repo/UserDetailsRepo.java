package com.wellness.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellness.entities.UserDetails;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Long>{

	Optional<UserDetails> findByEmail(String email);
	
	Optional<UserDetails> findByOtp(String otp);

}
