package com.miniproject.domain.payment.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.payment.service.PaymentService;
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

    @GetMapping
    public ResponseEntity<ResponseDTO> getPayments(@AuthenticationPrincipal Member member,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "20")int pageSize) {

        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 전체 불러오기",
                paymentService.getPayments(page,pageSize,member)));
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<ResponseDTO> getPayment
        (@PathVariable Long payment_id,@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 불러오기 성공",
                paymentService.getPayment(payment_id,member)));
    }


}
