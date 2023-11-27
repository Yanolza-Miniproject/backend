package com.miniproject.domain.refresh.controller;

import com.miniproject.global.jwt.api.RefreshTokenRequest;
import com.miniproject.global.security.jwt.JwtPair;
import com.miniproject.global.jwt.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtRefreshController {

    private final JwtService jwtService;

    public JwtRefreshController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/api/v1/refresh")
    public ResponseEntity<JwtPair> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jwtService.refreshAccessToken(request));
    }
}
