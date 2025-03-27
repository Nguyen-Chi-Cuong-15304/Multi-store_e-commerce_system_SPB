package com.example.projectII.DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ShopDTO {
    private int shopID;
    private String shopName;
    private MultipartFile backgroundImage;
    private int shopOwnerID;
    
    private String status;
    private String description;
    private double averageAssess;
}
