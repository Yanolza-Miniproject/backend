package com.miniproject.domain.room.dto;

import com.miniproject.domain.room.entity.RoomImage;
import lombok.Builder;

@Builder
public record RoomImageDTO(
        Long id,
        String imageUrl
) {

    public static RoomImageDTO fromEntity(RoomImage entity) {
        return RoomImageDTO.builder()
                .id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .build();

    }

}
