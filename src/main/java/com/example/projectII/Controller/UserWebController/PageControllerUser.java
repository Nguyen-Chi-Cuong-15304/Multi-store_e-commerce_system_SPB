package com.example.projectII.Controller.UserWebController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.Entity.Product;
import com.example.projectII.Service.ProductService;
import com.example.projectII.Service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/cart")
    public String getCartPage() {
        return "shoppingcartv2"; // Trả về tên trang cart.html
    }

    @GetMapping("/new-products/{shopId}")
    public ResponseEntity<?> getNewProducts(@PathVariable("shopId") int shopId) {
        try {
            return ResponseEntity.ok(productService.getNewProductsByShopId(shopId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching new products: " + e.getMessage());
        }
    }

    @GetMapping("/discount-products/{shopId}")
    public ResponseEntity<?> getDiscountProducts(@PathVariable("shopId") int shopId) {
        try {
            return ResponseEntity.ok(productService.getDiscountProductsByShopId(shopId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching discount products: " + e.getMessage());
        }
    }
   
    @GetMapping("/all-products/{shopId}")
    public ResponseEntity<?> getAllProducts(@PathVariable("shopId") int shopId, 
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "2") int size,
                                            @RequestParam(value = "sort", required = false) String sort) {
        try {
            PageRequest pageable = PageRequest.of(page, size);
            if (sort != null && !sort.isEmpty()) {
                String[] sortParams = sort.split(",");
                Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
            }
            Page<ProductDTO> productPage = productService.getAllProductsByShopId(shopId, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("content", productPage.getContent());
            response.put("totalPages", productPage.getTotalPages());
            response.put("totalElements", productPage.getTotalElements());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching all products: " + e.getMessage());
        }
    }
    
    
    
}
