package com.miniproject.domain.member.service;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.DuplicateEmailException;
import com.miniproject.domain.member.exception.MemberNotFoundException;
import com.miniproject.domain.member.repository.MemberRepository;
import com.miniproject.domain.member.request.SignUpRequest;
import com.miniproject.global.jwt.service.JwtService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public Long signUp(SignUpRequest request) {
        validateDuplicateMember(request.email());

        Member member = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .phoneNumber(request.phoneNumber())
                .build();

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public Member getMemberByLoginInfo(LoginInfo loginInfo){
        return memberRepository.findByEmail(loginInfo.username())
            .orElseThrow(MemberNotFoundException::new);
    }

    private void validateDuplicateMember(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new DuplicateEmailException();
        }
    }

    public void logout(
            @SecurityContext LoginInfo loginInfo
    ) {
        jwtService.deleteRefreshToken(loginInfo.username());
    }
}
