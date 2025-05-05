package com.example.projectII.DTO;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class CartItemDTO {
    private int productID;
    private int quantity;
    @Nullable
    private String productName;
    @Nullable
    private String linkImg;
    @Nullable
    private String description;
    @Nullable
    private String shopName;
    @Nullable
    private double cost;
    @Nullable
    private double sellCost;
}
