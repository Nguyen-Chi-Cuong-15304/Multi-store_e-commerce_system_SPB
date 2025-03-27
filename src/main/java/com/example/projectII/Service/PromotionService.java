package com.example.projectII.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectII.DTO.PromotionDTO;
import com.example.projectII.Entity.Product;
import com.example.projectII.Entity.Promotion;
import com.example.projectII.Repository.PromotionRepository;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProductService productService;

    public void createPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        promotion.setProduct(productService.getProductByID(promotionDTO.getProductID()));
        promotion.setShop(promotion.getProduct().getShop());
        promotion.setDiscount(promotionDTO.getDiscount());
        promotion.setDescription(promotionDTO.getDescription());
        promotionRepository.save(promotion);

        Product product = promotion.getProduct();
        product.setSellCost(product.getCost() * (1 - promotion.getDiscount()));
        productService.updateProduct(product);
    }
}
