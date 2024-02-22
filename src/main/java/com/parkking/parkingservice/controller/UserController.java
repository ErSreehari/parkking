package com.parkking.parkingservice.controller;

import com.parkking.parkingservice.dto.auth.SignUpRequest;
import com.parkking.parkingservice.dto.user.UpdateDriver;
import com.parkking.parkingservice.dto.user.UpdateSpotOwner;
import com.parkking.parkingservice.model.user.Driver;
import com.parkking.parkingservice.model.user.SpotOwner;
import com.parkking.parkingservice.model.user.User;
import com.parkking.parkingservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public User updateUser(@RequestBody SignUpRequest signUpRequest) throws IOException {
        return userService.updateUser(signUpRequest);
    }
    @GetMapping("/register/spotOwner/{licenseNo}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<SpotOwner> registerAsSpotOwner(@PathVariable String licenseNo) {
        return ResponseEntity.ok(userService.registerAsSpotOwner(licenseNo));
    }

    @GetMapping("/register/driver/{licenseNo}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Driver> registerAsDriver(@PathVariable String licenseNo) {
        return ResponseEntity.ok(userService.registerAsDriver(licenseNo));
    }

    @PutMapping("/update/driver")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Driver> updateDriver(@RequestBody UpdateDriver updateDriverDto) {
        return ResponseEntity.ok(userService.updateDriver(updateDriverDto));
    }
    @PutMapping("/update/spotOwner")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<SpotOwner> updateSpotOwner(@RequestBody UpdateSpotOwner updateSpotOwnerDto) {
        return ResponseEntity.ok(userService.updateSpotOwner(updateSpotOwnerDto));
    }
}


