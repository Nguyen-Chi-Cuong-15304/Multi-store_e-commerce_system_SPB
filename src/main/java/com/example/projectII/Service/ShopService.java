package com.example.projectII.Service;

import java.lang.foreign.Linker.Option;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectII.DTO.CategoryDTO;
import com.example.projectII.DTO.ShopDTO;
import com.example.projectII.Entity.Shop;
import com.example.projectII.Entity.ShopCategory;
import com.example.projectII.Entity.ShopOwner;
import com.example.projectII.Repository.ShopCategoryRepository;
import com.example.projectII.Repository.ShopOwnerRepository;
import com.example.projectII.Repository.ShopRepository;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopOwnerRepository shopOwnerRepository;

    @Autowired
    private ShopCategoryRepository shopCategoryRepository;

    public Shop createShop(ShopDTO shopDTO) {
        Shop shop = new Shop();
        ShopOwner shopOwner = shopOwnerRepository.findById(shopDTO.getShopOwnerID()).orElse(null);
        shop.setShopName(shopDTO.getShopName());
        shop.setDescription(shopDTO.getDescription());
        shop.setShopOwner(shopOwner);
        shop.setStatus(shopDTO.getStatus());
        shop.setAverageAssess(0);
        if(shopDTO.getBackgroundImage() != null) {
            String image = shopDTO.getBackgroundImage().getOriginalFilename();
            String path = "src/main/resources/static/Image/ShopBG/";
            Path uploadPath = Paths.get(path);
            
            if(!Files.exists(Paths.get(path))) {
                try {
                    Files.createDirectories(Paths.get(path));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String imagePath = System.currentTimeMillis() + '_' + image;
            Path filePath = uploadPath.resolve(imagePath);
            try {
                Files.copy(shopDTO.getBackgroundImage().getInputStream(), filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dbPath = "/Image/ShopBG/" + imagePath;
            shop.setBackgroundImage(dbPath);
        }   

        return shopRepository.save(shop);
    }

    

    public ShopCategory addCategory(CategoryDTO categoryDTO) {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setCategoryName(categoryDTO.getCategoryName());
        shopCategory.setShop(shopRepository.findById(categoryDTO.getShopID()).orElse(null));
        shopCategory.setDescription(categoryDTO.getDescription());
        shopCategoryRepository.save(shopCategory);
        return shopCategory;
    }

    public List<ShopCategory> findAllCategoryByShop(Shop shop) {
        return shopCategoryRepository.findByShop(shop);
    }

    public Optional<Shop> getShopByShopOwner(ShopOwner shopOwner) {
        return shopRepository.findByShopOwner(shopOwner);
    }
}
