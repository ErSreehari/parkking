package com.parkking.parkingservice.service;



import com.parkking.parkingservice.dto.auth.JwtResponse;
import com.parkking.parkingservice.dto.auth.LoginRequest;
import com.parkking.parkingservice.dto.auth.TokenRefreshRequest;
import com.parkking.parkingservice.dto.auth.TokenRefreshResponse;

public interface AuthService {

    JwtResponse authenticateUser(LoginRequest loginRequest);

    TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);

    String sendOtp(String phoneNo, String role);

}
