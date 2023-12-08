package com.miniproject.domain.openapi.dto;

import com.miniproject.domain.accommodation.entity.AccommodationType;
import lombok.Builder;

@Builder
public record AccommodationCommon(
        String contentid, // 숙소 id
        String title, // 숙소명
        String tel, // 전화 번호
        String homepage, // 홈페이지
        String firstimage, // 섬네일
        String addr1, // 메인 주소
        String addr2, // 서브 주소 (없는 경우가 많음)
        String mapx, // x좌표
        String mapy, // y좌표
        String overview, // 세부설명
        AccommodationType accommodationType // 소분류(숙소타입)
) {



}
