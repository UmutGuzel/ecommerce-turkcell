package com.turkcell.ecommerce.util.security;

import io.jsonwebtoken.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  private final JwtFilter jwtFilter;
  public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
//                    .requestMatchers("/api/v1/user/login", "/api/v1/user/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/user/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAnyAuthority("admin")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAnyAuthority("admin")
                    .requestMatchers("/api/v1/**").authenticated()
                    .anyRequest().permitAll()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
