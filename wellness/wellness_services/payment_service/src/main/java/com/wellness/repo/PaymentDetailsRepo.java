package com.wellness.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellness.entities.PaymentDetails;
@Repository
public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long> {

}
