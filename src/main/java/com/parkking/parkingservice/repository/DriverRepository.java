package com.parkking.parkingservice.repository;

import com.parkking.parkingservice.model.user.Driver;
import com.parkking.parkingservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByUser(User user);

}