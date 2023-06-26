package com.news.controller;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PaymentResponse {

	private String paymentId;
	private Long bookingId;
	private double payment;
	private LocalDate date;
}
