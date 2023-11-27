package com.miniproject.domain.wish.controller;

<<<<<<< HEAD
=======
import com.miniproject.domain.member.entity.Member;
>>>>>>> 7b3b263ee821d6d268fcc56610566823330442ee
import com.miniproject.domain.wish.service.WishService;
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

    // 좋아요 누르기
    @PostMapping("/{accommodation_id}")
    public ResponseDTO addWish(@PathVariable(name = "accommodation_id") Long accommodationId,
<<<<<<< HEAD
                               @SecurityContext LoginInfo loginInfo) {
        wishService.saveWish(accommodationId, loginInfo);
=======
                               Member member) {
        wishService.saveWish(accommodationId, member);
>>>>>>> 7b3b263ee821d6d268fcc56610566823330442ee
        return ResponseDTO.res("좋아요 성공");
    }

    // 좋아요 취소
    @DeleteMapping("/{accommodation_id}")
    public ResponseDTO cancelWish(@PathVariable(name = "accommodation_id") Long accommodationId,
<<<<<<< HEAD
                                  @SecurityContext LoginInfo loginInfo) {
        wishService.deleteWish(accommodationId, loginInfo);
=======
                         Member member) {
        wishService.deleteWish(accommodationId, member);
>>>>>>> 7b3b263ee821d6d268fcc56610566823330442ee
        return ResponseDTO.res("좋아요 취소");
    }

    // 회원의 좋아요 리스트 조회
    @GetMapping
<<<<<<< HEAD
    public ResponseDTO<List<AccommodationWishResDto>> getWishes(@SecurityContext LoginInfo loginInfo) {
        return ResponseDTO.res("좋아요 리스트 조회 성공", wishService.getWishes(loginInfo));
=======
    public ResponseDTO<List<AccommodationWishResDto>> getWishes( Member member) {
        return ResponseDTO.res("좋아요 리스트 조회 성공", wishService.getWishes(member));
>>>>>>> 7b3b263ee821d6d268fcc56610566823330442ee
    }
}
