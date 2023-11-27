package com.miniproject.global.security.jwt;

import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.exception.BadTokenException;
import com.miniproject.global.jwt.exception.TokenExpiredException;
import com.miniproject.global.jwt.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        log.info("JwtAuthenticationProvider.authenticate 시작");
        String accessToken = (String) authentication.getCredentials();
        log.info("accessToken = {}", accessToken);

        JwtPayload jwtPayload = jwtService.verifyToken(accessToken);
        return JwtAuthenticationToken.authorize(jwtPayload.email());

//        try{
//        } catch (AuthenticationException e) {
//            log.error("에러캐치");
//            throw new BadTokenException(e.getMessage());
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
