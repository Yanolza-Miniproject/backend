package com.miniproject.global.security.login;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(""));
        return new AccountContext(member.getEmail(), member.getPassword(),
                Set.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
