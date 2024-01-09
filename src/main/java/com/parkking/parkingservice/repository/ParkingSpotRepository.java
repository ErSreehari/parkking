package com.parkking.parkingservice.repository;

import com.parkking.parkingservice.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    List<ParkingSpot> findByAddress_HexCodeIn(List<String> hexCode);
    List<ParkingSpot> findByAddress_HexCode(String hexCode);
}