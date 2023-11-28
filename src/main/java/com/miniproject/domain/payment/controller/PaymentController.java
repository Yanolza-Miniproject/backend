package com.miniproject.domain.payment.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.payment.service.PaymentService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final MemberService memberService;
    @GetMapping
    public ResponseEntity<ResponseDTO> getPayments(@SecurityContext LoginInfo loginInfo,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "20")int pageSize) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 전체 불러오기",
                paymentService.getPayments(page,pageSize,member)));
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<ResponseDTO> getPayment
        (@PathVariable Long payment_id,@SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 불러오기 성공",
                paymentService.getPayment(payment_id,member)));
    }


}
