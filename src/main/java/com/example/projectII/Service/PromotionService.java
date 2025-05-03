package com.example.projectII.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectII.DTO.PromotionCateDTO;
import com.example.projectII.DTO.PromotionDTO;
import com.example.projectII.Entity.Product;
import com.example.projectII.Entity.Promotion;
import com.example.projectII.Entity.PromotionForCate;
import com.example.projectII.Entity.Shop;
import com.example.projectII.Repository.PromotionForCateRepository;
import com.example.projectII.Repository.PromotionRepository;
import com.example.projectII.Repository.ShopCategoryRepository;
import com.example.projectII.Repository.ShopRepository;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ShopCategoryRepository shopCategoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionForCateRepository  promotionForCateRepository;

    public void createPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        promotion.setProduct(productService.getProductByID(promotionDTO.getProductID()));
        promotion.setShop(promotion.getProduct().getShop());
        promotion.setDiscount(promotionDTO.getDiscount());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());
        promotionRepository.save(promotion);

        Product product = promotion.getProduct();
        product.setSellCost(product.getCost() * (1 - promotion.getDiscount()));
        productService.updateProduct(product);
    }

    public void applyPromotion(){
        LocalDate today = LocalDate.now();
        List<Promotion> startingPromotion = promotionRepository.findByStartDate(today);
        for(Promotion promotion : startingPromotion){
            Product p = promotion.getProduct();
            p.setSellCost(p.getCost() * (1 - promotion.getDiscount()));
            productService.updateProduct(p);
            promotion.setStatus("active");
            promotionRepository.save(promotion);
        } 
    }

    public void endPromotion(){
        LocalDate today = LocalDate.now();
        List<Promotion> enddingPromotion = promotionRepository.findByEndDate(today);
        for(Promotion promotion : enddingPromotion){
            Product p = promotion.getProduct();
            p.setSellCost(p.getCost());
            productService.updateProduct(p);
            promotion.setStatus("inactive");
            promotionRepository.save(promotion);   
        }
    }

    public List<PromotionDTO> getAllDiscountByShopID_Product(int shopID) {
        Shop shop = shopRepository.findById(shopID).orElse(null);
        if (shop == null) {
            return null; // or throw an exception
        }
        List<Promotion> promotions = promotionRepository.findByShop(shop);
        if (promotions.isEmpty()) {
            return null; // or throw an exception
        }
        List<PromotionDTO> promotionDTOs = new ArrayList<>();
        for (Promotion promotion : promotions) {
            PromotionDTO promotionDTO = new PromotionDTO();
            promotionDTO.setPromotionID(promotion.getPromotionID());
            promotionDTO.setProductID(promotion.getProduct().getProductID());
            promotionDTO.setProductName(promotion.getProduct().getProductName());
            promotionDTO.setDiscount(promotion.getDiscount());
            promotionDTO.setDescription(promotion.getDescription());
            promotionDTO.setStartDate(promotion.getStartDate());
            promotionDTO.setEndDate(promotion.getEndDate());
            promotionDTO.setCost(promotion.getProduct().getCost());
            promotionDTO.setSellCost(promotion.getProduct().getSellCost());
            promotionDTO.setType("PRODUCT");
            promotionDTOs.add(promotionDTO);
        }
        System.out.println("PromotionDTOs size: " + promotionDTOs.size());
        return promotionDTOs;
    }

    public List<PromotionCateDTO> getAllDiscountByShopID_Category(int shopID) {
        Shop shop = shopRepository.findById(shopID).orElse(null);
        if (shop == null) {
            return null; // or throw an exception
        }


        List<PromotionForCate> promotions = promotionForCateRepository.findByShop(shop);
        if (promotions.isEmpty()) {
            return null; // or throw an exception
        }
        List<PromotionCateDTO> promotionDTOs = new ArrayList<>();
        for (PromotionForCate promotion : promotions) {
            PromotionCateDTO promotionDTO = new PromotionCateDTO();
            promotionDTO.setPromotionForCateID(promotion.getPromotionID());
            promotionDTO.setShopID(shopID);
            promotionDTO.setDiscount(promotion.getDiscount());
            promotionDTO.setDescription(promotion.getDescription());
            promotionDTO.setStartDate(promotion.getStartDate());
            promotionDTO.setEndDate(promotion.getEndDate());
            promotionDTO.setType("CATEGORY");
            promotionDTO.setCategoryName(promotion.getShopCategory().getCategoryName());
            promotionDTO.setShopCategoryID(promotion.getShopCategory().getShopCategoryID());
            promotionDTOs.add(promotionDTO);
        }
        System.out.println("PromotionDTOs size: " + promotionDTOs.size());
        return promotionDTOs;
    }


    public PromotionForCate createPromotionForCate(PromotionCateDTO promotionDTO) {
        PromotionForCate promotion = new PromotionForCate();
        promotion.setShopCategory(promotion.getShopCategory());
        promotion.setShop(shopRepository.findById(promotionDTO.getShopID()).orElse(null));
        promotion.setDiscount(promotionDTO.getDiscount());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());
        promotion.setShopCategory(shopCategoryRepository.findById(promotionDTO.getShopCategoryID()).orElse(null));
        return promotionForCateRepository.save(promotion);
    }

    public PromotionDTO getPromotionByID(int promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID).orElse(null);
        if (promotion == null) {
            return null; // or throw an exception
        }
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setPromotionID(promotion.getPromotionID());
        promotionDTO.setProductID(promotion.getProduct().getProductID());
        promotionDTO.setProductName(promotion.getProduct().getProductName());
        promotionDTO.setDiscount(promotion.getDiscount());
        promotionDTO.setDescription(promotion.getDescription());
        promotionDTO.setStartDate(promotion.getStartDate());
        promotionDTO.setEndDate(promotion.getEndDate());
        promotionDTO.setCost(promotion.getProduct().getCost());
        promotionDTO.setSellCost(promotion.getProduct().getSellCost());
        promotionDTO.setType("PRODUCT");
        promotionDTO.setCategoryName(promotion.getProduct().getShopCategory().getCategoryName());
        promotionDTO.setShopCategoryID(promotion.getProduct().getShopCategory().getShopCategoryID());
        return promotionDTO;

    }

    public PromotionDTO updatePromotion(int promotionID, PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepository.findById(promotionID).orElse(null);
        if (promotion == null) {
            return null; // or throw an exception
        }
        promotion.setDiscount(promotionDTO.getDiscount());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());
        promotionRepository.save(promotion);
        return promotionDTO;
    }

    public PromotionCateDTO updatePromotionForCate(int id, PromotionCateDTO promotionDTO) {
        PromotionForCate promotion = promotionForCateRepository.findById(id).orElse(null);
        if (promotion == null) {
            return null; // or throw an exception
        }
        promotion.setDiscount(promotionDTO.getDiscount());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());
        promotionForCateRepository.save(promotion);
        return promotionDTO;
    }

    public PromotionCateDTO getPromotionForCateByID(int promotionID) {
        PromotionForCate promotion = promotionForCateRepository.findById(promotionID).orElse(null);
        if (promotion == null) {
            return null; // or throw an exception
        }
        PromotionCateDTO promotionDTO = new PromotionCateDTO();
        promotionDTO.setPromotionForCateID(promotion.getPromotionID());
        promotionDTO.setShopID(promotion.getShop().getShopID());
        promotionDTO.setDiscount(promotion.getDiscount());
        promotionDTO.setDescription(promotion.getDescription());
        promotionDTO.setStartDate(promotion.getStartDate());
        promotionDTO.setEndDate(promotion.getEndDate());
        promotionDTO.setCategoryName(promotion.getShopCategory().getCategoryName());
        promotionDTO.setShopCategoryID(promotion.getShopCategory().getShopCategoryID());
        promotionDTO.setType("CATEGORY");
        return promotionDTO;
    }
}
