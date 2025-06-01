package com.example.projectII.Controller.PageController;

import java.security.Principal;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectII.Entity.Buyer;
import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Service.RegisterService;
import com.example.projectII.Service.ShopService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/all")
public class PageController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private BuyerRepository buyerRepository;

    @GetMapping("/")
    public String home() {
        return "homepage";
    }
    @GetMapping("/register")
    public String registerPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "message", required = false) String message,
                               Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "register";
    }
    @PostMapping("/perform_register")
    public String performRegister(@RequestParam("fullname") String fullName,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam("role") String role, Model model) {
        try {
            registerService.register(fullName, username, password, role);
            return "redirect:/all/login?message=Registration successful, please login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/all/login?logout=true";
    }
    
    @GetMapping("/view_shop/{shopId}")
    public String viewShop(@PathVariable("shopId") int shopId, Model model) {
        // Fetch shop details using the shopId and add to the model
        // Example: ShopDTO shop = shopService.getShopById(shopId);
        // model.addAttribute("shop", shop);
        model.addAttribute("shopId", shopId);
        System.out.println("Shop ID: " + shopId);
        return "shop"; // Return the name of the view for displaying shop details
    }

    @GetMapping("/get_shop_info/{shopId}")
    public ResponseEntity<?> getShopInfo(@PathVariable("shopId") int shopId) {
        try {
            return ResponseEntity.ok(shopService.getShopById(shopId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching shop information: " + e.getMessage());
        }
    }
    
    @GetMapping("/view_product/{productId}")
    public String viewProduct(@PathVariable("productId") int productId, Model model) {
        // Fetch product details using the productId and add to the model
        // Example: ProductDTO product = productService.getProductById(productId);
        // model.addAttribute("product", product);
        model.addAttribute("productID", productId);
        System.out.println("Product ID: " + productId);
        return "product"; // Return the name of the view for displaying product details
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Buyer user = buyerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));
        int userID = user.getBuyerID();
        model.addAttribute("username", username);
        model.addAttribute("buyerID", userID);
        return "order"; // Return the name of the view for displaying orders
    }


    
}
