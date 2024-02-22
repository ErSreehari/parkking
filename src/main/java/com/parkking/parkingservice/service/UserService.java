package com.parkking.parkingservice.service;

import com.parkking.parkingservice.dto.auth.SignUpRequest;
import com.parkking.parkingservice.dto.user.UpdateDriver;
import com.parkking.parkingservice.dto.user.UpdateSpotOwner;
import com.parkking.parkingservice.model.user.Driver;
import com.parkking.parkingservice.model.user.SpotOwner;
import com.parkking.parkingservice.model.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User updateUser(SignUpRequest signUpRequest);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User saveUser(User user);

    Map<String, Boolean> deleteUser(Long userId);

    SpotOwner registerAsSpotOwner(String licenseNo);

    Driver registerAsDriver(String licenseNo);

    Driver updateDriver(UpdateDriver updateDriverDto);

    SpotOwner updateSpotOwner(UpdateSpotOwner updateSpotOwnerDto);
}
