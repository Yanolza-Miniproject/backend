package com.miniproject.domain.orders.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.orders.dto.response.OrdersResponseDto;
import com.miniproject.domain.orders.service.OrdersService;
import com.miniproject.domain.payment.service.PaymentService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;
    private final PaymentService paymentService;
    private final MemberService memberService;


    @GetMapping("/{orders_id}")
    public ResponseEntity<ResponseDTO<OrdersResponseDto>> getOrder
        (@PathVariable Long orders_id,@SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        return ResponseEntity.ok()
            .body(ResponseDTO.res("주문 상세 조회 완료", ordersService.getOrder(orders_id,member)));

    }

    @PostMapping("/{orders_id}/payments")
    public ResponseEntity<ResponseDTO> paymentOrder
        (@PathVariable Long orders_id, @SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        Long paymentId = ordersService.registerPayment(orders_id,member);
        paymentService.completePayment(paymentId, member);
        return ResponseEntity.created(URI.create("/api/v1/payment/" + paymentId))
            .body(ResponseDTO.res("결제 완료", paymentId));
    }


    @DeleteMapping("/{orders_id}")
    public ResponseEntity<ResponseDTO> deleteOrders
        (@PathVariable Long orders_id, @SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        ordersService.deleteOrders(orders_id, member);
        return ResponseEntity.ok()
            .body(ResponseDTO.res("주문 취소"));
    }


}
