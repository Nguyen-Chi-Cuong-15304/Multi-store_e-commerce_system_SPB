package com.example.projectII.Controller.ProductController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.Service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/getproductbyshopid/{shopID}")
    public ResponseEntity<?> getProductByShopID(@PathVariable int shopID) {
        try{
            return ResponseEntity.ok(productService.getProductByShopID(shopID));
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
