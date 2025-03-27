package com.example.projectII.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ordership")
@Data
public class OrderShip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordershipid")
    private int orderShipID;
    
    @OneToOne
    @JoinColumn(name = "orderid")
    private Order order;

    @OneToOne
    @JoinColumn(name = "shipperid")
    private Shipper shipper;
}
