package com.example.hp_29_MiniatureCrafts.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/images/**") // Truy cập bằng /images/ trên web
                .addResourceLocations("file:D:/DoAnTotNghiep/DATN_HP_29/DoAnTotNghiep_MiniatureCrafts/upload/images/"); // Đường dẫn thực tế
    }
}