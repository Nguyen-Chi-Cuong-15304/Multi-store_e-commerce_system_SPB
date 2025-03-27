package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shop")
@Data
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopid")
    private int shopID;

    @Column(name = "shopname")
    private String shopName;

    @Column(name = "backgroundimage")
    private String backgroundImage;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "shopownerid")
    private ShopOwner shopOwner;

    @Column(name = "averageassess")
    private double averageAssess;
}
