package com.news.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.entity.Advertisement;
import com.news.entity.Booking;
import com.news.exception.BookingAlreadyExistsException;
import com.news.exception.BookingNotFoundException;
import com.news.exception.DuplicateBookingException;
import com.news.repository.BookingRepository;
import com.news.repository.PaymentRepository;
import com.news.service.BookingService;

@Service

public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;

	@Autowired

	private PaymentRepository paymentRepository;

	@Autowired

	public BookingServiceImpl(BookingRepository bookingRepository) {

		this.bookingRepository = bookingRepository;

	}

	@Override
	public Booking addBooking(Booking booking) throws DuplicateBookingException {
	    Advertisement advertisement = booking.getAdvertisement();
	    LocalDate bookingDate = booking.getBookingDate();

	    // Check if the booking date is within the publication date and expiry date of the advertisement
	    if (bookingDate.isBefore(advertisement.getPublicationDate()) || bookingDate.isAfter(advertisement.getExpiryDate())) {
	        throw new IllegalArgumentException("Booking date is outside the publication and expiry date of the advertisement.");
	    }

	    // Check if the booking already exists
	    Optional<Booking> existingBooking = bookingRepository.findByAdvertisementAndBookingDate(advertisement, bookingDate);

	    if (existingBooking.isPresent()) {
	        // Handle duplicate booking scenario
	        throw new DuplicateBookingException("Booking already exists for the advertisement and booking date.");
	    }

	    return bookingRepository.save(booking);
	}



	@Override

	public Booking updateBooking(long id, Booking booking) throws BookingNotFoundException {

		Optional<Booking> existingBooking = bookingRepository.findById(id);

		if (!existingBooking.isPresent()) {

			throw new BookingNotFoundException("Booking not found with ID: " + id);

		}
		Booking updated = existingBooking.get();
		updated.setBookingId(id);
		updated.setAdvertisement(existingBooking.get().getAdvertisement());
		updated.setBookingStatus(booking.getBookingStatus());
		updated.setBookingDate(booking.getBookingDate());

		return bookingRepository.save(updated);

	}

	@Override

	public Booking findBookingById(long bookingId) throws BookingNotFoundException {

		Optional<Booking> booking = bookingRepository.findById(bookingId);

		if (!booking.isPresent()) {

			throw new BookingNotFoundException("Booking not found with ID: " + bookingId);

		}

		return booking.get();

	}

	@Override

	public void deleteBookingById(long bookingId) throws BookingNotFoundException {

		Optional<Booking> booking = bookingRepository.findById(bookingId);

		if (!booking.isPresent()) {

			throw new BookingNotFoundException("Booking not found with ID: " + bookingId);

		}

		bookingRepository.delete(booking.get());

	}

	@Override

	public List<Booking> getAllBookings() {

		return bookingRepository.findAll();

	}

	

	@Override

	public List<Booking> getAllBookingsByAdvertiserId(long advertiserId) {

		List<Booking> bookings = bookingRepository.findAll();

		List<Booking> bookingsByAdvertiser = bookings.stream()

				.filter(booking -> booking.getAdvertisement().getAdvertiser().getId() == advertiserId)

				.collect(Collectors.toList());

		return bookingsByAdvertiser;

	}

}