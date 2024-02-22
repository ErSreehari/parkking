package com.parkking.parkingservice.dto.user;

import com.parkking.parkingservice.model.Address;
import com.parkking.parkingservice.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDriver {

    private String licenseNo;

}
