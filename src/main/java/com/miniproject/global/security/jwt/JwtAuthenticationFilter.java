package com.miniproject.global.security.jwt;

import com.miniproject.global.config.CustomHttpHeaders;
import com.miniproject.global.security.jwt.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        if (!hasJwtToken(request)) {
            log.info("JwtAuthenticationFilter 그냥 통과");
            chain.doFilter(request, response);
            return;
        }


        log.info("JwtAuthenticationFilter.doFilterInternal 시작");
        String accessToken = request.getHeader(CustomHttpHeaders.ACCESS_TOKEN);

        log.info("accessToken : {}", accessToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authenticationManager.authenticate(
                        JwtAuthenticationToken.unAuthorize(accessToken)));

        chain.doFilter(request, response);
    }

    private boolean hasJwtToken(HttpServletRequest request) {
        return request.getHeader(CustomHttpHeaders.ACCESS_TOKEN) != null;
    }
}
