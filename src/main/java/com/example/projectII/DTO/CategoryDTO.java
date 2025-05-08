package com.example.projectII.DTO;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class CategoryDTO {
    private String categoryName;
    private int ShopID;
    private String description;

    @Nullable
    private int categoryID;
}
