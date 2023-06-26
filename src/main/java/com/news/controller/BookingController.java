package com.news.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.entity.Advertisement;
import com.news.entity.Booking;
import com.news.exception.AdvertisementNotFoundException;
import com.news.exception.BookingAlreadyExistsException;
import com.news.exception.BookingNotFoundException;
import com.news.exception.DuplicateBookingException;
import com.news.service.AdvertisementService;
import com.news.service.BookingService;
import com.news.service.NewPaymentService;

@RestController

@RequestMapping("/bookings")

public class BookingController {

	@Autowired

	private NewPaymentService paymentService;

	@Autowired

	private BookingService bookingService;

	@Autowired

	private AdvertisementService advertisementService;
/*
	@PostMapping("/add")

	public ResponseEntity<String> postBooking(@RequestBody Booking booking) throws BookingAlreadyExistsException {

		bookingService.addBooking(booking);

		return ResponseEntity.status(HttpStatus.OK).body("booking added in DB");

	}
	*/

	@GetMapping("/getall")

	public List<Booking> getAllBookings() {

		List<Booking> list = bookingService.getAllBookings();

		return list;

	}

	@GetMapping("/one/{id}")

	public ResponseEntity<Booking> getBookingById(@PathVariable("id") long id) throws BookingNotFoundException {

		Booking booking = bookingService.findBookingById(id);

		return new ResponseEntity<>(booking, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")

	public ResponseEntity<String> deleteBookingById(@PathVariable("id") int id) throws BookingNotFoundException {

		bookingService.deleteBookingById(id);

		return ResponseEntity.status(HttpStatus.OK).body("booking details Deleted successfully with id: " + id);

	}

	@PutMapping("/edit/{id}")

	public ResponseEntity<Booking> updateBooking(@PathVariable("id") long id, @RequestBody Booking booking)

			throws BookingNotFoundException {

		Booking updatedBooking = bookingService.updateBooking(id, booking);

		return new ResponseEntity<>(updatedBooking, HttpStatus.OK);

	}

	@GetMapping("/assign-payment/{id}")

	public ResponseEntity<String> assignPayment(@PathVariable("id") long bookingId) throws BookingNotFoundException {

		Booking booking = bookingService.findBookingById(bookingId);

		String bill = paymentService.assignPayment(booking);

		return ResponseEntity.ok(bill);

	}

	@PostMapping("/booking/{adid}")
	public ResponseEntity<Object> postBooking(@PathVariable("adid") int id, @RequestBody Booking booking)
			throws AdvertisementNotFoundException, DuplicateBookingException {
		// Set the meter using the provided meter ID

		Optional<Advertisement> optionalAdvertisement = Optional.of(advertisementService.findAdvertisementById(id));
		if (!optionalAdvertisement.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Advertisement ID");
		}
		Advertisement advertisement = optionalAdvertisement.get();

		booking.setAdvertisement(advertisement);

		// Save the customer using the customerService

		bookingService.addBooking(booking);
		// Return a success message in the response body
		return ResponseEntity.status(HttpStatus.OK).body("booking added for advertisement with id: " + id);
	}

	@GetMapping("/advertiser/{advertiserId}")
	public ResponseEntity<List<Booking>> getAllBookingsByAdvertiserId(

			@PathVariable("advertiserId") long advertiserId) {

		List<Booking> bookings = bookingService.getAllBookingsByAdvertiserId(advertiserId);

		return ResponseEntity.ok(bookings);

	}

}
