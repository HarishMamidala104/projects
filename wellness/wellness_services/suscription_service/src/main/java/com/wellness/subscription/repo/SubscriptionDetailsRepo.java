package com.wellness.subscription.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellness.subscription.entities.SubscriptionDetails;

@Repository
public interface SubscriptionDetailsRepo extends JpaRepository<SubscriptionDetails, Long> {
	
}
