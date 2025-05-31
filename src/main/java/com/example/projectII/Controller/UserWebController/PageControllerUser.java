package com.example.projectII.Controller.UserWebController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.Product;
import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Service.ProductService;
import com.example.projectII.Service.ShopService;

import org.springframework.ui.Model;

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
    @Autowired
    private BuyerRepository buyerRepository;

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
    public String getCartPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            Buyer buyer = buyerRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            int buyerID = buyer.getBuyerID();
            model.addAttribute("username", username);
            model.addAttribute("buyerID", buyerID);
            System.out.println("User is authenticated: " + username);
        } else {
            model.addAttribute("username", "Guest");
        }
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
                                            @RequestParam(value = "sort", required = false) String sort,
                                            @RequestParam(value = "categoryID", required = false) int categoryID) {
        try {
            PageRequest pageable = PageRequest.of(page, size);
            if (sort != null && !sort.isEmpty()) {
                String[] sortParams = sort.split(",");
                Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
            }
            Page<ProductDTO> productPage = productService.getAllProductsByShopId(shopId, pageable, categoryID);

            Map<String, Object> response = new HashMap<>();
            response.put("content", productPage.getContent());
            response.put("totalPages", productPage.getTotalPages());
            response.put("totalElements", productPage.getTotalElements());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching all products: " + e.getMessage());
        }
    }
    
    @GetMapping("/category/{shopId}")
    public ResponseEntity<?> getCategory(@PathVariable("shopId") int shopId) {
        try {
            return ResponseEntity.ok(shopService.getCategoryByShopId(shopId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching categories: " + e.getMessage());
        }
    }
    
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") int productId) {
        try {
            ProductDTO product = productService.getProductDTOByID(productId);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(404).body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching product: " + e.getMessage());
        }
    }

    @GetMapping("/related-products/{productID}")
    public ResponseEntity<?> getRelatedProducts(@PathVariable("productID") int productID) {
        try {
            return ResponseEntity.ok(productService.getRelatedProducts(productID));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching related products: " + e.getMessage());
        }
    }

    @GetMapping("/products/suggestions")
    public ResponseEntity<?> getProductSuggestions(@RequestParam("query") String query) {
        try {
            return ResponseEntity.ok(productService.getProductSuggestions(query));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching product suggestions: " + e.getMessage());
        }
    }
}
