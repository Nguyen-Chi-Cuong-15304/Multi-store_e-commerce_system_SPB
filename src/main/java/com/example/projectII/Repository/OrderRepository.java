package com.example.projectII.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByBuyer(Buyer buyer);

    
    
    // Optional<Order> findByOrderId(int orderId);
    
    // List<Order> findByProductId(int productId);
    
    // List<Order> findByStatus(String status);
    
    // List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
