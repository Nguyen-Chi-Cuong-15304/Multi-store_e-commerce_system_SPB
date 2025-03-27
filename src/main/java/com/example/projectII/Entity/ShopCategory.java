package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shopcategory")
@Data
public class ShopCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryid")
    private int shopCategoryID;

    @ManyToOne
    @JoinColumn(name = "shopid")
    private Shop shop;

    @Column(name = "categoryname")
    private String categoryName;

    @Column(name = "description")
    private String description;
}
