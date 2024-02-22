package com.parkking.parkingservice.service.impl;

import com.parkking.parkingservice.dto.auth.SignUpRequest;
import com.parkking.parkingservice.dto.user.UpdateDriver;
import com.parkking.parkingservice.dto.user.UpdateSpotOwner;
import com.parkking.parkingservice.exception.ResourceNotFoundException;
import com.parkking.parkingservice.model.user.Driver;
import com.parkking.parkingservice.model.user.SpotOwner;
import com.parkking.parkingservice.model.user.User;
import com.parkking.parkingservice.repository.AddressRepository;
import com.parkking.parkingservice.repository.DriverRepository;
import com.parkking.parkingservice.repository.SpotOwnerRepository;
import com.parkking.parkingservice.repository.UserRepository;
import com.parkking.parkingservice.service.UserService;
import com.parkking.parkingservice.service.core.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SpotOwnerRepository spotOwnerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public User updateUser(SignUpRequest signUpRequest) {
        User user = userRepository.findByMobileNo(signUpRequest.getMobileNo()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        if(StringUtils.hasText(signUpRequest.getEmail())) user.setEmail(signUpRequest.getEmail());
        if(StringUtils.hasText(signUpRequest.getName())) user.setName(signUpRequest.getName());
        if (signUpRequest.getAddresses() != null && !signUpRequest.getAddresses().isEmpty()) {
            signUpRequest.getAddresses().forEach(address -> {
                address.setUser(user);
                addressRepository.save(address);
            });
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Map<String, Boolean> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + userId));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Override
    public SpotOwner registerAsSpotOwner(String licenseNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserById(((UserDetailsImpl)authentication.getPrincipal()).getId());
        SpotOwner spotOwner = new SpotOwner(licenseNo, user);
        return spotOwnerRepository.save(spotOwner);
    }

    @Override
    public Driver registerAsDriver(String licenceNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserById(((UserDetailsImpl)authentication.getPrincipal()).getId());
        Driver driver = new Driver(licenceNo, user);
        return driverRepository.save(driver);
    }

    @Override
    public Driver updateDriver(UpdateDriver updateDriverDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserById(((UserDetailsImpl)authentication.getPrincipal()).getId());
        Driver driver = driverRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Driver not found"));
        driver.setLicenseNo(updateDriverDto.getLicenseNo());
        return driverRepository.save(driver);
    }

    @Override
    public SpotOwner updateSpotOwner(UpdateSpotOwner updateSpotOwnerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserById(((UserDetailsImpl)authentication.getPrincipal()).getId());
        SpotOwner spotOwner = spotOwnerRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("SpotOwner not found"));
        spotOwner.setLicenseNo(updateSpotOwnerDto.getLicenseNo());
        return spotOwnerRepository.save(spotOwner);
    }

}
