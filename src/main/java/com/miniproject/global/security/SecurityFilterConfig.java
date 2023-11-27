package com.miniproject.global.security;

import com.miniproject.global.config.JwtAuthenticationEntryPoint;
import com.miniproject.global.security.login.CustomLoginFilter;
import com.miniproject.global.security.jwt.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityFilterConfig {

    private final CustomLoginFilter customLoginFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint entryPoint;	// 추가


    public SecurityFilterConfig(CustomLoginFilter customLoginFilter, JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint entryPoint) {
        this.customLoginFilter = customLoginFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.entryPoint = entryPoint;
    }

    @Bean
    SecurityFilterChain http(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.authorizeHttpRequests(request ->
                request
                        .requestMatchers("/api/v1/accommodations", "/api/v1/accommodations/**").permitAll()
                        .requestMatchers("/api/v1/members/join", "/api/v1/members/login", "/api/v1/refresh").permitAll()
                        .requestMatchers("/api/v1/rooms", "/api/v1/rooms/**").permitAll()
                        .requestMatchers("/api/v1/rooms/{room_id}/orders").authenticated()
                        .anyRequest().authenticated()
        );

      //  http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter, ExceptionTranslationFilter.class);
        http.addFilterAfter(customLoginFilter, JwtAuthenticationFilter.class);

        http.exceptionHandling((exception)-> exception.authenticationEntryPoint(entryPoint));
        http.exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint));	// 추가
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web.ignoring()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .requestMatchers("/favicon.ico", "/resources/**", "/error")
                        .requestMatchers("/h2-console/**", "/favicon.ico", "/css/**","/js/**","/img/**","/lib/**");
    }
}
