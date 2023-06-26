package com.news.service;
import com.news.entity.Booking;
import com.news.exception.BookingAlreadyExistsException;
import com.news.exception.BookingNotFoundException;
import com.news.exception.DuplicateBookingException;

import java.util.List;

public interface BookingService {

    Booking addBooking(Booking booking)throws DuplicateBookingException;

    Booking updateBooking(long id, Booking booking) throws BookingNotFoundException;

    Booking findBookingById(long bookingId) throws BookingNotFoundException;

    void deleteBookingById(long bookingId) throws BookingNotFoundException;

    List<Booking> getAllBookings();

    List<Booking> getAllBookingsByAdvertiserId(long advertiserId);
}
