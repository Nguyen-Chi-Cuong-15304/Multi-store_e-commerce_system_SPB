package com.example.projectII.DTO;

import java.util.List;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class CheckoutDTO {
    @Nullable
    private int buyerID;
    @Nullable
    List<CartItemDTO> cartItems;
    @Nullable
    private String fullName;
    @Nullable
    private String phoneNumber;
    @Nullable
    private String address;
    @Nullable
    private String paymentMethod;
    @Nullable
    private String notes;
    @Nullable
    private double subtotal;
    @Nullable
    private double shippingFee;
    @Nullable
    private double total;
    @Nullable
    private String email;
}
