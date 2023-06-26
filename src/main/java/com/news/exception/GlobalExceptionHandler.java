package com.news.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	/*
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), "An error occurred");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	*/
	@ExceptionHandler(AdvertisementNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAdvertisementNotFoundException(AdvertisementNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(AdvertiserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAdvertiserNotFoundException(AdvertiserNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleBookingNotFoundException(BookingNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(NewspaperNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNewspaperNotFoundException(NewspaperNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(PaymentNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(DuplicateBookingException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateBookingException(DuplicateBookingException ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

}
