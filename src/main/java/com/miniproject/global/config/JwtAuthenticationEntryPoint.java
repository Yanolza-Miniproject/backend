package com.miniproject.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.global.jwt.exception.BadTokenException;
import com.miniproject.global.jwt.exception.TokenExpiredException;
import com.miniproject.global.util.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String errorMessage;

        if (authException instanceof BadTokenException) {
            log.error("BadTokenException", authException);
            log.error("Request Uri : {}", request.getRequestURI());
            errorMessage = "토큰이 유효하지 않습니다. ";

        } else if (authException instanceof TokenExpiredException) {
            log.error("TokenExpiredException", authException);
            log.error("Request Uri : {}", request.getRequestURI());
            errorMessage = "토큰 기간이 만료되었습니다. ";

        } else if (authException instanceof BadCredentialsException) {
            log.error("BadCredentialsException", authException);
            log.error("Request Uri : {}", request.getRequestURI());
            errorMessage = "비밀번호가 맞지 않습니다. ";
        } else {
            errorMessage = "유효하지 않은 입력입니다. ";
        }

        ResponseDTO<?> responseBody = ResponseDTO.res(errorMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseBody.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(jsonResponse);
    }
}
