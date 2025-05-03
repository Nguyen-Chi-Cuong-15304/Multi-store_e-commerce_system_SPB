package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private int productID;

    @Column(name = "productname")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "shopid")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private ShopCategory shopCategory;

    @Column(name = "cost")
    private double cost;

    @Column(name = "sellcost")
    private double sellCost;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "quantityinstock")
    private int quantityInStock;

    @Column(name = "viewcount")
    private int viewCount;

    @Column(name = "inputprice")
    private double inputPrice;

    @Column(name = "status")
    private String status;
    //luot mua
    @Column(name = "purchasenumber")
    private int purchaseNumber;
}
