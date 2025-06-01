package com.example.projectII.DTO;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class OrderDTO {
    @Nullable
    private int orderID;
    @Nullable
    private int buyerID;
    @Nullable
    private int productID;
    // @Nullable
    // private int quantity;
    @Nullable
    private LocalDateTime orderDate;
    @Nullable
    private String status;
    @Nullable
    private String address;
    @Nullable
    private double totalPrice;
    
}
