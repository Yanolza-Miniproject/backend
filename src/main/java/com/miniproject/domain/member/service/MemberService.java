package com.miniproject.domain.member.service;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.DuplicateEmailException;
import com.miniproject.domain.member.repository.MemberRepository;
import com.miniproject.domain.member.request.SignUpRequest;
import com.miniproject.global.resolver.LoginInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long signUp(SignUpRequest request) {
        validateDuplicateMember(request.email());

        return memberRepository.save(Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .phoneNumber(request.phoneNumber())
                .build()).getId();
    }
    private void validateDuplicateMember(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new DuplicateEmailException();
        }
    }

    public Optional<Member> test(LoginInfo loginInfo) {
        return memberRepository.findByEmail(loginInfo.username());
    }
}
