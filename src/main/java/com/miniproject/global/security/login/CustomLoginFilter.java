package com.miniproject.global.security.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.member.repository.MemberRepository;
import com.miniproject.domain.member.request.LoginRequest;
import com.miniproject.domain.member.request.LoginResponse;
import com.miniproject.global.config.CustomHttpHeaders;
import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.service.JwtService;
import com.miniproject.global.jwt.service.TokenPair;
import com.miniproject.global.util.ResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Slf4j
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService jwtService;

    public CustomLoginFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super("/api/v1/members/login");
        setAuthenticationManager(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {

        InputStream inputStream = request.getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);

        String username = loginRequest.email();
        String password = loginRequest.password();

        return getAuthenticationManager().authenticate(
                CustomLoginToken.unAuthenticate(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String email = (String) authResult.getPrincipal();
        TokenPair tokenPair = jwtService.createTokenPair(new JwtPayload(email, new Date()));
        response.setHeader(
                CustomHttpHeaders.ACCESS_TOKEN, tokenPair.accessToken());
        response.setHeader(CustomHttpHeaders.REFRESH_TOKEN, tokenPair.refreshToken());

        var loginResponse = jwtService.createLoginResponse(email);
        ResponseDTO<LoginResponse> responseBody = ResponseDTO.res("로그인이 완료되었습니다.",loginResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseBody);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(jsonResponse);
    }
}

