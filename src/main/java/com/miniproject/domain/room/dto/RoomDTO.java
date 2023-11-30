package com.miniproject.domain.room.dto;

import com.miniproject.domain.room.entity.Room;
import lombok.Builder;

import java.util.List;

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
        Boolean categoryDryer,
        List<RoomImageDTO> roomImages,
        List<RoomInventoryDTO> roomInventories
) {

    public static RoomDTO fromEntity(Room entity) {

        List<RoomImageDTO> roomImages = entity.getRoomImages()
                .stream()
                .map(RoomImageDTO::fromEntity)
                .toList();

        List<RoomInventoryDTO> roomInventories = entity.getRoomInventories()
                .stream()
                .map(RoomInventoryDTO::fromEntity)
                .toList();

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
                .roomImages(roomImages)
                .roomInventories(roomInventories)
                .build();
    }
}
