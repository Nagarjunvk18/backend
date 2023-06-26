package com.news.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingTest {

    private Booking booking;
    private Advertisement advertisement;
    private long bookingId;
    private LocalDate bookingDate;
    private String bookingStatus;

    @BeforeEach
    public void setUp() {
        bookingId = 1L;
        advertisement = new Advertisement();
        bookingDate = LocalDate.now();
        bookingStatus = "Confirmed";
        booking = new Booking(bookingId, advertisement, bookingDate, bookingStatus);
    }

    @Test
    public void testSetAndGetBookingId() {
        assertEquals(bookingId, booking.getBookingId());
    }

    @Test
    public void testSetAndGetAdvertisement() {
        assertNotNull(booking.getAdvertisement());
    }

    @Test
    public void testSetAndGetBookingDate() {
        assertEquals(bookingDate, booking.getBookingDate());
    }

    @Test
    public void testSetAndGetBookingStatus() {
        assertEquals(bookingStatus, booking.getBookingStatus());
    }

    @Test
    public void testAllArgsConstructor() {
        Booking newBooking = new Booking(bookingId, advertisement, bookingDate, bookingStatus);
        assertNotNull(newBooking);
        assertEquals(bookingId, newBooking.getBookingId());
        assertNotNull(newBooking.getAdvertisement());
        assertEquals(bookingDate, newBooking.getBookingDate());
        assertEquals(bookingStatus, newBooking.getBookingStatus());
    }
}
