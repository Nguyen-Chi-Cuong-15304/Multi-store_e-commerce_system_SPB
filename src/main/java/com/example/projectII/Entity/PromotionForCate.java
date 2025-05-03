package com.example.projectII.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "promotion_for_cate")
@Data
public class PromotionForCate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotionid")
    private int promotionID;
    
    @ManyToOne
    @JoinColumn(name = "categoryid")
    private ShopCategory shopCategory;

    @ManyToOne
    @JoinColumn(name = "shopid")
    private Shop shop;

    @Column(name = "discount")
    private double discount;

    @Column(name = "description")
    private String description;

    @Column(name = "enddate")
    private LocalDate endDate;

    @Column(name = "startdate")
    private LocalDate startDate;

    @Column(name = "status")
    private String status; // "active" or "inactive"
}
