package com.example.projectII.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    Optional<ShoppingCart> findByBuyer(Buyer buyer);
    Optional<ShoppingCart> findById(int id);
    void deleteById(int id);
}
