package com.example.projectII.DTO;

import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class PromotionDTO {

    @Nullable
    private int promotionID;

    @Nullable
    private int productID;
    @Nullable
    private int shopID;
    private double discount;

    @Nullable
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    @Nullable
    private String status; // "active" or "inactive"

    @Nullable
    private String productName;

    @Nullable
    private double cost;

    @Nullable
    private double sellCost;

    @Nullable
    private String type;

    @Nullable
    private int shopCategoryID;

    @Nullable
    private String categoryName;
}
