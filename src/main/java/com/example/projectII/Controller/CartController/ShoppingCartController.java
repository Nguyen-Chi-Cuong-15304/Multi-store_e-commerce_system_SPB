package com.example.projectII.Controller.CartController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectII.DTO.CartItemDTO;
import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.Service.ProductService;
import com.example.projectII.Service.ShoppingCartService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add_to_cart")
    public ResponseEntity<?> addToCart(@ModelAttribute CartItemDTO cartItemDTO) {
        try {
            System.out.println("Adding product to cart: " + cartItemDTO.getProductID() + ", quantity: " + cartItemDTO.getQuantity());
            shoppingCartService.addToCart(cartItemDTO);
            return ResponseEntity.ok("{\"message\": \"Product added to cart successfully\"}");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/get_cart")
    public ResponseEntity<?> getCart() {
        try {
            System.out.println("Getting cart items");
            return ResponseEntity.ok(shoppingCartService.getCartItems());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    
}
