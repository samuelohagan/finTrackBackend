package com.samproject.financeapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        RequestMatcher publicEndpointsMatcher = new AntPathRequestMatcher("/validateGoogleToken/**");

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(publicEndpointsMatcher).permitAll()
                .anyRequest().authenticated());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}