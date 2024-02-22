package com.parkking.parkingservice.repository;

import com.parkking.parkingservice.model.Booking;
import com.parkking.parkingservice.model.ParkingSpot;
import com.parkking.parkingservice.model.user.Driver;
import com.parkking.parkingservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByParkingSpotId(Long parkingSpotId);
}