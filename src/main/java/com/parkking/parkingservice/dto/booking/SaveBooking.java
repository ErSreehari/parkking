package com.parkking.parkingservice.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveBooking {

    private Long id;
    private Long parkingSpotId;
    private Instant startDate;
    private Instant endDate;

}
