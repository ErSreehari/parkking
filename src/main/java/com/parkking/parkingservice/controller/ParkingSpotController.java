package com.parkking.parkingservice.controller;

import com.parkking.parkingservice.dto.parkingSpot.SaveParkingSpot;
import com.parkking.parkingservice.model.ParkingSpot;
import com.parkking.parkingservice.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/parkingspots")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ParkingSpot> getAllParkingSpots(){
        return parkingSpotService.getAllParkingSpots();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ParkingSpot createParkingSpot(@RequestBody SaveParkingSpot parkingSpotDto) throws IOException {
        return parkingSpotService.saveParkingSpot(parkingSpotDto);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpotService.getParkingSpotById(id));
    }

    @GetMapping("/lat/{latitude}/long/{longitude}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ParkingSpot>> getNearByParkingSpots(@PathVariable Double latitude, @PathVariable Double longitude) throws IOException {
        return ResponseEntity.ok(parkingSpotService.getNearByParkingSpots(latitude, longitude));
    }
    @PutMapping
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParkingSpot> updateParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        return ResponseEntity.ok(parkingSpotService.updateParkingSpot(parkingSpot));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Boolean>> deleteStore(@PathVariable Long id){
        return ResponseEntity.ok(parkingSpotService.deleteParkingSpot(id));
    }
}


