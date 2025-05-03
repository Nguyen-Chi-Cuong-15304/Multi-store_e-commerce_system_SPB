package com.example.projectII.Controller.UserWebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectII.Entity.Product;
import com.example.projectII.Service.ProductService;
import com.example.projectII.Service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/api")
public class PageControllerUser {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;

    @GetMapping("/featured-products")
    public ResponseEntity<?> getFeaturedProducts() {
        try {
            return ResponseEntity.ok(productService.getFeaturedProducts());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching featured products: " + e.getMessage());
        }
        
    }

    // Lấy danh sách featured store
    @GetMapping("/featured-stores")
    public ResponseEntity<?> getFeaturedStores() {
        try {
            return ResponseEntity.ok(shopService.getFeaturedStores());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching featured stores: " + e.getMessage());
        }
    }

    @GetMapping("/best-selling-products")
    public ResponseEntity<?> getBestSellingProducts() {
        try {
            return ResponseEntity.ok(productService.getBestSellingProducts());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching best-selling products: " + e.getMessage());
        }
    }

    @GetMapping("/discount-products")
    public ResponseEntity<?> getDiscountProducts() {
        try {
            return ResponseEntity.ok(productService.getDiscountProducts());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching discount products: " + e.getMessage());
        }
    }
    
    
}
