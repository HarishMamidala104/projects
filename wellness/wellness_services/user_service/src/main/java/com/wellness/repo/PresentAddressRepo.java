package com.wellness.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellness.entities.PresentAddress;

@Repository
public interface PresentAddressRepo extends JpaRepository<PresentAddress, Long> {

	public Optional<PresentAddress> findByUserId(long userId);
}
