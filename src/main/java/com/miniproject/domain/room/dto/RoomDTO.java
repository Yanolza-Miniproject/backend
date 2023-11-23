package com.miniproject.domain.room.dto;

import com.miniproject.domain.room.entity.Room;
import lombok.Builder;

@Builder
public record RoomDTO(
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

    public static RoomDTO fromEntity(Room entity) {
        return RoomDTO.builder()
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
