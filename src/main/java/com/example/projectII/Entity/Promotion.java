package com.example.projectII.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "promotion")
@Data
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotionid")
    private int promotionID;

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

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
