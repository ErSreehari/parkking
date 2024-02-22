package com.parkking.parkingservice.dto.parkingSpot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParkingSpot {

    private Long id;
    private Long addressId;
    private String spotType;

}
