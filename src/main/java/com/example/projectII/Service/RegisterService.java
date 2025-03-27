package com.example.projectII.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.Shipper;
import com.example.projectII.Entity.ShopOwner;
import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Repository.ShipperRepository;
import com.example.projectII.Repository.ShopOwnerRepository;

import java.time.LocalDateTime;

@Service
public class RegisterService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ShopOwnerRepository shopownerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(String fullName, String username, String password, String role) {
        // Kiểm tra vai trò hợp lệ
        if (!role.equals("BUYER") && !role.equals("SHIPPER") && !role.equals("SHOPOWNER")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Kiểm tra username và email đã tồn tại
        switch (role) {
            case "BUYER":
                if (buyerRepository.findByUsername(username).isPresent()) {
                    throw new IllegalArgumentException("Username already exists");
                }
                
                break;
            case "SHIPPER":
                if (shipperRepository.findByUsername(username).isPresent()) {
                    throw new IllegalArgumentException("Username already exists");
                }
                
                break;
            case "SHOPOWNER":
                if (shopownerRepository.findByUsername(username).isPresent()) {
                    throw new IllegalArgumentException("Username already exists");
                }
                break;
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(password);

        // Lưu vào bảng tương ứng
        switch (role) {
            case "BUYER":
                Buyer buyer = new Buyer();
                buyer.setUsername(username);
                buyer.setRole(role);
                buyer.setPassword(encodedPassword);
                buyer.setBuyername(fullName);
                buyerRepository.save(buyer);
                System.out.println("Buyer registered at " + LocalDateTime.now());
                break;
            case "SHIPPER":
                Shipper shipper = new Shipper();
                shipper.setUsername(username);
                shipper.setPassword(encodedPassword);
                shipper.setRole(role);
                shipper.setShipperName(fullName);
                shipperRepository.save(shipper);
                break;
            case "SHOPOWNER":
                ShopOwner shopowner = new ShopOwner();
                shopowner.setUsername(username);
                shopowner.setPassword(encodedPassword);
                shopowner.setRole(role);
                shopowner.setShopOwnerName(fullName);
                shopownerRepository.save(shopowner);
                break;
        }
    }
}