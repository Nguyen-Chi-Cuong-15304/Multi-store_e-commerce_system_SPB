package com.example.projectII.DTO;

import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class ShopDTO {
    @Nullable
    private int shopID;
    @Nullable
    private String shopName;
    @Nullable
    private MultipartFile backgroundImage;
    @Nullable
    private int shopOwnerID;
    @Nullable
    private String status;
    @Nullable
    private String description;
    @Nullable
    private double averageAssess;
    @Nullable
    private String typeofbussiness;

    @Nullable
    private String linkImg;
}
