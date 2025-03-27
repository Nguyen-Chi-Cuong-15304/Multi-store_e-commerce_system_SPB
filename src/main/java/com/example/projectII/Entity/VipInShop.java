package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vipinshop")
@Data
public class VipInShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vipinshopid")
    private int vipInShopID;
    
    @OneToOne
    @JoinColumn(name = "shopid")
    private Shop shop;

    @OneToOne
    @JoinColumn(name = "buyerid")
    private Buyer buyer;

    @Column(name = "viplevel")
    private int vipLevel;

    @Column(name = "sumofmoney")
    private double sumOfMoney;
}
