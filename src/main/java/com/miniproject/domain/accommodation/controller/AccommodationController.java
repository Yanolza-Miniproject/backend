package com.miniproject.domain.accommodation.controller;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accommodation")
@RestController
public class AccommodationController {

    private final AccommodationService accommodationService;

    // 숙소 단일 조회
    @GetMapping("/{accommodationId}")
    public ResponseEntity<AccommodationDetailResponse> getAccommodation(@PathVariable Long accommodationId) {
        log.info(accommodationId.toString());

        return ResponseEntity.ok(
                accommodationService.getAccommodationWithRoomById(accommodationId)
        );

    }


    // 숙소 전체 조회


    // 숙소 필터링 전체 조회






}
