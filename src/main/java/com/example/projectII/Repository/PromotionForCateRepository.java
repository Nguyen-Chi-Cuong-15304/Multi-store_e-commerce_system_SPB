package com.example.projectII.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.PromotionForCate;
import com.example.projectII.Entity.Shop;

@Repository
public interface PromotionForCateRepository extends JpaRepository<PromotionForCate, Integer> {
    
    List<PromotionForCate> findByShop(Shop shop);
}
