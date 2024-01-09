package com.parkking.parkingservice.repository;

import com.parkking.parkingservice.model.auth.ERole;
import com.parkking.parkingservice.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}