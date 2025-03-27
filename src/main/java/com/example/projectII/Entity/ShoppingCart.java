package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shoppingcart")
@Data
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartid")
    private int shoppingCartID;

    @ManyToOne
    @JoinColumn(name = "buyerid")
    private Buyer buyer;
}
