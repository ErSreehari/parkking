package com.parkking.parkingservice.service;

import com.parkking.parkingservice.dto.address.SaveAddress;
import com.parkking.parkingservice.model.Address;

import java.io.IOException;
import java.util.List;

public interface AddressService {

    List<Address> getAllAddresses();

    Address saveAddress(SaveAddress saveAddress) throws IOException;

    Address getAddressById(Long id);

    Address updateAddress(Long id, SaveAddress saveAddress) throws IOException;

    void deleteAddress(Long id);
}