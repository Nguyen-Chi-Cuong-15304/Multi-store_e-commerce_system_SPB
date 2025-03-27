package com.example.projectII.Controller.ShopOwnerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectII.DTO.CategoryDTO;
import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.DTO.PromotionDTO;
import com.example.projectII.DTO.ShopDTO;
import com.example.projectII.Entity.Product;
import com.example.projectII.Entity.Shop;
import com.example.projectII.Entity.ShopCategory;
import com.example.projectII.Entity.ShopOwner;
import com.example.projectII.Repository.ShopOwnerRepository;
import com.example.projectII.Repository.ShopRepository;
import com.example.projectII.Service.ProductService;
import com.example.projectII.Service.PromotionService;
import com.example.projectII.Service.ShopService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/shopowner")
public class pageShopOwnerController {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopOwnerRepository shopOwnerRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionService promotionService;
    
    
    @GetMapping("/login")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            ShopOwner shopOwner = shopOwnerRepository.findByUsername(authentication.getName()).orElse(null);
            Shop shop = shopRepository.findByShopOwner(shopOwner).orElse(null);
            if (shop != null) {
                return "ShopOwnerView/shopOwnerDashboard";
            }
            return "redirect:/shopowner/createShop";
        }
        return "redirect:/all/login";
    }

    @GetMapping("/dashboard")
    public String shopOwnerDashboard() {
        return "ShopOwnerView/shopOwnerDashboard";
    }

    @GetMapping("/createShop")
    public String registerShopPage() {
        return "ShopOwnerView/createShop";
    }

    @PostMapping("/create-shop")
    public ResponseEntity<?> createShop(@ModelAttribute ShopDTO shopDTO) {
        try {
            Shop shop = shopService.createShop(shopDTO);
            return ResponseEntity.ok().body("{\"message\": \"Create shop successfully\", \"shopID\": " + shop.getShopID() + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/productlist")
    public String productListPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ShopOwner shopOwner = shopOwnerRepository.findByUsername(authentication.getName()).orElse(null);
        Shop shop = shopService.getShopByShopOwner(shopOwner).orElse(null);
        model.addAttribute("shopID", shop.getShopID());
        return "ShopOwnerView/listProduct";
    }

    @PostMapping("/addcategory")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            ShopCategory category = shopService.addCategory(categoryDTO);
            return ResponseEntity.ok().body("{\"message\": \"Add category successfully\", \"categoryID\": " + category.getShopCategoryID() + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/getcategory")
    public ResponseEntity<?> getCategory() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            ShopOwner shopOwner = shopOwnerRepository.findByUsername(authentication.getName()).orElse(null);
            Shop shop = shopService.getShopByShopOwner(shopOwner).orElse(null);
            return ResponseEntity.ok().body(shopService.findAllCategoryByShop(shop));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/addproduct")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDTO productDTO) {
        try {
            Product product = productService.createProduct(productDTO);
            return ResponseEntity.ok().body("{\"message\": \"Add product successfully\", \"productID\": " + product.getProductID() + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/editproduct")
    public ResponseEntity<?> editProduct(@ModelAttribute ProductDTO productDTO) {
        try {
            productService.editProduct(productDTO);
            return ResponseEntity.ok().body("{\"message\": \"Edit product successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot edit product");
        }
    }
    
    @GetMapping("/promotion")
    public String promotionPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ShopOwner shopOwner = shopOwnerRepository.findByUsername(authentication.getName()).orElse(null);
        Shop shop = shopService.getShopByShopOwner(shopOwner).orElse(null);
        model.addAttribute("shopID", shop.getShopID());
        return "ShopOwnerView/listPromotion";
    }

    @PostMapping("/addpromotion")
    public ResponseEntity<?> addPromotion(@ModelAttribute PromotionDTO promotionDTO) {
        try {
            promotionService.createPromotion(promotionDTO);
            return ResponseEntity.ok().body("{\"message\": \"Add promotion successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    
    
    
}