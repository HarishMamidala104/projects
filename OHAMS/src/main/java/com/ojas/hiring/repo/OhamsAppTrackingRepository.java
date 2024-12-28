package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.OhamsAppTracking;
@Repository
public interface OhamsAppTrackingRepository extends JpaRepository<OhamsAppTracking, Integer> {

}
