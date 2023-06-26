package com.news.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.entity.Advertisement;
import com.news.entity.Booking;
import com.news.entity.Payment;
import com.news.exception.PaymentNotFoundException;
import com.news.repository.BookingRepository;
import com.news.repository.PaymentRepository;
@Service
public class NewPaymentService {

	@Autowired

	private PaymentRepository paymentRepository;

	@Autowired

	private BookingRepository bookingRepository;

	@Transactional

	public String assignPayment(Booking booking) {

		// Calculate the total cost for advertisements

		double totalCost = calculateTotalCost(booking.getAdvertisement());

		// Generate a transaction ID

		String transactionId = generateTransactionId();

		// Create a new Payment object

		Payment payment = new Payment();

		payment.setBooking(booking);

		payment.setPaymentId(transactionId);
		payment.setPayment(totalCost);

		// Save the payment

		paymentRepository.save(payment);

		// Update the booking status to 'paid'

		booking.setBookingStatus("Booked");

		bookingRepository.save(booking);

		// Generate and return the bill

		String bill = generateBill(transactionId, totalCost);

		return bill;

	}

	double calculateTotalCost(Advertisement advertisement) {

		LocalDate publicationDate = advertisement.getPublicationDate();

		LocalDate expiryDate = advertisement.getExpiryDate();

		double advertisementPrice = advertisement.getAdvertisementPrice();

		// Calculate the number of days between publication and expiry

		long days = ChronoUnit.DAYS.between(publicationDate, expiryDate);

		// Calculate the total cost

		double totalCost = advertisementPrice * (days + 1); // Including the publication day

		return totalCost;

	}

	String generateTransactionId() {

		// Logic to generate a transaction ID

		String transactionId = UUID.randomUUID().toString();

		return transactionId;

	}

	private String generateBill(String transactionId, double totalCost) {

		// Generate the bill with the transaction ID and total cost

		String bill = "Transaction ID: " + transactionId + "\n";

		bill += "Total Cost: $" + totalCost + "\n";

		// Add other relevant information to the bill

		return bill;

	}

	/*
	public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
	*/
    public Payment updatePayment(Payment payment) throws PaymentNotFoundException {
        Optional<Payment> existingPayment = paymentRepository.findById(payment.getId());
        if (!existingPayment.isPresent()) {
            throw new PaymentNotFoundException("Payment not found with ID: " + payment.getId());
        }
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) throws PaymentNotFoundException {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (!payment.isPresent()) {
            throw new PaymentNotFoundException("Payment not found with ID: " + paymentId);
        }
        return payment.get();
    }

    public void deletePaymentById(Long paymentId) throws PaymentNotFoundException {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (!payment.isPresent()) {
            throw new PaymentNotFoundException("Payment not found with ID: " + paymentId);
        }
        paymentRepository.delete(payment.get());
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public void completePayment(String paymentId, Double paymentAmount) throws PaymentNotFoundException {
        Payment optionalPayment = paymentRepository.findByPaymentId(paymentId);
        
            Payment payment = optionalPayment;
            payment.setPayment(paymentAmount);
            // You can perform other necessary operations here, such as updating the payment status or storing additional information.
            paymentRepository.save(payment);
        }
}
