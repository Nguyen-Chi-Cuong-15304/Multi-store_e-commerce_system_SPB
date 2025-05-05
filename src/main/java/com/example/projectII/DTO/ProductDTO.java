package com.example.projectII.DTO;

import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class ProductDTO {
    private int productID;
    @Nullable
    private String productName;
    @Nullable
    private int shopID;
    @Nullable
    private int categoryID;
    @Nullable
    private double cost;
    @Nullable
    private double sellCost;
    @Nullable
    private String description;

    @Nullable
    private MultipartFile image;   
    @Nullable
    private int quantityInStock;
    @Nullable
    private int viewCount;
    @Nullable
    private double inputCost;
    @Nullable
    private String status;

    @Nullable
    private int purchaseNumber = 0; // Initialize purchase number to 0

    @Nullable
    private String linkImg;

    @Nullable
    private double discount;

    @Nullable
    private String shopName;

}
