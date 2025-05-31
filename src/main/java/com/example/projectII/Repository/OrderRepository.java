package com.example.projectII.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Define any custom query methods if needed
    // For example, you can find orders by buyer ID or status
    // List<Order> findByBuyerId(Integer buyerId);
    
    // You can also define methods to find orders by status, date range, etc.
}
