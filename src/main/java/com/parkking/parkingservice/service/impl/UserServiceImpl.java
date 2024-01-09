package com.parkking.parkingservice.service.impl;

import com.parkking.parkingservice.dto.auth.SignUpRequest;
import com.parkking.parkingservice.exception.ResourceNotFoundException;
import com.parkking.parkingservice.model.user.User;
import com.parkking.parkingservice.repository.AddressRepository;
import com.parkking.parkingservice.repository.UserRepository;
import com.parkking.parkingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public User updateUser(SignUpRequest signUpRequest) {
        User user = userRepository.findByMobileNo(signUpRequest.getMobileNo()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        if(StringUtils.hasText(signUpRequest.getEmail())) user.setEmail(signUpRequest.getEmail());
        if(StringUtils.hasText(signUpRequest.getName())) user.setName(signUpRequest.getName());
        signUpRequest.getAddresses().forEach(address -> {
            address.setUser(user);
            addressRepository.save(address);
        });
        user.setUserType(signUpRequest.getUserType());
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

}
