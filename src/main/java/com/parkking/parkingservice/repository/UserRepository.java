package com.parkking.parkingservice.repository;

import com.parkking.parkingservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobileNo(String mobileNo);

    Boolean existsByMobileNo(String mobileNo);

    Boolean existsByEmail(String email);
}