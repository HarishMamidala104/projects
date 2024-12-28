package com.wellness.subscription.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellness.subscription.entities.SubscriberDetails;

@Repository
public interface SubscribersDetailsRepo extends JpaRepository<SubscriberDetails, Long> {

}
