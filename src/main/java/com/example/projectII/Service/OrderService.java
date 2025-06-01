package com.example.projectII.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectII.DTO.OrderDTO;
import com.example.projectII.DTO.OrderItemDTO;
import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.Order;
import com.example.projectII.Entity.OrderItem;
import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Repository.OrderItemRepository;
import com.example.projectII.Repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderDTO> getOrderHistoryByUserId(int userId) {
        Buyer buyer  = buyerRepository.findByBuyerID(userId)
                .orElseThrow(() -> new RuntimeException("Buyer not found with id: " + userId));
        List<Order> orders = orderRepository.findAllByBuyer(buyer);
        List<OrderDTO> orderDTO = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO dto = new OrderDTO();
            dto.setOrderID(order.getOrderID());
            dto.setBuyerID(order.getBuyer().getBuyerID());
            // dto.setProductID(order.getProduct().getProductID());
            dto.setOrderDate(order.getCreateTime());
            dto.setStatus(order.getStatus());
            dto.setTotalPrice(order.getTotalCost());
            dto.setAddress(order.getAddress());
            orderDTO.add(dto);
        }
        return orderDTO;
    }

    public List<OrderItemDTO> getOrderDetails(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        List<OrderItemDTO> orderItems = new ArrayList<>();
        List<OrderItem> items = orderItemRepository.findByOrder(order);
        for (OrderItem item : items) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setOrderItemID(item.getOrderItemID());
            dto.setProductID(item.getProduct().getProductID());
            dto.setQuantity(item.getQuantity());
            // dto.setPrice(item.getPrice());
            dto.setLinkImg(item.getProduct().getImage());
            dto.setProductName(item.getProduct().getProductName());
            orderItems.add(dto);
        }
        return orderItems;
    }
}
