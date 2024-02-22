package com.parkking.parkingservice.service.impl;

import com.parkking.parkingservice.dto.booking.SaveBooking;
import com.parkking.parkingservice.model.Booking;
import com.parkking.parkingservice.model.ParkingSpot;
import com.parkking.parkingservice.model.user.Driver;
import com.parkking.parkingservice.model.user.User;
import com.parkking.parkingservice.repository.BookingRepository;
import com.parkking.parkingservice.repository.DriverRepository;
import com.parkking.parkingservice.repository.ParkingSpotRepository;
import com.parkking.parkingservice.service.BookingService;
import com.parkking.parkingservice.service.UserService;
import com.parkking.parkingservice.service.core.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking saveBooking(SaveBooking saveBooking) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(saveBooking.getParkingSpotId()).orElseThrow(() -> new RuntimeException("Parking spot not found"));
        bookingRepository.findByParkingSpotId(saveBooking.getParkingSpotId()).forEach(booking -> {
            if(booking.getStartDate().isBefore(saveBooking.getEndDate()) && booking.getEndDate().isAfter(saveBooking.getStartDate())){
                throw new RuntimeException("Parking spot already booked");
            }
        });
        Booking booking = new Booking(saveBooking);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserById(((UserDetailsImpl)authentication.getPrincipal()).getId());
        Driver driver = driverRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Driver not found"));
        booking.setDriver(driver);
        booking.setParkingSpot(parkingSpot);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStartDate(bookingDetails.getStartDate());
        booking.setEndDate(bookingDetails.getEndDate());
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}