package com.miniproject.domain.accommodation.controller;

import com.miniproject.domain.accommodation.dto.request.AccommodationRequest;
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

        ResponseEntity<ResponseDTO<AccommodationDetailResponse>> response = ResponseEntity.ok(
                ResponseDTO.res("성공", accommodationService.getAccommodationWithRoomById(accommodationId, loginInfo))
        );

        return response;
    }

    // 타입 추가하기

    // 숙소 전체 조회 + 필터링
    // 주차, 조리, 픽업, 지역(일단 도, 특별시 기준)
    @GetMapping
    public ResponseEntity<ResponseDTO<List<AccommodationSimpleResponse>>> getAccommodations(
            Pageable pageable,
            @ModelAttribute AccommodationRequest request,
            @SecurityContext LoginInfo loginInfo
    ) {

        Page<AccommodationSimpleResponse> accommodationPage =
                accommodationService.getAccommodations(
                        pageable,
                        request,
                        loginInfo);

        List<AccommodationSimpleResponse> accommodationList = accommodationPage.getContent();

        ResponseEntity<ResponseDTO<List<AccommodationSimpleResponse>>> response = ResponseEntity
                .ok(ResponseDTO.res("성공", accommodationList));

        return response;
    }
}
