package com.news.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.entity.Payment;
import com.news.exception.PaymentNotFoundException;
import com.news.service.NewPaymentService;

@CrossOrigin(origins = "*", maxAge = 3006)
@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final NewPaymentService paymentService;

	@Autowired
	public PaymentController(NewPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long paymentId)
			throws PaymentNotFoundException {
		Payment payment = paymentService.getPaymentById(paymentId);
		PaymentResponse response = new PaymentResponse();
		response.setPaymentId(payment.getPaymentId());
		response.setBookingId(payment.getBooking().getBookingId());
		response.setPayment(payment.getPayment());
		response.setDate(payment.getPaidDate());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{paymentId}")
	public ResponseEntity<Void> deletePaymentById(@PathVariable Long paymentId) throws PaymentNotFoundException {
		paymentService.deletePaymentById(paymentId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/allpayments")
	public ResponseEntity<List<PaymentResponse>> getAllPayments() {
		List<Payment> payments = paymentService.getAllPayments();
		List<PaymentResponse> paymentResponses = new ArrayList<>();

		for (Payment payment : payments) {
			PaymentResponse response = new PaymentResponse();
			response.setPaymentId(payment.getPaymentId());
			response.setBookingId(payment.getBooking().getBookingId());
			response.setPayment(payment.getPayment());
			response.setDate(payment.getPaidDate());
			paymentResponses.add(response);
		}

		return ResponseEntity.ok(paymentResponses);
	}

	@PostMapping("/complete")
	public ResponseEntity<String> completePayment(@RequestBody Payment payment) {
		try {
			// Retrieve payment details from the request
			String paymentId = payment.getPaymentId();
			Double paymentAmount = payment.getPayment();

			// Complete the payment process (e.g., insert payment details into the database)
			paymentService.completePayment(paymentId, paymentAmount);

			// Return a success message
			String message = "Payment successful!";
			return ResponseEntity.ok(message);
		} catch (PaymentNotFoundException e) {
			// Handle the case where the payment ID is not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
		} catch (Exception e) {
			// Handle other exceptions or errors
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to complete payment");
		}
	}
}
