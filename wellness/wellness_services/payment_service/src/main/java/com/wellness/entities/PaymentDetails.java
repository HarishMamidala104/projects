package com.wellness.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellness.enums.PaymentMode;
import com.wellness.enums.PaymentStatus;
import com.wellness.enums.PaymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long paymentId;

	private long userId;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	private String responsePayload;

	private String requestPayload;

	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	private Date updateDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date creationDate = new Date();

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private String trancationId;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PaymentStatus paymentStatus = PaymentStatus.PENDING;

}
