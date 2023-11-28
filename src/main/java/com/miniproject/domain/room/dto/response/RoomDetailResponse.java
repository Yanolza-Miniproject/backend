package com.miniproject.domain.room.dto.response;

import com.miniproject.domain.room.entity.Room;
import lombok.Builder;

@Builder
public record RoomDetailResponse(
        Long id,
        String name,
        Integer price,
        Integer capacity,
        Integer inventory,
        Boolean categoryTv,
        Boolean categoryPc,
        Boolean categoryInternet,
        Boolean categoryRefrigerator,
        Boolean categoryBathingFacilities,
        Boolean categoryDryer
) {

    public static RoomDetailResponse fromEntity(Room entity) {
        return RoomDetailResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .capacity(entity.getCapacity())
                .inventory(entity.getInventory())
                .categoryTv(entity.isCategoryTv())
                .categoryPc(entity.isCategoryPc())
                .categoryInternet(entity.isCategoryInternet())
                .categoryRefrigerator(entity.isCategoryRefrigerator())
                .categoryBathingFacilities(entity.isCategoryBathingFacilities())
                .categoryDryer(entity.isCategoryDryer())
                .build();
    }
}
