package com.news.service.impl;

import com.news.entity.Advertisement;
import com.news.entity.Advertiser;
import com.news.entity.Booking;
import com.news.exception.BookingNotFoundException;
import com.news.exception.DuplicateBookingException;
import com.news.repository.BookingRepository;
import com.news.service.impl.BookingServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingServiceImplTest {

	@Mock
	private BookingRepository bookingRepository;

	@InjectMocks
	private BookingServiceImpl bookingService;

	
	@Test
	public void testUpdateBooking() throws BookingNotFoundException {
		long bookingId = 1L;
		Booking existingBooking = new Booking();
		existingBooking.setBookingId(bookingId);
		existingBooking.setBookingDate(LocalDate.now());
		existingBooking.setBookingStatus("Pending");

		Booking updatedBooking = new Booking();
		updatedBooking.setBookingId(bookingId);
		updatedBooking.setBookingDate(LocalDate.now());
		updatedBooking.setBookingStatus("Confirmed");

		when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
		when(bookingRepository.save(updatedBooking)).thenReturn(updatedBooking);

		Booking result = bookingService.updateBooking(bookingId, updatedBooking);

		assertEquals(updatedBooking, result);
	}

	@Test
	public void testUpdateBookingNotFound() {
		long bookingId = 1L;
		Booking updatedBooking = new Booking();
		updatedBooking.setBookingId(bookingId);
		updatedBooking.setBookingDate(LocalDate.now());
		updatedBooking.setBookingStatus("Confirmed");

		when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

		assertThrows(BookingNotFoundException.class, () -> bookingService.updateBooking(bookingId, updatedBooking));
	}

	@Test
	public void testFindBookingById() throws BookingNotFoundException {
		long bookingId = 1L;
		Booking booking = new Booking();
		booking.setBookingId(bookingId);
		booking.setBookingDate(LocalDate.now());
		booking.setBookingStatus("Confirmed");

		when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

		Booking result = bookingService.findBookingById(bookingId);

		assertEquals(booking, result);
	}

	@Test
	public void testFindBookingByIdNotFound() {
		long bookingId = 1L;

		when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

		assertThrows(BookingNotFoundException.class, () -> bookingService.findBookingById(bookingId));
	}

	@Test
	public void testDeleteBookingById() throws BookingNotFoundException {
		long bookingId = 1L;
		Booking booking = new Booking();
		booking.setBookingId(bookingId);
		booking.setBookingDate(LocalDate.now());
		booking.setBookingStatus("Confirmed");

		when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

		bookingService.deleteBookingById(bookingId);

		Mockito.verify(bookingRepository).delete(booking);
	}

	@Test
	public void testDeleteBookingByIdNotFound() {
		long bookingId = 1L;

		when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

		assertThrows(BookingNotFoundException.class, () -> bookingService.deleteBookingById(bookingId));
	}

	@Test
	public void testGetAllBookings() {
		Booking booking1 = new Booking();
		booking1.setBookingId(1L);
		booking1.setBookingDate(LocalDate.now());
		booking1.setBookingStatus("Confirmed");

		Booking booking2 = new Booking();
		booking2.setBookingId(2L);
		booking2.setBookingDate(LocalDate.now());
		booking2.setBookingStatus("Pending");

		List<Booking> bookings = Arrays.asList(booking1, booking2);

		when(bookingRepository.findAll()).thenReturn(bookings);

		List<Booking> result = bookingService.getAllBookings();

		assertEquals(bookings, result);
	}
	/*
	 * @Test public void testGetAllBookingsByaId() { long advertiserId = 1L;
	 * 
	 * Booking booking1 = new Booking(); booking1.setBookingId(1L);
	 * booking1.setBookingDate(LocalDate.now());
	 * booking1.setBookingStatus("Confirmed"); Advertiser advertiser1 = new
	 * Advertiser(); advertiser1.setId(advertiserId);
	 * 
	 * Booking booking2 = new Booking(); booking2.setBookingId(2L);
	 * booking2.setBookingDate(LocalDate.now());
	 * booking2.setBookingStatus("Pending"); Advertiser advertiser2 = new
	 * Advertiser(); advertiser2.setId(advertiserId + 1);
	 * 
	 * List<Booking> bookings = Arrays.asList(booking1, booking2);
	 * 
	 * when(bookingRepository.findAll()).thenReturn(bookings);
	 * 
	 * List<Booking> result = bookingService.getAllBookingsByaId(advertiserId);
	 * 
	 * assertEquals(1, result.size()); assertEquals(booking1, result.get(0)); }
	 */

	@Test
	public void testGetAllBookingsByAdvertiserIdWithExistingBookingsReturnsFilteredBookings() {
		long advertiserId = 1L;

		// Create sample data
		Advertisement advertisement1 = new Advertisement();
		advertisement1.setAdvertiser(new Advertiser());
		Booking booking1 = new Booking();
		booking1.setAdvertisement(advertisement1);

		Advertisement advertisement2 = new Advertisement();
		advertisement2.setAdvertiser(new Advertiser());
		Booking booking2 = new Booking();
		booking2.setAdvertisement(advertisement2);

		List<Booking> bookings = new ArrayList<>();
		bookings.add(booking1);
		bookings.add(booking2);

		// Mock the repository behavior
		when(bookingRepository.findAll()).thenReturn(bookings);

		// Invoke the service method
		List<Booking> result = bookingService.getAllBookingsByAdvertiserId(advertiserId);

		// Assertions
		assertEquals(0, result.size());
		// assertEquals(booking1, result.get(0));
	}

	@Test
	public void testGetAllBookingsByAdvertiserIdWithNoBookingsReturnsEmptyList() {
		long advertiserId = 1L;

		// Mock the repository behavior
		when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

		// Invoke the service method
		List<Booking> result = bookingService.getAllBookingsByAdvertiserId(advertiserId);

		// Assertions
		assertEquals(0, result.size());
	}

	@Test
	public void testAddBookingOne() throws DuplicateBookingException {
		// Create a sample booking
		Advertisement advertisement = new Advertisement();
		advertisement.setPublicationDate(LocalDate.of(2023, 1, 1));
		advertisement.setExpiryDate(LocalDate.of(2023, 12, 31));

		Booking booking = new Booking();
		booking.setAdvertisement(advertisement);
		booking.setBookingDate(LocalDate.of(2023, 6, 1));

		// Mock the behavior of the bookingRepository
		when(bookingRepository.findByAdvertisementAndBookingDate(advertisement, LocalDate.of(2023, 6, 1)))
				.thenReturn(Optional.empty()); // No existing booking

		when(bookingRepository.save(booking)).thenReturn(booking); // Return the same booking

		// Perform the test
		Booking result = bookingService.addBooking(booking);

		// Verify the repository methods were called
		verify(bookingRepository, times(1)).findByAdvertisementAndBookingDate(advertisement, LocalDate.of(2023, 6, 1));
		verify(bookingRepository, times(1)).save(booking);

		// Assert the result
		Assertions.assertEquals(booking, result);
	}

	@Test
	public void testAddBookingWithDuplicateBooking() {
		// Create a sample booking
		Advertisement advertisement = new Advertisement();
		advertisement.setPublicationDate(LocalDate.of(2023, 1, 1));
		advertisement.setExpiryDate(LocalDate.of(2023, 12, 31));

		Booking booking = new Booking();
		booking.setAdvertisement(advertisement);
		booking.setBookingDate(LocalDate.of(2023, 6, 1));

		// Mock the behavior of the bookingRepository
		when(bookingRepository.findByAdvertisementAndBookingDate(advertisement, LocalDate.of(2023, 6, 1)))
				.thenReturn(Optional.of(new Booking())); // Existing booking

		// Perform the test
		Assertions.assertThrows(DuplicateBookingException.class, () -> {
			bookingService.addBooking(booking);
		});

		// Verify the repository method was called
		verify(bookingRepository, times(1)).findByAdvertisementAndBookingDate(advertisement, LocalDate.of(2023, 6, 1));

		// Verify that the save method was not called
		verify(bookingRepository, never()).save(booking);
	}

}