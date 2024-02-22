package com.parkking.parkingservice.service;

import com.parkking.parkingservice.dto.booking.SaveBooking;
import com.parkking.parkingservice.model.Booking;
import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings();

    Booking saveBooking(SaveBooking booking);

    Booking getBookingById(Long id);

    Booking updateBooking(Long id, Booking bookingDetails);

    void deleteBooking(Long id);
}