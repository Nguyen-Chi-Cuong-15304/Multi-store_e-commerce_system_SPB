package com.example.projectII.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Product;
import com.example.projectII.Entity.Shop;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByShop(Shop shop);

    List<Product> findTop6ByOrderByPurchaseNumberDesc();

    //Lấy danh sách top 10 sản phẩm giảm giá nhiều nhất (sản phẩm không có thuộc tính discount mà phải tính toán từ giá gốc và giá khuyến mãi)
    @Query("SELECT p FROM Product p WHERE p.sellCost > 0 Order by (p.cost - p.sellCost)/p.cost desc LIMIT 10")
    List<Product> findTop10ByOrderByDiscountDesc(); //Lấy danh sách top 10 sản phẩm giảm giá nhiều nhất (sản phẩm không có thuộc tính discount mà phải tính toán từ giá gốc và giá khuyến mãi)
    
}
