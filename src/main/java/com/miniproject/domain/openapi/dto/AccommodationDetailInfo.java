package com.miniproject.domain.openapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AccommodationDetailInfo(
        String contentid, // 숙소 id
        String roomtitle, // 객실명
        Integer roomsize1, // 객실크기
        Integer roomcount, // 객실수
        Integer roombasecount, // 기준인원
        Integer roomoffseasonminfee1, // 비수기 주중 최소값
        Integer roomoffseasonminfee2, // 비수기 주말 최소값
        Integer roompeakseasonminfee1, // 성수기 주중 최소값
        Integer roompeakseasonminfee2, // 성수기 주말 최소값
        Boolean roomtv, // tv 유무
        Boolean roompc, // pc 유무
        Boolean roominternet, // 인터넷 유무
        Boolean roomrefrigerator, // 냉장고 유무
        Boolean roombathfacility, // 목욕시설
        Boolean roomhairdryer, // 드라이 유무
        List<RoomImageUrlDto> roomImages

) {
}
