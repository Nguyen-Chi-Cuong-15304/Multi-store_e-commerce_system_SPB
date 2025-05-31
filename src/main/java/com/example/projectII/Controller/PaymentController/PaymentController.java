package com.example.projectII.Controller.PaymentController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.projectII.Repository.BuyerRepository;
import com.example.projectII.Repository.OrderItemRepository;
import com.example.projectII.Repository.OrderRepository;
import com.example.projectII.Repository.ProductRepository;
import com.example.projectII.Service.VNPayService;

// import com.example.Project1.entity.CartWrapper;
// import com.example.Project1.entity.Orders;
// import com.example.Project1.entity.Product;
// import com.example.Project1.entity.OrderItem;
// import com.example.Project1.entity.OrderItemWrapper;
// import com.example.Project1.entity.WebUser;
// import com.example.Project1.repository.OrderItemRepository;
// import com.example.Project1.repository.OrderRepository;
// import com.example.Project1.repository.ProductRepository;
// import com.example.Project1.repository.WebUserRepository;
// import com.example.Project1.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;

import com.example.projectII.DTO.CartItemDTO;
import com.example.projectII.DTO.CheckoutDTO;
import com.example.projectII.Entity.Buyer;
import com.example.projectII.Entity.CartItem;
import com.example.projectII.Entity.Order;
import com.example.projectII.Entity.OrderItem;
import com.example.projectII.Entity.Product;

@Controller
public class PaymentController {
    @Autowired
    private VNPayService vnPayService;
    // @Autowired
    // private OrderRepository orderRepository;
    // @Autowired
    // private OrderItemRepository orderItemRepository;
    // @Autowired
    // private WebUserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    // @GetMapping({"", "/"})
    // public String home(){
    //     return "createOrder";
    // }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public ResponseEntity<?> submitOrder(   
                            HttpServletRequest request, @RequestBody CheckoutDTO checkoutDTO) {
        
        // In thông tin CheckoutDTO để kiểm tra
        
        System.out.println("CheckoutDTO: " + checkoutDTO.getCartItems().size());
        
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Username: " + username);
        Buyer user = buyerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        // String email = auth.getName();
        // WebUser user = userRepository.findByEmail(email);
        int userID = user.getBuyerID();
        System.out.println("User ID: " + userID);
        
        // Tính tổng tiền và chuẩn bị dữ liệu
        // BigDecimal totalAmount = new BigDecimal(0);
        // for (OrderItem orderItem : orderItemWrapper.getOrderItems()) {
        //     totalAmount = totalAmount.add(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        // }
        double totalAmount = checkoutDTO.getTotal();

        // Tạo URL VNPAY
        String orderInfo = "Thanh toan don hang cua user " + user.getUsername() + " voi ID " + userID;
        int orderTotal = (int) (totalAmount * 1000); // VNPAY yêu cầu số tiền tính bằng đồng
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);

        // Lưu thông tin đơn hàng tạm thời vào session (hoặc database với trạng thái Pending)
        // request.getSession().setAttribute("orderItemWrapper", orderItemWrapper);
        // request.getSession().setAttribute("totalAmount", totalAmount);
        // request.getSession().setAttribute("userID", user.getUserID());
        request.getSession().setAttribute("cehckoutDTO", checkoutDTO);
        // return "redirect:" + vnpayUrl;
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("redirectUrl", vnpayUrl);
        response.put("message", "Đã tạo liên kết thanh toán VNPAY thành công");
        response.put("orderInfo", orderInfo);
        response.put("totalAmount", orderTotal);

        return ResponseEntity.ok(response);
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        // Lấy thông tin từ session
        // OrderItemWrapper orderItemWrapper = (OrderItemWrapper) request.getSession().getAttribute("orderItemWrapper");
        CheckoutDTO checkoutDTO = (CheckoutDTO) request.getSession().getAttribute("cehckoutDTO");
        // BigDecimal totalAmount = (BigDecimal) request.getSession().getAttribute("totalAmount");
        int userID = checkoutDTO.getBuyerID();
        double totalAmount = checkoutDTO.getTotal();
         if(paymentStatus == 1){
            // Orders order = orderItemWrapper.getOrder();
            // order.setUserID(userID);
            // order.setStatus("Đã thanh toán");
            // order.setOrderDate(new Date(System.currentTimeMillis()));
            // order.setTotalAmount(totalAmount);
            // orderRepository.save(order);

            // for (OrderItem orderItem : orderItemWrapper.getOrderItems()) {
            //     orderItem.setOrderID(order.getOrderID());
            //     orderItemRepository.save(orderItem);
            //     Product product = productRepository.findById(orderItem.getProductID()).get();
            //     product.setStockQuantity(product.getStockQuantity() - orderItem.getQuantity());
            //     productRepository.save(product);
            // }
            Order order = new Order();
            order.setBuyer(buyerRepository.findById(userID).orElseThrow(() -> new IllegalArgumentException("Invalid user ID")));
            order.setStatus("Đã thanh toán");
            LocalDateTime orderDate = LocalDateTime.now();
            order.setCreateTime(orderDate);
            order.setTotalCost(totalAmount);
            order.setBuyerName(checkoutDTO.getFullName());
            order.setPhoneNumber(checkoutDTO.getPhoneNumber());
            order.setAddress(checkoutDTO.getAddress());
            order.setNote(checkoutDTO.getNotes());
            order.setStar(5);
            orderRepository.save(order);

            // Lưu thông tin các item trong đơn hàng
            for (CartItemDTO cartItem : checkoutDTO.getCartItems()) {
                // Tạo OrderItem từ CartItem
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(productRepository.findById(cartItem.getProductID()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID")));
                orderItem.setOrder(order);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setTotalCost(cartItem.getSellCost() * cartItem.getQuantity());
                // Lưu OrderItem
                orderItemRepository.save(orderItem);
                
                // Cập nhật số lượng sản phẩm trong kho
                Product product = productRepository.findById(cartItem.getProductID()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
                product.setQuantityInStock(product.getQuantityInStock() - cartItem.getQuantity());
                productRepository.save(product);
            }
        }

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}