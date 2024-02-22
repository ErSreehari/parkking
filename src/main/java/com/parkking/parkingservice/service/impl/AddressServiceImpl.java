package com.parkking.parkingservice.service.impl;

import com.parkking.parkingservice.dto.address.SaveAddress;
import com.parkking.parkingservice.model.Address;
import com.parkking.parkingservice.model.user.User;
import com.parkking.parkingservice.repository.AddressRepository;
import com.parkking.parkingservice.service.AddressService;
import com.parkking.parkingservice.service.UserService;
import com.parkking.parkingservice.service.core.UserDetailsImpl;
import com.uber.h3core.H3Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address saveAddress(SaveAddress saveAddress) throws IOException {
        Address address = new Address(saveAddress);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserById(((UserDetailsImpl)authentication.getPrincipal()).getId());
        address.setUser(user);
        H3Core h3 = H3Core.newInstance();
        address.setHexCode(h3.latLngToCellAddress(address.getLatitude(), address.getLongitude(), 9));
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
    }

    @Override
    public Address updateAddress(Long id, SaveAddress saveAddress) throws IOException {
        H3Core h3 = H3Core.newInstance();
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        address.setAddress(saveAddress.getAddress());
        address.setCity(saveAddress.getCity());
        address.setState(saveAddress.getState());
        address.setLatitude(saveAddress.getLatitude());
        address.setLongitude(saveAddress.getLongitude());
        address.setHexCode(h3.latLngToCellAddress(address.getLatitude(), address.getLongitude(), 9));
        address.setLandmark(saveAddress.getLandmark());
        address.setPinCode(saveAddress.getPinCode());
        address.setName(saveAddress.getName());
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}