package com.example.projectII.DTO;

import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class PromotionCateDTO {
    // private int promotionID;
    @Nullable
    private int promotionForCateID;
    @Nullable
    private int shopCategoryID;
    @Nullable
    private int shopID;
    private double discount;
    private String description;
    private LocalDate endDate;
    private LocalDate startDate;

    @Nullable
    private String categoryName ;

    @Nullable
    private String status = "inactive"; // "active" or "inactive"

    @Nullable
    private String type;
}
