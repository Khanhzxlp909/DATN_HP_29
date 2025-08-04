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
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebSecurityConfig {

    @Autowired
    private UsersDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/api/v1/transactions/**",
                                "/upload/images/**",
                                "/swagger-ui/**",
                                "/MiniatureCrafts/signin",
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
                                "/MiniatureCrafts/bestseller",
                                "/MiniatureCrafts/history/**",
                                "/MiniatureCrafts/history/getprd/**",
                                "/admin/warehouse/**",
                                "/admin/orders/**",
                                "/admin/orders/history/getprd/**",
                                "/admin/employee/**",
                                "/admin/signin",
                                "/admin/variation/brands/**",
                                "/admin/variation/warehouse/supiller/**",
                                "/admin/variation/getproduct",
                                "/admin/signup",
                                "/brands/get",
                                "/admin/variation/category/**",
                                "/admin/variation/result/all",
                                "/admin/variation/get/all",
                                "/admin/orders/resultvoucher/**",
                                "/admin/orders/findall",
                                "/admin/customer/result/**",
                                "/admin/customer/edit/**",
                                "/admin/category/get",
                                "/admin/category/get",
                                "/admin/product/findAll",
                                "/admin/brands/get",
                                "/admin/result/all",
                                "/admin/employee/**",
                                "/admin/variation/add",
                                "/admin/employee/findall",
                                "/admin/variation/images/upload",
                                "/admin/variation/images/findall",
                                "/admin/variation/getproduct",
                                "/admin/variation/images/findall",
                                "/admin/news/findall",
                                "/api/v1/cart/findall/**",
                                "/api/v1/cart/addtocart",
                                "/api/v1/cart/remove/**",
                                "/api/v1/cart/removeall/**",
                                "/api/v1/cart/editquantity/**",
                                "/MiniatureCrafts/send-email/**",
                                "/admin/introduces/findall",
                                "/MiniatureCrafts/contact/send",
                                "/MiniatureCrafts/product/**",
                                "/MiniatureCrafts/fetch_products"


                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/v1/cart/**",
                                "/MiniatureCrafts/history/**"
                        ).authenticated()
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://192.168.1.11:8082/",
                "http://127.0.0.1:5502/",
                "http://localhost:5174/",
                "http://localhost:5173/",
                "http://localhost:3000/",
                "http://localhost:8081/",
                "http://localhost:8082/"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}