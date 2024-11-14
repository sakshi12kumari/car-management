package com.car_management.car_management.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure key generation
    private static final String DELIMITER = " : ";

    public String generateToken(String email, String password) {
        return Jwts.builder()
                .setSubject(email + DELIMITER + password )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000)) // 10 hours expiration
                .signWith(SECRET_KEY)
                .compact();
    }

    public String validateTokenAndGetEmail(String token) {
        try {
            String subject = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // Retrieves the subject (email) from the token if valid

            return subject.split(DELIMITER)[0];
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Handle token expiration specifically
            throw new RuntimeException("Token has expired");
        } catch (io.jsonwebtoken.JwtException e) {
            // Handle other JWT exceptions (e.g., malformed token)
            throw new RuntimeException("Invalid token", e);
        }
    }
}
