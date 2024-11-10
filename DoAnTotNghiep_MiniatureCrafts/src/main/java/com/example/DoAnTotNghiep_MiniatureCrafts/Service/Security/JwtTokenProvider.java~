package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private SecretKey secretKey;

    @Value("${jwt.secret}")
    private String secretKeyString;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);

        // Ensure the key is 32 bytes (256 bits) for HS256
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 32 characters long");
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .signWith(secretKey, SignatureAlgorithm.HS256)  // Explicitly set HS256
                .compact();
    }
}
