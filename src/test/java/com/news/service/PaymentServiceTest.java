package com.news.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.news.entity.Advertisement;
import com.news.entity.Booking;
import com.news.entity.Payment;
import com.news.repository.BookingRepository;
import com.news.repository.PaymentRepository;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private NewPaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assignPaymentShouldCreatePaymentAndSetBookingStatusToPaid() {
        // Arrange
        Booking booking = new Booking();
        Advertisement advertisement = new Advertisement();
        advertisement.setPublicationDate(LocalDate.now());
        advertisement.setExpiryDate(LocalDate.now().plusDays(7));
        advertisement.setAdvertisementPrice(100.0);
        booking.setAdvertisement(advertisement);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        String bill = paymentService.assignPayment(booking);

        // Assert
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void calculateTotalCostShouldReturnCorrectTotalCost() {
        // Arrange
        Advertisement advertisement = new Advertisement();
        advertisement.setPublicationDate(LocalDate.now());
        advertisement.setExpiryDate(LocalDate.now().plusDays(7));
        advertisement.setAdvertisementPrice(100.0);

        // Act
        double totalCost = paymentService.calculateTotalCost(advertisement);

        // Assert
        assertEquals(800.0, totalCost);
    }

    @Test
    void generateTransactionIdShouldGenerateUniqueTransactionId() {
        // Act
        String transactionId1 = paymentService.generateTransactionId();
        String transactionId2 = paymentService.generateTransactionId();

        // Assert
        assertEquals(36, transactionId1.length());
        assertEquals(36, transactionId2.length());
        assertNotEquals(transactionId1, transactionId2);
    }
}
