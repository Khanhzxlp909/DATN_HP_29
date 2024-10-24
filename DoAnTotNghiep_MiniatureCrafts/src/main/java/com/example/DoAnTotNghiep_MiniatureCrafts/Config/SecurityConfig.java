package com.example.DoAnTotNghiep_MiniatureCrafts.Config;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        // Retrieve the list of users from the database
        List<Users> userList = userService.userRepository.findAll();

        // Create an instance of InMemoryUserDetailsManager to hold users
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        PasswordEncoder encoder = passwordEncoder();

        // từ vòng lặp lấy ra danh sách username vs password
        for (Users user : userList) {
            String roleCreate = String.valueOf(user.getRole().getCanCreate());
            String roleEdit = String.valueOf(user.getRole().getCanUpdate());
            String roleDelete = String.valueOf(user.getRole().getCanDelete());
            UserDetails userDetails = User
                    .withUsername(user.getUsername())
                    .password(encoder.encode(user.getPassword()))// mã hoá mat khau
                    .roles(roleCreate, roleEdit, roleDelete)
                    .build();

            manager.createUser(userDetails);
        }

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Allow the `/public-api` to be accessed without authentication
                        .requestMatchers("/gunshop/home**").permitAll()

                        // Require authentication for any other request
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Use HTTP Basic Authentication with default settings

        return http.build();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());// Sử dụng HTTP Basic Authentication với cấu hình mặc định
//
//        return http.build();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Cho phép tất cả các pattern của origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Cho phép tất cả các phương thức HTTP
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Cho phép các header cần thiết
        configuration.setAllowCredentials(true); // Cho phép gửi cookie hoặc thông tin xác thực
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
