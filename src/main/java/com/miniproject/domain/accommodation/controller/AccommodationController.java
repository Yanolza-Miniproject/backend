package com.miniproject.domain.accommodation.controller;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
import com.miniproject.domain.accommodation.service.AccommodationService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accommodations")
@RestController
public class AccommodationController {

    private final AccommodationService accommodationService;


    // 숙소 단일 조회
    @GetMapping("/{accommodationId}")
    public ResponseEntity<ResponseDTO<AccommodationDetailResponse>> getAccommodation(@PathVariable Long accommodationId,
                                                                                     @SecurityContext LoginInfo loginInfo) {
        log.info(loginInfo.username());
        return ResponseEntity.ok(
                ResponseDTO.res("성공", accommodationService.getAccommodationWithRoomById(accommodationId, loginInfo))
        );
    }

    // 숙소 전체 조회 + 필터링
    // 주차, 조리, 픽업, 지역(일단 도, 특별시 기준)
    @GetMapping
    public ResponseEntity<ResponseDTO<List<AccommodationSimpleResponse>>> getAccommodations(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, name = "category-parking") Integer categoryParking,
            @RequestParam(required = false, name = "category-cooking") Integer categoryCooking,
            @RequestParam(required = false, name = "category-pickup") Integer categoryPickup,
            @RequestParam(required = false, name = "wish-count") Integer wishCount,
            @RequestParam(required = false, name = "region01") Integer region01,
            @SecurityContext LoginInfo loginInfo
    ) {

//        Sort sort = Sort.by("name").ascending(); // 만약 페이지 네이션에 정렬이 필요한 경우
        Pageable pageable = PageRequest.of(page, 20);

        Page<AccommodationSimpleResponse> accommodationPage =
                accommodationService.getAccommodations(
                        pageable,
                        categoryParking,
                        categoryCooking,
                        categoryPickup,
                        wishCount,
                        region01,
                        loginInfo);

        List<AccommodationSimpleResponse> accommodationList = accommodationPage.getContent();

        return ResponseEntity.ok(
                ResponseDTO.res("성공", accommodationList)
        );
    }
}
