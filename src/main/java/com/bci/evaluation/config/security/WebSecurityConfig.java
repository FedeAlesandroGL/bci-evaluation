package com.bci.evaluation.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  private final JwtRequestFilter jwtRequestFilter;

  public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      @Lazy JwtRequestFilter jwtRequestFilter) {
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
            .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/authentication").permitAll()
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(jwtAuthenticationEntryPoint));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
