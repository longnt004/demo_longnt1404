package com.fpoly.demo_longnt1404.config;



import com.fpoly.demo_longnt1404.service.impl.CustomUserDetailService;
import com.fpoly.demo_longnt1404.utlis.CustomAuthenticationEntryPoint;
import com.fpoly.demo_longnt1404.utlis.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtRequestFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder, JwtAuthenticationFilter jwtRequestFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customUserDetailService = customUserDetailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtRequestFilter = jwtRequestFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configuring CSRF: Cross-Site Request Forgery (Attack forging requests to the server)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Endpoints that do not require authentication
                        .requestMatchers(
                                "/api/users",                          // Sign up (create user)
                                "/api/password-recovery",              // Password recovery (send OTP to email)
                                "/api/sessions",                       // Login (create session)
                                "/api/users/verification/**",          // Verify account (verify OTP code)
                                "/api/password/change/**"              // Change password (change password)
                        ).permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                // Configuring exception handling
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        // Custom authentication entry point
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        // Custom access denied handler
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            throw new AccessDeniedException("You do not have permission to access this resource");
                        })
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        // STATELESS: No session will be created or used by Spring Security
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Add JWT filter to Filter Chain
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(
                        withDefaults()
                )
                .logout(logout -> logout.logoutUrl("/api/v2/logout").permitAll())
                .httpBasic(withDefaults());

        return http.build();
    }

    // AuthenticationManager used to authenticate the user
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configure user authentication from the database
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
}
