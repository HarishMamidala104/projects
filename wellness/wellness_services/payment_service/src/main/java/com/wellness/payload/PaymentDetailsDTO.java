package com.wellness.payload;

import java.util.Date;

import com.wellness.enums.PaymentMode;
import com.wellness.enums.PaymentStatus;
import com.wellness.enums.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetailsDTO {

	private long paymentId;

	private long userId;

	private PaymentType paymentType;

	private String responsePayload;

	private String requestPayload;

	private PaymentMode paymentMode;

	private Date updateDate = new Date();

	private Date creationDate = new Date();

	private String trancationId;

	private PaymentStatus paymentStatus;

}
