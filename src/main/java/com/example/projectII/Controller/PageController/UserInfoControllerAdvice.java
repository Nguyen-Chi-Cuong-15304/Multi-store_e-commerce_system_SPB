package com.example.projectII.Controller.PageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.Shipper;
import com.example.projectII.Entity.ShopOwner;
import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Repository.ShipperRepository;
import com.example.projectII.Repository.ShopOwnerRepository;

@ControllerAdvice
public class UserInfoControllerAdvice {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ShopOwnerRepository shopOwnerRepository;

    @ModelAttribute
    public void addUserInfoToModel(Model model) {
        // Kiểm tra trạng thái đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            

            // Truy vấn thông tin chi tiết từ cơ sở dữ liệu
            String fullname = null;
            Integer id = null;
            switch (role) {
                case "ROLE_SHOPOWNER":
                    ShopOwner shopOwner = shopOwnerRepository.findByUsername(username).orElse(null);
                    if (shopOwner != null) {
                        fullname = shopOwner.getShopOwnerName();
                        id = shopOwner.getShopOwnerID();
                    }
                    break;
                case "ROLE_BUYER":
                    Buyer buyer = buyerRepository.findByUsername(username).orElse(null);
                    if (buyer != null) {
                        fullname = buyer.getBuyername();
                        id = buyer.getBuyerID();
                    }
                    break;
                case "ROLE_SHIPPER":
                    Shipper shipper = shipperRepository.findByUsername(username).orElse(null);
                    if (shipper != null) {
                        fullname = shipper.getShipperName();
                        id = shipper.getShipperID();
                    }
                    break;
            }

            // Thêm thông tin vào model
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", username);
            model.addAttribute("fullname", fullname != null ? fullname : username);
            model.addAttribute("role", role);
            model.addAttribute("id", id);
        } else {
            // Nếu chưa đăng nhập
            model.addAttribute("isLoggedIn", false);
        }
    }
}