package org.example.bettingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST APIs generally disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/events/**", "/api/bets/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable())
                .sessionManagement(sessionManagement -> sessionManagement.disable());

        return http.build();
    }
}
