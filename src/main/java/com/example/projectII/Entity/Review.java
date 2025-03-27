package com.example.projectII.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewid")
    private int reviewID;

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shopid")
    private Shop shop;

    @Column(name = "reviewcontent")
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "buyerid")
    private Buyer buyer;

    @Column(name = "rate")
    private int rate;
}
