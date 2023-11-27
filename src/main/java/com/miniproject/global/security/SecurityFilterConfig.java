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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityFilterConfig {

    private final CustomLoginFilter customLoginFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint entryPoint;

    public SecurityFilterConfig(CustomLoginFilter customLoginFilter, JwtAuthenticationFilter jwtAuthenticationFilter,
                                JwtAuthenticationEntryPoint entryPoint) {
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
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/accommodations"), new AntPathRequestMatcher("/api/v1/accommodations/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/members/join"), new AntPathRequestMatcher("/api/v1/members/login", "/api/v1/refresh")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/rooms"), new AntPathRequestMatcher("/api/v1/rooms/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/rooms/{room_id}/orders")).authenticated()
                        .anyRequest().authenticated()
        );

        http.addFilterAfter(jwtAuthenticationFilter, ExceptionTranslationFilter.class);
        http.addFilterAfter(customLoginFilter, JwtAuthenticationFilter.class);

        http.exceptionHandling((exception)-> exception.authenticationEntryPoint(entryPoint));
        http.exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint));	// 추가
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher( "/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher( "/css/**"))
                .requestMatchers(new AntPathRequestMatcher( "/js/**"))
                .requestMatchers(new AntPathRequestMatcher( "/img/**"))
                .requestMatchers(new AntPathRequestMatcher( "/lib/**"));
    }

}
