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
        http.csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Bật CORS và chỉ định cấu hình CORS
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/MiniatureCrafts/signin",
                                        "/MiniatureCrafts/signup",
                                        "/MiniatureCrafts/registerinfo",
                                        "/MiniatureCrafts/home",
                                        "/MiniatureCrafts/filterByPrice",
                                        "/MiniatureCrafts/result/**",
                                        "/MiniatureCrafts/findid/**",
                                        "/MiniatureCrafts/category/**",
                                        "/MiniatureCrafts/brands/**",
                                        "/MiniatureCrafts/user/**",
                                        "/MiniatureCrafts/categories",
                                        "/MiniatureCrafts/brand",
                                        "/MiniatureCrafts/new",
                                        "/MiniatureCrafts/history/**",
                                        "/MiniatureCrafts/history/getprd/**",
                                        "/admin/warehouse/**",
                                        "/admin/orders/**",
                                        "/admin/orders/history/getprd/**",
                                        "/users/signup",
                                        "/admin/signin",
                                        "/admin/variation/brands/**",
                                        "/admin/signup",
                                        "/brands/get",
                                        "/admin/variation/category/**",
                                        "/admin/variation/result/all",
                                        "/admin/orders/resultvoucher/**",
                                        "/admin/orders/findall",
                                        "/admin/warehouse/findall",
                                        "/admin/customer/result/**",
                                        "/admin/result/all",
                                        "/admin/warehouse/result/**",
                                        "/admin/warehouse/update/**",
                                        "/admin/warehouse/save",
                                        "/admin/warehouse/**",
                                        "/admin/variation/images/upload",
                                        "/admin/variation/getproduct",
                                        "/images/**").permitAll() // Cho phép tất cả các yêu cầu
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173/","http://localhost:3000/", "http://127.0.0.1:5502/", "http://192.168.1.5:8081/", "http://localhost:8081/", "http://192.168.1.133:8081/", "http://127.0.0.1:8081/")); // Cho phép origin từ frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương thức HTTP được phép
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Các headers cần thiết
        configuration.setAllowCredentials(true); // Cho phép cookie hoặc thông tin xác thực
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng CORS cho tất cả các endpoint
        return source;
    }

}
