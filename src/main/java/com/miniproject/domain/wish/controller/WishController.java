package com.miniproject.domain.wish.controller;

import com.miniproject.domain.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wish")
public class WishController {

    private final WishService wishService;

    // 좋아요 누르기
    @PostMapping
    void like() {

    }

    // 좋아요 취소
    @PostMapping
    void disLike() {

    }

    // 회원의 좋아요 리스트 조회
    @GetMapping
    void getList() {

    }

}
