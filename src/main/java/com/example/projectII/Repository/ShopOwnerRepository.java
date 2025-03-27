package com.example.projectII.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.ShopOwner;

@Repository
public interface ShopOwnerRepository extends JpaRepository<ShopOwner, Integer> {
    Optional<ShopOwner> findByUsername(String username);
}
