package com.example.projectII.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Buyer;


@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
    Optional<Buyer> findByUsername(String username);
    Optional<Buyer> findByBuyerID(int buyerID);
}
