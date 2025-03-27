package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cartitem")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartitemid")
    private int cartItemID;

    @ManyToOne
    @JoinColumn(name = "cartid")
    private ShoppingCart shoppingCart;

    @OneToOne
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "quantity")
    private int quantity;
}
