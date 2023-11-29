package com.miniproject.domain.room.dto;

import com.miniproject.domain.room.entity.RoomInventory;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RoomInventoryDTO(
        Long id,
        LocalDate date,
        Integer inventory
) {

    public static RoomInventoryDTO fromEntity(RoomInventory entity) {

        return RoomInventoryDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .inventory(entity.getInventory())
                .build();

    }


}
