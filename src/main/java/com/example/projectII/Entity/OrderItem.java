package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orderitem")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderitemid")
    private int orderItemID;
    
    @ManyToOne
    @JoinColumn(name = "orderid")
    private Order order;

    @OneToOne
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "totalcost")
    private double totalCost;
}
