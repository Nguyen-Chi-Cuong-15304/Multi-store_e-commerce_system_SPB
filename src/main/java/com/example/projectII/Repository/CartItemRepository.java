package com.example.projectII.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.CartItem;
import com.example.projectII.Entity.Product;
import com.example.projectII.Entity.ShoppingCart;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByProduct(Product product);

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    
}
