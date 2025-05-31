package com.example.projectII.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    // Define any custom query methods if needed
    // For example, you can find order items by order ID or product ID
    // List<OrderItem> findByOrderId(Integer orderId);
    
    // You can also define methods to find order items by product ID, quantity, etc.
}
