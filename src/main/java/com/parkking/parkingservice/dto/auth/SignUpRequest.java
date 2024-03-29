package com.parkking.parkingservice.dto.auth;

import com.parkking.parkingservice.model.Address;
import com.parkking.parkingservice.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String mobileNo;
    private String email;
    private String name;
    private Set<Address> addresses;

}
