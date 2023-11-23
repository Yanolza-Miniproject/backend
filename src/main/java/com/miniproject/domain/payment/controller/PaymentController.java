package com.miniproject.domain.payment.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.payment.service.PaymentService;
import com.miniproject.global.util.ResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPayments(@PathVariable Long payment_id,Member member) {

        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 전체 불러오기", paymentService.getPayments(member)));
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<ResponseDTO> getPayment(@PathVariable Long payment_id,Member member) {
        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 불러오기 성공", paymentService.getPayment(payment_id,member)));
    }

    @PostMapping("/{payment_id}")
    public ResponseEntity<ResponseDTO> completePayment(@PathVariable Long payment_id,
        Member member) {
        paymentService.completePayment(payment_id, member);
        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 완료"));
    }

    @PostMapping("/{payment_id}")
    public ResponseEntity<ResponseDTO> deletePayment(@PathVariable Long payment_id, Member member) {
        paymentService.deletePayment(payment_id, member);
        return ResponseEntity.ok()
            .body(ResponseDTO.res("결제 취소"));
    }


}
