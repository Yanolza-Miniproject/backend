package com.miniproject.domain.openapi.dto;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record AccommodationIntro(
        String contentid,
        Integer roomcount, // 객실 수
        Integer accomcountlodging, // 수용가능 인원수
        Integer scalelodging, // 규모
        String roomtype, // 객실 유형 -
        LocalTime checkintime, // 체크인 -
        LocalTime checkouttime, // 체크아웃 -
        Boolean parkinglodging, // 주차 가능 (가능, 불가) -
        Boolean chkcooking, // 조리 가능 -
        Boolean pickup, // 픽업서비스 -
        String foodplace, // 식음료장 -
        String subfacility // 부대시설 -
) {
}
