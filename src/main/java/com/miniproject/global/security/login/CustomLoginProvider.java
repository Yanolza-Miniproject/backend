package com.miniproject.global.security.login;

import com.miniproject.global.security.jwt.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class CustomLoginProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomLoginProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        log.info("CustomAuthenticationProvider.authenticate 시작");
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        log.info("userEmail : {}", username);
        log.info("userpw : {}\n", password);

        AccountContext accountContext = (AccountContext) userDetailsService
                .loadUserByUsername(username);

        log.info("userDetailsService.loadUserByUsername 이후");
        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            log.error("비밀번호가 다르네요");
            throw new BadCredentialsException("비밀번호가 맞지 않습니다. ");
        }

        return CustomLoginToken.authenticate(username, accountContext.getAuthorities());
    }



//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.isAssignableFrom(CustomLoginToken.class);
//    }


    @Override
    public boolean supports(Class<?> authentication) {
        return CustomLoginToken.class.isAssignableFrom(authentication);
    }
}

