package com.miniproject.domain.basket.controller;

import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/baskets")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping
    public ResponseEntity<ResponseDTO<BasketResponseDto>> getBasket(){
        return ResponseEntity.ok().body(ResponseDTO
                .res("장바구니 조회 성공", basketService.getBasket()));
    }

//    @PostMapping("/orders")
//    public ResponseEntity<ResponseDTO> orderBasket(@RequestBody CheckBasketRequestDto dto) {
//
//    }


}
