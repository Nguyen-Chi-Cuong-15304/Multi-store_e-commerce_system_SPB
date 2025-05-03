package com.example.projectII.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Shop;
import com.example.projectII.Entity.ShopCategory;

import java.util.List;



@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Integer> {
    List <ShopCategory> findByShop(Shop shop);
}
