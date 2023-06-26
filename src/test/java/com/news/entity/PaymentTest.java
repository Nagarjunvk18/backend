package com.news.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentTest {

    
    @Test
    void gettersAndSettersShouldGetAndSetPropertiesCorrectly() {
        // Arrange
        Long id = 1L;
        Booking booking = new Booking();
        String paymentId = "PAYMENT-123";
        double paymentAmount = 100.0;
        LocalDate paidDate = LocalDate.of(2023, 6, 12);

        // Act
        Payment payment = new Payment();
        payment.setId(id);
        payment.setBooking(booking);
        payment.setPaymentId(paymentId);
        payment.setPayment(paymentAmount);
        payment.setPaidDate(paidDate);

        // Assert
        assertEquals(id, payment.getId());
        assertEquals(booking, payment.getBooking());
        assertEquals(paymentId, payment.getPaymentId());
        assertEquals(paymentAmount, payment.getPayment());
        assertEquals(paidDate, payment.getPaidDate());
    }
}
