package com.miniproject.global.jwt.service;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.repository.MemberRepository;
import com.miniproject.domain.member.request.LoginResponse;
import com.miniproject.domain.refresh.entity.RefreshToken;
import com.miniproject.domain.refresh.exception.RefreshTokenException;
import com.miniproject.domain.refresh.exception.RefreshTokenNotFoundException;
import com.miniproject.domain.refresh.repository.RefreshTokenRepository;
import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.JwtProvider;
import com.miniproject.global.jwt.api.RefreshTokenRequest;
import com.miniproject.global.security.jwt.JwtPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Transactional
@Service
public class JwtService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtService(JwtProvider jwtProvider, MemberRepository memberRepository,
                      RefreshTokenRepository refreshTokenRepository,
                      @Value("${service.jwt.access-expiration}") long accessExpiration,
                      @Value("${service.jwt.refresh-expiration}") long refreshExpiration) {
        this.jwtProvider = jwtProvider;
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }


    public TokenPair createTokenPair(JwtPayload jwtPayload) {
        String accessToken = jwtProvider.createToken(jwtPayload, accessExpiration);
        String refreshToken = jwtProvider.createToken(jwtPayload, refreshExpiration);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberEmail(jwtPayload.email())
                        .token(refreshToken)
                        .expiryDate(LocalDateTime.now().plusNanos(refreshExpiration * 1000000))
                        .build());

        //토큰 쌍 생성 후 db에 저장
        return new TokenPair(accessToken, refreshToken);
    }

    public JwtPayload verifyToken(String token) {
        return jwtProvider.verifyToken(token);
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteRefreshTokenByMemberEmail(email);
    }

    public JwtPair refreshAccessToken(RefreshTokenRequest request) {

        //요청에 포함된 리프레시 토큰을 검증 후 payload 가져옴
        JwtPayload jwtPayload = verifyToken(request.refreshToken());

        //DB에 저장된 member의 리프레시 토큰을 꺼내옴

        var refreshToken = refreshTokenRepository.findRefreshTokenByMemberEmail(jwtPayload.email());

        if (refreshToken.getToken() == null) {
            throw new RefreshTokenNotFoundException();
        }
        String savedTokenInfo = refreshToken.getToken();

        if (!isAcceptable(savedTokenInfo, request.refreshToken())) {
            throw new RefreshTokenException();
        }

        //새로운 액세스 토큰 발급
        String refreshedAccessToken = jwtProvider.reCreateToken(jwtPayload, accessExpiration);

        deleteExpiredTokens();

        return new JwtPair(refreshedAccessToken, request.refreshToken());
    }

    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        refreshTokenRepository.deleteAllExpiredSince(now);
    }

    public LoginResponse createLoginResponse(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        return new LoginResponse(member.getId(), member.getNickname());
    }

    private boolean isAcceptable(String one, String other) {
        return one.equals(other);
    }


}
