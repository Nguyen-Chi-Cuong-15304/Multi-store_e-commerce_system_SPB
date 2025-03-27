package com.example.projectII.DTO;

import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class ProductDTO {
    private int productID;
    private String productName;
    private int shopID;
    private int categoryID;
    private double cost;
    private double sellCost;
    private String description;

    @Nullable
    private MultipartFile image;   
    private int quantityInStock;
    private int viewCount;
    private double inputCost;
    @Nullable
    private String status;

}
