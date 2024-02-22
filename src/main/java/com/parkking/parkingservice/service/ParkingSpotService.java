package com.parkking.parkingservice.service;

import com.parkking.parkingservice.dto.parkingSpot.SaveParkingSpot;
import com.parkking.parkingservice.dto.parkingSpot.UpdateParkingSpot;
import com.parkking.parkingservice.model.ParkingSpot;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ParkingSpotService {
    List<ParkingSpot> getAllParkingSpots();

    ParkingSpot saveParkingSpot(SaveParkingSpot parkingSpot) throws IOException;

    ParkingSpot getParkingSpotById(Long id);

    ParkingSpot updateParkingSpot(UpdateParkingSpot updateParkingSpot);

    Map<String, Boolean> deleteParkingSpot(Long id);

    List<ParkingSpot> getNearByParkingSpots(Double latitude, Double longitude) throws IOException;
}
