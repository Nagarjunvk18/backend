package com.news.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.news.entity.Booking;
import com.news.exception.AdvertisementNotFoundException;
import com.news.exception.BookingNotFoundException;
import com.news.exception.DuplicateBookingException;
import com.news.service.BookingService;

public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    

    @Test
    public void testGetAllBookings() {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> expectedBookings = Arrays.asList(booking1, booking2);

        when(bookingService.getAllBookings()).thenReturn(expectedBookings);

        List<Booking> bookings = bookingController.getAllBookings();

        verify(bookingService, times(1)).getAllBookings();
        assertEquals(expectedBookings, bookings);
    }

    @Test
    public void testGetBookingById() throws BookingNotFoundException {
        long bookingId = 1L;
        Booking expectedBooking = new Booking();
        expectedBooking.setBookingId(bookingId);

        when(bookingService.findBookingById(bookingId)).thenReturn(expectedBooking);

        ResponseEntity<Booking> response = bookingController.getBookingById(bookingId);

        verify(bookingService, times(1)).findBookingById(bookingId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBooking, response.getBody());
    }

    @Test
    public void testDeleteBookingById() throws BookingNotFoundException {
        int bookingId = 1;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("booking details Deleted successfully with id: 1");

        doNothing().when(bookingService).deleteBookingById(bookingId);

        ResponseEntity<String> response = bookingController.deleteBookingById(bookingId);

        verify(bookingService, times(1)).deleteBookingById(bookingId);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testUpdateBooking() throws BookingNotFoundException {
        long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        Booking updatedBooking = new Booking();
        updatedBooking.setBookingId(bookingId);

        when(bookingService.updateBooking(bookingId,booking)).thenReturn(updatedBooking);

        ResponseEntity<Booking> response = bookingController.updateBooking(bookingId, booking);

        verify(bookingService, times(1)).updateBooking(bookingId,booking);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBooking, response.getBody());
    }

   
}