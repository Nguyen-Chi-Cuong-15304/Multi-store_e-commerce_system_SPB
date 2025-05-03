package com.example.projectII.Controller.ProductController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectII.Entity.Shop;
import com.example.projectII.Repository.ShopRepository;
import com.example.projectII.Service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/getproductbyshopid/{shopID}")
    public ResponseEntity<?> getProductByShopID(@PathVariable int shopID) {
        try{
            // return ResponseEntity.ok(productService.getProductByShopID(shopID));
            Shop shop = shopRepository.findById(shopID).orElse(null);
            if (shop == null) {
                return ResponseEntity.badRequest().body("Shop not found");
            }
            System.out.println("Shop not null " + shop.getShopName());
            return ResponseEntity.ok().body(productService.getProductByShopID(shopID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot get product by shop ID");
        }
    }

    @GetMapping("/getproductbyid/{productID}")
    public ResponseEntity<?> getProductByID(@PathVariable int productID) {
        try{
            return ResponseEntity.ok(productService.getProductByID(productID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot get product by ID");
        }
    }
    
}
