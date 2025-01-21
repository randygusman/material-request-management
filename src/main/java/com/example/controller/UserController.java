package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserResponse;
import com.example.service.CustomUserDetailsService;
import com.example.util.JwtTokenUtil;

@RestController
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/api/user/me")
    public ResponseEntity<UserResponse> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            // Extract JWT token from Authorization header
            String jwt = token.replace("Bearer ", "");

            // Get username from token
            String username = jwtTokenUtil.getUsernameFromToken(jwt);

            // Load user details from the service
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Get role from the authorities assigned to the user
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_USER"); // Default to "ROLE_USER" if no role is found

            // Menghilangkan prefix "ROLE_"
            role = role.replace("ROLE_", "");

            // Create a UserResponse object
            UserResponse userResponse = new UserResponse(username, role);

            // Return the response with status OK
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            // Handle error (invalid token, user not found, etc.)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Tambahkan endpoint lainnya di sini
}
