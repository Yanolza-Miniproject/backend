package com.miniproject.domain.wish.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.wish.dto.WishResponses;
import com.miniproject.domain.wish.entity.Wish;
import com.miniproject.domain.wish.service.WishService;
import com.miniproject.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.miniproject.domain.wish.dto.WishResponses.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wish")
public class WishController {

    private final WishService wishService;

    // 좋아요 누르기
    @PostMapping("/{accommodation_id}")
    public ResponseDTO addWish(@PathVariable(name = "accommodation_id") Long accommodationId,
                              @AuthenticationPrincipal Member member) {
        wishService.saveWish(accommodationId, member);
        return ResponseDTO.res("좋아요 성공");
    }

    // 좋아요 취소
    @DeleteMapping("/{accommodation_id}")
    public ResponseDTO cancelWish(@PathVariable(name = "accommodation_id") Long accommodationId,
                        @AuthenticationPrincipal Member member) {
        wishService.deleteWish(accommodationId, member);
        return ResponseDTO.res("좋아요 취소");
    }

    // 회원의 좋아요 리스트 조회
    @GetMapping
    public ResponseDTO<List<AccommodationWishResDto>> getWishes(@AuthenticationPrincipal Member member) {
        return ResponseDTO.res("좋아요 리스트 조회 성공", wishService.getWishes(member));
    }
}
