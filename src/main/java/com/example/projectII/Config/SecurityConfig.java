package com.example.projectII.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.projectII.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/register", "/login", "/static/**", "/JS/**", "/CSS/**", "/Image/**", "/all/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/buyer/**").hasRole("BUYER")
                .requestMatchers("/shipper/**").hasRole("SHIPPER")
                .requestMatchers("/shopowner/**").hasRole("SHOPOWNER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/all/login") // Trang đăng nhập tùy chỉnh
                .loginProcessingUrl("/all/login") // URL xử lý đăng nhập
                .failureHandler((request, response, exception) -> {
                    System.out.println("Authentication failed: " + exception.getMessage());
                    String errorMessage = "Invalid username, password, or role";
                    if (exception.getMessage().contains("Role parameter is missing")) {
                        errorMessage = "Please select a role";
                    } else if (exception.getMessage().contains("Username not found")) {
                        errorMessage = "Username not found for the selected role";
                    } else if (exception.getMessage().contains("Bad credentials")) {
                        errorMessage = "Invalid username or password";
                    }
                    response.sendRedirect("/all/login?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
                })
                .successHandler((request, response, authentication) -> {
                    System.out.println("Authentication successful for user: " + authentication.getName());
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    System.out.println("User role: " + role);
                    if (role.equals("ROLE_BUYER")) {
                        response.sendRedirect("/all/");
                    } else if (role.equals("ROLE_SHIPPER")) {
                        response.sendRedirect("/shipper/dashboard");
                    } else if (role.equals("ROLE_SHOPOWNER")) {
                        response.sendRedirect("/shopowner/login");
                    } else {
                        response.sendRedirect("/all/login?error=invalid_role");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/all/logout")
                .logoutSuccessUrl("/all/")
                .permitAll()
            );

        return http.build();
    }
}
