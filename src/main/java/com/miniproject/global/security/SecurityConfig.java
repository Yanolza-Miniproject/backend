package com.miniproject.global.security;

import com.miniproject.global.config.JwtAuthenticationEntryPoint;
import com.miniproject.global.jwt.service.JwtService;
import com.miniproject.global.security.jwt.JwtAuthenticationFilter;
import com.miniproject.global.security.jwt.JwtAuthenticationProvider;
import com.miniproject.global.security.login.CustomLoginFilter;
import com.miniproject.global.security.login.CustomLoginProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public SecurityConfig(UserDetailsService userDetailsService,
                          AuthenticationConfiguration authenticationConfiguration,
                          JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CustomLoginProvider customAuthenticationProvider() {
        return new CustomLoginProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(jwtAuthenticationProvider);
        authenticationManager.getProviders().add(customAuthenticationProvider());
        return authenticationManager;
    }

    @Bean
    CustomLoginFilter customAuthenticationFilter(JwtService jwtService) throws Exception {
        return new CustomLoginFilter(authenticationManager(), jwtService);
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Bean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

}
