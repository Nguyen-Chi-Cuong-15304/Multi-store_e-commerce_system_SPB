package com.example.projectII.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Shipper;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
    Optional<Shipper> findByUsername(String username);
}

