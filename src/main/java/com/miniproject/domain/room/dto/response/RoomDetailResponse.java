package com.miniproject.domain.room.dto.response;

import com.miniproject.domain.room.dto.RoomInventoryDTO;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInventory;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomDetailResponse(
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
        Boolean categoryDryer,
        List<RoomInventoryDTO> roomInventories
) {

    public static RoomDetailResponse fromEntity(Room entity) {

        List<RoomInventory> roomInventories = entity.getRoomInventories();
        List<RoomInventoryDTO> roomInventoryDTOList = roomInventories.stream()
                .map(RoomInventoryDTO::fromEntity)
                .toList();

        return RoomDetailResponse.builder()
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
                .roomInventories(roomInventoryDTOList)
                .build();
    }
}
