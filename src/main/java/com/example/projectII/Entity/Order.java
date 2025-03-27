package com.example.projectII.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "buyerid")
    private Buyer buyer;

    @Column(name = "createtime")
    private LocalDateTime createTime;

    @Column(name = "totalcost")
    private double totalCost;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;
    
    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "star")
    private int star;

    @Column(name = "buyername")
    private String buyerName;

    @Column(name = "note")
    private String note;
}
