package com.miniproject.domain.room.dto.response;

import com.miniproject.domain.room.dto.RoomInventoryDTO;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInventory;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomSimpleResponse(
        Long id,
        String accommodationName,
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

    public static RoomSimpleResponse fromEntity(Room entity) {

        return RoomSimpleResponse.builder()
                .id(entity.getId())
                .accommodationName(entity.getAccommodation().getName())
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
