package com.news.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.news.entity.Advertisement;
import com.news.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	Optional<Booking> findByAdvertisementAndBookingDate(Advertisement advertisement, LocalDate bookingDate);
}
