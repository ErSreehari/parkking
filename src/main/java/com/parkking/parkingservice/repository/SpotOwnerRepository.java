package com.parkking.parkingservice.repository;

import com.parkking.parkingservice.model.user.SpotOwner;
import com.parkking.parkingservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpotOwnerRepository extends JpaRepository<SpotOwner, Long> {

    Optional<SpotOwner> findByUser(User user);
}