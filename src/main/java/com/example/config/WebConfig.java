package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Mengatur CORS untuk menerima permintaan dari frontend yang berjalan di
        // localhost:3000
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Mengizinkan permintaan dari frontend di localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Mengizinkan metode HTTP tertentu
                .allowedHeaders("*") // Mengizinkan semua header
                .allowCredentials(true); // Jika perlu mengirimkan cookie atau token
    }
}
