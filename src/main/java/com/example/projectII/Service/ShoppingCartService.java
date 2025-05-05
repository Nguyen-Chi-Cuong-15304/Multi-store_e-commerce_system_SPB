package com.example.projectII.Service;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.projectII.DTO.CartItemDTO;
import com.example.projectII.DTO.ProductDTO;
import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.CartItem;
import com.example.projectII.Entity.Product;
import com.example.projectII.Entity.Shop;
import com.example.projectII.Entity.ShoppingCart;
import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Repository.CartItemRepository;
import com.example.projectII.Repository.ProductRepository;
import com.example.projectII.Repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public void addToCart(CartItemDTO cartItemDTO) {
        // Lay thong tin nguoi dung
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            // Xu ly them san pham vao gio hang
            Buyer buyer = buyerRepository.findByUsername(username).orElse(null);
            ShoppingCart shoppingCart = shoppingCartRepository.findByBuyer(buyer).orElse(null);
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart();
                shoppingCart.setBuyer(buyer);
                shoppingCartRepository.save(shoppingCart);
                System.out.println("Gio hang moi duoc tao cho nguoi dung: " + username);
            }
            System.out.println("Gio hang da ton tai cho nguoi dung: " + username);
            Product product = productRepository.findById(cartItemDTO.getProductID()).orElse(null);
            if (product != null) {
                System.out.println("San pham da ton tai: " + product.getProductName());
                CartItem cartItem = cartItemRepository.findByProduct(product).orElse(null);
                if (cartItem == null) {
                    cartItem = new CartItem();
                    cartItem.setProduct(product);
                    cartItem.setQuantity(cartItemDTO.getQuantity());
                    cartItem.setShoppingCart(shoppingCart);
                    cartItemRepository.save(cartItem);
                } else {
                    System.out.println("San pham da ton tai trong gio hang: " + product.getProductName());
                    cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
                    cartItemRepository.save(cartItem);
                }
            } 
        }
    }

    public List<CartItemDTO> getCartItems() {
        // Lay thong tin nguoi dung
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            Buyer buyer = buyerRepository.findByUsername(username).orElse(null);
            ShoppingCart shoppingCart = shoppingCartRepository.findByBuyer(buyer).orElse(null);
            if (shoppingCart != null) {
                List<CartItem> cartItems = cartItemRepository.findByShoppingCart(shoppingCart);
                List<CartItemDTO> cartItemDTOs = new ArrayList<>();
                for (CartItem cartItem : cartItems) {
                    Product product = cartItem.getProduct();
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setProductID(product.getProductID());
                    cartItemDTO.setProductName(product.getProductName());
                    cartItemDTO.setQuantity(cartItem.getQuantity());
                    cartItemDTO.setLinkImg(product.getImage());
                    cartItemDTO.setShopName(product.getShop().getShopName());
                    cartItemDTO.setSellCost(product.getSellCost());
                    cartItemDTOs.add(cartItemDTO);
                }
                return cartItemDTOs;
            }
        }
        return new ArrayList<>();
    }
}
