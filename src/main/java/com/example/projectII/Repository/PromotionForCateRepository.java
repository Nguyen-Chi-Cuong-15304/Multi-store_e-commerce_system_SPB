package com.example.projectII.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectII.Entity.PromotionForCate;
import com.example.projectII.Entity.Shop;

public interface PromotionForCateRepository extends JpaRepository<PromotionForCate, Integer> {
    
    List<PromotionForCate> findByShop(Shop shop);
}
