package com.miniproject.domain.basket.controller;

import com.miniproject.domain.basket.dto.request.CheckBasketRequestDto;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/baskets")
public class BasketController {

    private final BasketService basketService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ResponseDTO<BasketResponseDto>> getBasket
        (@SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        return ResponseEntity.ok().body(ResponseDTO
            .res("장바구니 조회 성공", basketService.getBasket(member)));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> deleteRoomInBasket
        (@RequestBody CheckBasketRequestDto dto, @SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        basketService.deleteRoomInBasket(dto, member);
        return ResponseEntity.ok().body(ResponseDTO.res("장바구니 내 객실 삭제 완료"));
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseDTO> orderBasket
        (@RequestBody CheckBasketRequestDto dto, @SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        Long orderId = basketService.registerOrder(dto, member);
        return ResponseEntity.created(URI.create("/api/v1/orders/" + orderId))
            .body(ResponseDTO.res("장바구니에서 주문 생성", orderId));
    }


}
