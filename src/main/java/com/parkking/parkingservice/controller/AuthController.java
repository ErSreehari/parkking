package com.parkking.parkingservice.controller;

import com.parkking.parkingservice.dto.auth.LoginRequest;
import com.parkking.parkingservice.dto.auth.TokenRefreshRequest;
import com.parkking.parkingservice.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  AuthService authService;

  @GetMapping("/sendOtp/phoneNo/{phoneNo}/role/{userRole}")
  public ResponseEntity<?> sendOTP(@Valid @PathVariable String phoneNo, @PathVariable String userRole) {
    return ResponseEntity.ok(authService.sendOtp(phoneNo, userRole));
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.authenticateUser(loginRequest));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request));
  }

}