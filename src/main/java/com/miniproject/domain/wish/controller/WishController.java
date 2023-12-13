package com.miniproject.domain.wish.controller;

import com.miniproject.domain.wish.service.WishService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.miniproject.domain.wish.dto.WishResponses.AccommodationWishResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wish")
public class WishController {

    private final WishService wishService;

    @PostMapping("/{accommodation_id}")
    public ResponseDTO addWish(@PathVariable(name = "accommodation_id") Long accommodationId,
                               @SecurityContext LoginInfo loginInfo) {
        wishService.saveWish(accommodationId, loginInfo);
        return ResponseDTO.res("좋아요 성공");
    }

    @DeleteMapping("/{accommodation_id}")
    public ResponseDTO cancelWish(@PathVariable(name = "accommodation_id") Long accommodationId,
                                  @SecurityContext LoginInfo loginInfo) {
        wishService.deleteWish(accommodationId, loginInfo);

        return ResponseDTO.res("좋아요 취소");
    }

    @GetMapping
    public ResponseDTO<List<AccommodationWishResDto>> getWishes(@SecurityContext LoginInfo loginInfo) {
        return ResponseDTO.res("좋아요 리스트 조회 성공", wishService.getWishes(loginInfo));
    }
}
