package com.parkking.parkingservice.repository;

import com.parkking.parkingservice.model.user.SpotOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotOwnerRepository extends JpaRepository<SpotOwner, Long> {
}