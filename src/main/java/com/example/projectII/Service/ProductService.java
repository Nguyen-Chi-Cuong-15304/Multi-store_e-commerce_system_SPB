package com.example.projectII.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.Entity.Product;
import com.example.projectII.Repository.ProductRepository;
import com.example.projectII.Repository.ShopCategoryRepository;
import com.example.projectII.Repository.ShopRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopCategoryRepository shopCategoryRepository;

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setCost(productDTO.getCost());
        product.setSellCost(productDTO.getSellCost());
        product.setDescription(productDTO.getDescription());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        product.setViewCount(productDTO.getViewCount());
        product.setInputPrice(productDTO.getInputCost());
        product.setShop(shopRepository.findById(productDTO.getShopID()).orElse(null));
        product.setShopCategory(shopCategoryRepository.findById(productDTO.getCategoryID()).orElse(null));
        if(productDTO.getImage() != null) {
            String image = productDTO.getImage().getOriginalFilename();
            String path = "src/main/resources/static/Image/ProductIMG/";
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
                Files.copy(productDTO.getImage().getInputStream(), filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dbPath = "/Image/ProductIMG/" + imagePath;
            product.setImage(dbPath);
        }   

        return productRepository.save(product);
    }

    public List<Product> getProductByShopID(int shopID) {
        return productRepository.findByShop(shopRepository.findById(shopID).orElse(null));
    }

    public void editProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getProductID()).orElse(null);
        if(product != null) {
            String oldImage = product.getImage();
            product.setProductName(productDTO.getProductName());
            product.setCost(productDTO.getCost());
            product.setSellCost(productDTO.getSellCost());
            product.setDescription(productDTO.getDescription());
            product.setQuantityInStock(productDTO.getQuantityInStock());
            product.setViewCount(productDTO.getViewCount());
            product.setInputPrice(productDTO.getInputCost());
            product.setShop(shopRepository.findById(productDTO.getShopID()).orElse(null));
            product.setShopCategory(shopCategoryRepository.findById(productDTO.getCategoryID()).orElse(null));

            if(productDTO.getStatus() != null) {
                product.setStatus(productDTO.getStatus());
            }

            if(productDTO.getImage() != null && !productDTO.getImage().isEmpty()) {
                String image = productDTO.getImage().getOriginalFilename();
                String path = "src/main/resources/static/Image/ProductIMG/";
                Path uploadPath = Paths.get(path);

                if(oldImage != null && !oldImage.isEmpty()) {
                    try {
                        Files.deleteIfExists(Paths.get("src/main/resources/static" + oldImage));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
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
                    Files.copy(productDTO.getImage().getInputStream(), filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String dbPath = "/Image/ProductIMG/" + imagePath;
                product.setImage(dbPath);
            }   
            productRepository.save(product);
        }
    }

    public Product getProductByID(int productID) {
        return productRepository.findById(productID).orElse(null);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }
}
