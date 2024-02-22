package com.parkking.parkingservice.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAddress {

    private String address;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    private String landmark;
    private String pinCode;
    private String name;

}