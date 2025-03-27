package com.example.projectII.DTO;

import java.util.Date;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class PromotionDTO {
    private int productID;
    private int shopID;
    private double discount;

    @Nullable
    private String description;

    // @Nullable
    // private Date endDate;
}
