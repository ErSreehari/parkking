package com.parkking.parkingservice.service.impl;

import com.parkking.parkingservice.dto.parkingSpot.SaveParkingSpot;
import com.parkking.parkingservice.dto.parkingSpot.UpdateParkingSpot;
import com.parkking.parkingservice.model.Address;
import com.parkking.parkingservice.model.ParkingSpot;
import com.parkking.parkingservice.model.user.SpotOwner;
import com.parkking.parkingservice.repository.AddressRepository;
import com.parkking.parkingservice.repository.ParkingSpotRepository;
import com.parkking.parkingservice.repository.SpotOwnerRepository;
import com.parkking.parkingservice.repository.UserRepository;
import com.parkking.parkingservice.service.ParkingSpotService;
import com.uber.h3core.H3Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SpotOwnerRepository spotOwnerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }

    @Override
    public ParkingSpot saveParkingSpot(SaveParkingSpot parkingSpotDto) throws IOException {
        H3Core h3 = H3Core.newInstance();
        ParkingSpot parkingSpot = new ParkingSpot(parkingSpotDto);
        Address address = addressRepository.findById(parkingSpotDto.getAddressId()).orElseThrow(() -> new RuntimeException("Address not found"));
        String hexCode = h3.latLngToCellAddress(address.getLatitude(), address.getLongitude(), 9);
        address.setHexCode(hexCode);
        addressRepository.save(address);
        SpotOwner owner = new SpotOwner("dummy", userRepository.findById(parkingSpotDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        SpotOwner spotOwner = spotOwnerRepository.save(owner);
        parkingSpot.setAddress(address);
        parkingSpot.setSpotOwner(spotOwner);
        return parkingSpotRepository.save(parkingSpot);
    }

    @Override
    public ParkingSpot getParkingSpotById(Long id) {
        return parkingSpotRepository.findById(id).orElse(null);
    }

    @Override
    public ParkingSpot updateParkingSpot(UpdateParkingSpot updateParkingSpot) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(updateParkingSpot.getId()).orElseThrow(() -> new RuntimeException("Parking spot not found"));
        parkingSpot.setSpotType(updateParkingSpot.getSpotType());
        parkingSpot.setAddress(addressRepository.findById(updateParkingSpot.getAddressId()).orElseThrow(() -> new RuntimeException("Address not found")));
        return parkingSpotRepository.save(parkingSpot);
    }

    @Override
    public Map<String, Boolean> deleteParkingSpot(Long id) {
        parkingSpotRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Override
    public List<ParkingSpot> getNearByParkingSpots(Double latitude, Double longitude) throws IOException {
        H3Core h3 = H3Core.newInstance();
        String currHexCode = h3.latLngToCellAddress(latitude, longitude, 9);
        List<String> neighbourHexCodes = h3.gridDisk(currHexCode, 1);
        return parkingSpotRepository.findByAddress_HexCodeIn(neighbourHexCodes);
    }

}