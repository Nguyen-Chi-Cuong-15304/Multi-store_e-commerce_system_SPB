package com.example.projectII.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Shop;
import com.example.projectII.Entity.ShopOwner;


@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    
    Optional<Shop> findByShopOwner(ShopOwner shopOwner);

    // Shop findById(int shopID);

    
}