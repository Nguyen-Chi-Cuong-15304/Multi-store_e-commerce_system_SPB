package com.example.projectII.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.projectII.DTO.OrderDTO;
import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.Entity.Order;
import com.example.projectII.Entity.Product;
import com.example.projectII.Entity.Shop;
import com.example.projectII.Entity.ShopCategory;
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
        product.setPurchaseNumber(0); // Initialize purchase number to 0
        product.setStatus("active"); // Set default status to "active"
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
        Shop shop = shopRepository.findById(shopID).orElse(null);
        if (shop == null) {
            System.out.println("Shop not found with ID: " + shopID);
            return null; // or throw an exception
        }
        else {
            System.out.println("Shop not null " + shop.getShopName() + "Product service");
            System.out.println("Shop ID: " + shop.getShopID());
        }
        List<Product> products = productRepository.findByShop(shop);
        if (products == null || products.isEmpty()) {
            System.out.println("No products found for shop " + shop.getShopName());
            return null; // or throw an exception
        }
        else {
            System.out.println("Products found for shop " + shop.getShopName() + ": " + products.size());
        }
        for (Product product : products) {
            System.out.println("Product ID: " + product.getProductID() + ", Name: " + product.getProductName());
        }
        return products;
        // return productRepository.findByShop(shopRepository.findById(shopID).orElse(null));
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

    public List<ProductDTO> getFeaturedProducts() {
        // lấy top 6 sản phẩm có số lượng bán nhiều nhất
        List<Product> products = productRepository.findTop6ByOrderByPurchaseNumberDesc();
        if (products == null || products.isEmpty()) {
            System.out.println("No featured products found.");
            return null; // or throw an exception
        } else {
            System.out.println("Featured products found: " + products.size());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(product.getProductID());
            productDTO.setProductName(product.getProductName());
            productDTO.setCost(product.getCost());
            productDTO.setSellCost(product.getSellCost());
            
            productDTO.setViewCount(product.getViewCount());
            productDTO.setInputCost(product.getInputPrice());
            productDTO.setLinkImg(product.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO

            double discount = 0.0;
            if (product.getCost() > 0) {
                discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return productDTOs;
        
    }

    public List<ProductDTO> getBestSellingProducts() {
        // lấy top 6 sản phẩm có số lượng bán nhiều nhất
        List<Product> products = productRepository.findTop6ByOrderByPurchaseNumberDesc();
        if (products == null || products.isEmpty()) {
            System.out.println("No best-selling products found.");
            return null; // or throw an exception
        } else {
            System.out.println("Best-selling products found: " + products.size());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(product.getProductID());
            productDTO.setProductName(product.getProductName());
            productDTO.setCost(product.getCost());
            productDTO.setSellCost(product.getSellCost());
            
            productDTO.setViewCount(product.getViewCount());
            productDTO.setInputCost(product.getInputPrice());
            productDTO.setLinkImg(product.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO

            double discount = 0.0;
            if (product.getCost() > 0) {
                discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return productDTOs;
        
    }

    public List<ProductDTO> getDiscountProducts() {
        
        List<Product> products = productRepository.findTop10ByOrderByDiscountDesc();
        if (products == null || products.isEmpty()) {
            System.out.println("No discount products found.");
            return null; // or throw an exception
        } else {
            System.out.println("Discount products found: " + products.size());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(product.getProductID());
            productDTO.setProductName(product.getProductName());
            productDTO.setCost(product.getCost());
            productDTO.setSellCost(product.getSellCost());
            
            productDTO.setViewCount(product.getViewCount());
            productDTO.setInputCost(product.getInputPrice());
            productDTO.setLinkImg(product.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO

            double discount = 0.0;
            if (product.getCost() > 0) {
                discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO
            productDTO.setShopID(product.getShop().getShopID()); // Add shop ID to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return productDTOs;
        
    }

    public List<ProductDTO> getNewProductsByShopId(int shopID) {
        // lấy top 6 sản phẩm mới nhất
        List<Product> products = productRepository.findTop6ByShop_ShopIDOrderByProductIDDesc(shopID);
        if (products == null || products.isEmpty()) {
            System.out.println("No new products found for shop ID: " + shopID);
            return null; // or throw an exception
        } else {
            System.out.println("New products found for shop ID " + shopID + ": " + products.size());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(product.getProductID());
            productDTO.setProductName(product.getProductName());
            productDTO.setCost(product.getCost());
            productDTO.setSellCost(product.getSellCost());
            
            productDTO.setViewCount(product.getViewCount());
            productDTO.setInputCost(product.getInputPrice());
            productDTO.setLinkImg(product.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO

            double discount = 0.0;
            if (product.getCost() > 0) {
                discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return productDTOs;
        
    }

    public List<ProductDTO> getDiscountProductsByShopId(int shopID) {
        // lấy top 6 sản phẩm có discount nhiều nhất
        List<Product> products = productRepository.findTop10ByShop_ShopIDOrderByDiscountDesc(shopID);
        if (products == null || products.isEmpty()) {
            System.out.println("No discount products found for shop ID: " + shopID);
            return null; // or throw an exception
        } else {
            System.out.println("Discount products found for shop ID " + shopID + ": " + products.size());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(product.getProductID());
            productDTO.setProductName(product.getProductName());
            productDTO.setCost(product.getCost());
            productDTO.setSellCost(product.getSellCost());
            
            productDTO.setViewCount(product.getViewCount());
            productDTO.setInputCost(product.getInputPrice());
            productDTO.setLinkImg(product.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO
            productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO

            double discount = 0.0;
            if (product.getCost() > 0) {
                discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return productDTOs;
        
    }

    public Page<ProductDTO> getAllProductsByShopId(int shopID, Pageable pageable, int categoryID) {
        // lấy tất cả sản phẩm của shop theo phân trang
        ShopCategory category = shopCategoryRepository.findById(categoryID).orElse(null);
        Page<Product> products = null;
        if (categoryID == 0) {
            products = productRepository.findByShop(shopRepository.findById(shopID).orElse(null), pageable);
        } else {
            products = productRepository.findByShopCategory(category, pageable);
        }
        if (products == null || products.isEmpty()) {
            System.out.println("No products found for shop ID: " + shopID);
            return null; // or throw an exception
        } else {
            System.out.println("Products found for shop ID " + shopID + ": " + products.getTotalElements());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(product.getProductID());
            productDTO.setProductName(product.getProductName());
            productDTO.setCost(product.getCost());
            productDTO.setSellCost(product.getSellCost());
            
            productDTO.setViewCount(product.getViewCount());
            productDTO.setInputCost(product.getInputPrice());
            productDTO.setLinkImg(product.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO
            productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO

            double discount = 0.0;
            if (product.getCost() > 0) {
                discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return new PageImpl<>(productDTOs, pageable, products.getTotalElements());
    }

    public ProductDTO getProductDTOByID(int productID) {
        Product product = productRepository.findById(productID).orElse(null);
        if (product == null) {
            System.out.println("Product not found with ID: " + productID);
            return null; // or throw an exception
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(product.getProductID());
        productDTO.setProductName(product.getProductName());
        productDTO.setCost(product.getCost());
        productDTO.setSellCost(product.getSellCost());
        
        productDTO.setViewCount(product.getViewCount());
        productDTO.setInputCost(product.getInputPrice());
        productDTO.setLinkImg(product.getImage()); // Add image link to DTO
        productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO
        productDTO.setDescription(product.getDescription()); // Add description to DTO

        double discount = 0.0;
        if (product.getCost() > 0) {
            discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
        }
        productDTO.setDiscount(discount); // Add discount to DTO
        productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO

        // Thêm lượt xem cho sản phẩm
        product.setViewCount(product.getViewCount() + 1);
        productRepository.save(product); // Save the updated product to increment view count
        
        // Add other properties as needed
        
        return productDTO;
    }

    public List<ProductDTO> getRelatedProducts(int productID) {
        Product product = productRepository.findById(productID).orElse(null);
        if (product == null) {
            System.out.println("Product not found with ID: " + productID);
            return null; // or throw an exception
        }
        // tìm 4 sản phẩm ngẫu nhiên trong hệ thống vì chưa có đủ sản phẩm liên quan
        List<Product> relatedProducts = productRepository.findTop6ByShop_ShopIDOrderByProductIDDesc(product.getShop().getShopID());
        if (relatedProducts == null || relatedProducts.isEmpty()) {
            System.out.println("No related products found for product ID: " + productID);
            return null; // or throw an exception
        } else {
            System.out.println("Related products found for product ID " + productID + ": " + relatedProducts.size());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product relatedProduct : relatedProducts) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(relatedProduct.getProductID());
            productDTO.setProductName(relatedProduct.getProductName());
            productDTO.setCost(relatedProduct.getCost());
            productDTO.setSellCost(relatedProduct.getSellCost());
            
            productDTO.setViewCount(relatedProduct.getViewCount());
            productDTO.setInputCost(relatedProduct.getInputPrice());
            productDTO.setLinkImg(relatedProduct.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(relatedProduct.getPurchaseNumber()); // Add purchase number to DTO

            double discount = 0.0;
            if (relatedProduct.getCost() > 0) {
                discount = ((relatedProduct.getCost() - relatedProduct.getSellCost()) / relatedProduct.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            productDTO.setShopName(relatedProduct.getShop().getShopName()); // Add shop name to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public List<ProductDTO> getProductSuggestions(String query){
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(query);
        if (products == null || products.isEmpty()) {
            System.out.println("No product suggestions found for query: " + query);
            return null; // or throw an exception
        } else {
            System.out.println("Product suggestions found for query " + query + ": " + products.size());
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductID(product.getProductID());
            productDTO.setProductName(product.getProductName());
            productDTO.setCost(product.getCost());
            productDTO.setSellCost(product.getSellCost());
            
            productDTO.setViewCount(product.getViewCount());
            productDTO.setInputCost(product.getInputPrice());
            productDTO.setLinkImg(product.getImage()); // Add image link to DTO
            productDTO.setPurchaseNumber(product.getPurchaseNumber()); // Add purchase number to DTO

            double discount = 0.0;
            if (product.getCost() > 0) {
                discount = ((product.getCost() - product.getSellCost()) / product.getCost()) * 100;
            }
            productDTO.setDiscount(discount); // Add discount to DTO
            productDTO.setShopName(product.getShop().getShopName()); // Add shop name to DTO
            
            // Add other properties as needed
            
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    

}
