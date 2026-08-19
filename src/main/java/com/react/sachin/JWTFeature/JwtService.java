package com.react.sachin.JWTFeature;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
   private final SecretKey secretKey =
            Keys.hmacShaKeyFor(
                "SachinReactSpringBootJwtSecretKey2026@123456789".getBytes()
            );

    // Token validity = 1 hour
    private final long expirationTime = 60 * 60 * 1000;

    public String generateToken(String username, String role) {

        Date now = new Date();

        Date expiryDate =
                new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    } 
// jwt validation /filter
 // Get username from JWT
    public String extractUsername(String token) {
        return extractAllClaims(token)
                .getSubject();
    }
      // Validate JWT
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
