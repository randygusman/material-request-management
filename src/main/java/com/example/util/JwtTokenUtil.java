package com.example.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final SecretKey key;

    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;

    // Constructor to initialize the secret key
    public JwtTokenUtil() {
        // Generate a secure key for HS512 algorithm
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Key with secure length for HS512
    }

    // Generate token
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512) // Using the secure key for HS512
                .compact();
    }

    // Get username from token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }

    // Refresh token: generates a new JWT token using the old token
    public String refreshToken(String token) {
        try {
            // Parse the claims without validating expiration
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Generate a new token with the same claims but new expiration
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired. Cannot refresh.");
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid token. Cannot refresh.");
        }
    }
}
