package com.miniproject.domain.orders.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.dto.response.OrdersResponseDto;
import com.miniproject.domain.orders.service.OrdersService;
import com.miniproject.global.util.ResponseDTO;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/{orders_id}")
    public ResponseEntity<ResponseDTO<OrdersResponseDto>> getOrder(@PathVariable Long orders_id,Member member) {

        return ResponseEntity.ok()
            .body(ResponseDTO.res("주문 상세 조회 완료", ordersService.getOrder(orders_id,member)));

    }

    @PostMapping("/{orders_id}/payment")
    public ResponseEntity<ResponseDTO> paymentOrder(@PathVariable Long orders_id
        ,Member member) {
        Long paymentId = ordersService.registerPayment(orders_id,member);
        return ResponseEntity.created(URI.create("api/v1/orders/{orders_id}/payment/" + paymentId))
            .body(ResponseDTO.res("결제 생성 완료", paymentId));
    }

    @DeleteMapping("/{orders_id}")
    public ResponseEntity<ResponseDTO> deleteOrders(@PathVariable Long orders_id, Member member) {
        ordersService.deleteOrders(orders_id, member);
        return ResponseEntity.ok()
            .body(ResponseDTO.res("주문 취소"));
    }


}
