package com.example.projectII.Service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.Shipper;
import com.example.projectII.Entity.ShopOwner;
import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Repository.ShipperRepository;
import com.example.projectII.Repository.ShopOwnerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ShopOwnerRepository shopOwnerRepository;

    @Autowired
    private BuyerRepository buyerRepository;
    
    @Autowired
    private ShipperRepository shipperRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();    
        String role = attr.getRequest().getParameter("role");
        System.out.println("Login attempt with username: " + username + ", role: " + role);
        if (role == null || role.trim().isEmpty()) {
            throw new UsernameNotFoundException("Role parameter is missing or empty");
        }
        switch (role.toLowerCase()) {
            case "shopowner":
                ShopOwner shopOwner = shopOwnerRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found for role shopowner: " + username));
                System.out.println("Found shopowner: " + shopOwner.getUsername() + ", password: " + shopOwner.getPassword());
                return new org.springframework.security.core.userdetails.User(
                    shopOwner.getUsername(), 
                    shopOwner.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_SHOPOWNER"))  
                );
            case "buyer":
                Buyer buyer = buyerRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found for role buyer: " + username));  
                System.out.println("Found buyer: " + buyer.getUsername() + ", password: " + buyer.getPassword());
                return new org.springframework.security.core.userdetails.User(
                    buyer.getUsername(), 
                    buyer.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_BUYER"))  
                );    
                
            case "shipper":
                Shipper shipper = shipperRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found for role shipper: " + username));  
                System.out.println("Found shipper: " + shipper.getUsername() + ", password: " + shipper.getPassword());
                return new org.springframework.security.core.userdetails.User(
                    shipper.getUsername(), 
                    shipper.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_SHIPPER"))  
                );    
                
            default:
                throw new UsernameNotFoundException("Invalid role: " + role);
        }
    }
}