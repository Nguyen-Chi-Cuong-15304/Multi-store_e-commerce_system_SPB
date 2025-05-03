package com.example.projectII.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Promotion;
import com.example.projectII.Entity.Shop;

import java.util.List;
import java.time.LocalDate;


@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    
    List<Promotion> findByStartDate(LocalDate startDate);

    List<Promotion> findByEndDate(LocalDate endDate);

    List<Promotion> findByShop(Shop shop);
    
}
