package com.example.hp_29_MiniatureCrafts.security;

import com.example.hp_29_MiniatureCrafts.security.jwt.AuthEntryPointJwt;
import com.example.hp_29_MiniatureCrafts.security.jwt.AuthTokenFilter;
import com.example.hp_29_MiniatureCrafts.security.services.UsersDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity // Kích hoạt bảo mật cấp phương thức (các chú thích như @PreAuthorize, @Secured)
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UsersDetailsServiceImpl userDetailsService; // Dịch vụ tùy chỉnh để tải thông tin người dùng từ cơ sở dữ liệu

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // Xử lý khi có yêu cầu không hợp lệ hoặc không có quyền truy cập

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(); // Bộ lọc JWT để kiểm tra token trong mỗi yêu cầu
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Tạo DaoAuthenticationProvider với UserDetailsService và PasswordEncoder tùy chỉnh
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService); // Sử dụng userDetailsService để tải thông tin người dùng
        authProvider.setPasswordEncoder(passwordEncoder()); // Mã hóa mật khẩu bằng BCrypt

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Trả về AuthenticationManager để quản lý xác thực
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu bằng thuật toán BCrypt
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF cho ứng dụng RESTful
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Xử lý ngoại lệ khi không có quyền
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Không dùng session (stateless)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**").permitAll() // Cho phép truy cập không cần xác thực cho /api/auth/**
//
//                                .requestMatchers("/users/signin").permitAll() // Cho phép truy cập không cần xác thực cho /api/auth/**
//                                .requestMatchers("/shop/all**").permitAll() // Cho phép truy cập không cần xác thực cho /api/auth/**
//                                .requestMatchers("/shop/result/**").permitAll() // Cho phép truy cập không cần xác thực cho /api/test/**
                                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các URL khác
                );

        http.authenticationProvider(authenticationProvider()); // Đăng ký authenticationProvider đã cấu hình

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Thêm AuthTokenFilter trước UsernamePasswordAuthenticationFilter để kiểm tra token JWT

        return http.build(); // Xây dựng cấu hình bảo mật hoàn chỉnh
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Cho phép origin từ frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương thức HTTP được phép
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Các headers cần thiết
        configuration.setAllowCredentials(true); // Cho phép cookie hoặc thông tin xác thực
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng CORS cho tất cả các endpoint
        return source;
    }

}
