package com.react.sachin.LoginSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.react.sachin.JWTFeature.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
   
     @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    // BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //// Spring Security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                    // Disable CSRF for REST API
           // .csrf(csrf -> csrf.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
             // JWT does not use server session
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            
            .authorizeHttpRequests(auth -> auth
                 // Login is public
                .requestMatchers("/login").permitAll()

                 // Everything else requires JWT
                .anyRequest().authenticated() // only authanticalte are allowed
                 //.anyRequest().permitAll() // for all allowed
            )
             // JWT filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );
            

        return http.build();
    }  
    
   // =========================
    // CORS CONFIGURATION
    // =========================
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource
            corsConfigurationSource() {

        org.springframework.web.cors.CorsConfiguration configuration =
                new org.springframework.web.cors.CorsConfiguration();

        configuration.setAllowedOrigins(
                java.util.List.of(
                    "http://localhost:5173"
                )
        );

        configuration.setAllowedMethods(
                java.util.List.of(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                java.util.List.of("*")
        );

        configuration.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }  
}
