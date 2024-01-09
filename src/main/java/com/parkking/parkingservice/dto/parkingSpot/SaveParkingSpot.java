package com.parkking.parkingservice.dto.parkingSpot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveParkingSpot {

    private Long addressId;
    private Long userId;
    private String spotType;

}
