package com.example.projectII.DTO;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class OrderItemDTO {
    @Nullable
    private int orderItemID;
    @Nullable
    private int orderID;
    @Nullable
    private int productID;
    @Nullable
    private int quantity;
    @Nullable
    private double pricePerUnit;
    @Nullable
    private double totalPrice;
    @Nullable
    private String productName;
    @Nullable
    private String linkImg;
}
