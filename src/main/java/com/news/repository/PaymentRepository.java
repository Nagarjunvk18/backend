package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.news.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Payment findByPaymentId(String paymentId);

}
