package com.urbanvoyage.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class    SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Allow all requests without authentication
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Specify your logout URL
                        .logoutSuccessUrl("/home") // Redirect to home after successful logout
                        .permitAll() // Allow access to logout without authentication
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF if you're not using sessions

        return http.build();
    }
}
