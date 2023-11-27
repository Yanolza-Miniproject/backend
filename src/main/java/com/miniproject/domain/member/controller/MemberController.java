package com.miniproject.domain.member.controller;

import com.miniproject.domain.member.request.SignUpRequest;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseDTO<Long> singUp(
            @Valid  @RequestBody SignUpRequest request
            ){
        return ResponseDTO.res("가입 성공!", memberService.signUp(request));
    }

}
