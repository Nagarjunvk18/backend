package com.news.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.news.entity.Advertisement;
import com.news.entity.Booking;
import com.news.entity.Payment;
import com.news.exception.PaymentNotFoundException;
import com.news.repository.BookingRepository;
import com.news.repository.PaymentRepository;

public class NewPaymentServiceTest {
	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private BookingRepository bookingRepository;

	private NewPaymentService paymentService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		paymentService = new NewPaymentService();
	}

	@Test
	public void testCalculateTotalCost() {
		// Create a sample advertisement
		Advertisement advertisement = new Advertisement();
		advertisement.setPublicationDate(LocalDate.of(2023, 1, 1));
		advertisement.setExpiryDate(LocalDate.of(2023, 1, 5));
		advertisement.setAdvertisementPrice(10.0);

		// Perform the test
		double totalCost = paymentService.calculateTotalCost(advertisement);

		// Assert the result
		Assertions.assertEquals(50.0, totalCost);
	}

	@Test
	public void testGenerateTransactionId() {
		// Perform the test
		String transactionId = paymentService.generateTransactionId();

		// Assert the result
		Assertions.assertNotNull(transactionId);
		// Add more assertions as needed for the generated transaction ID
	}

}
