package com.miniproject.domain.accommodation.dto;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.room.dto.RoomDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AccommodationDTO(
        Long id,
        String name,
        String type,
        String address,
        String phoneNumber,
        String homepage,
        String infoDetail,
        String thumbnailUrl,
        Boolean categoryParking,
        Boolean categoryCooking,
        Boolean categoryPickup,
        Boolean categoryAmenities,
        Boolean categoryDiningArea,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Integer wishCount,
        Integer viewCount,
        List<RoomDTO> rooms

) {
    public static AccommodationDTO fromEntity(Accommodation entity) {
        List<RoomDTO> rooms = entity.getRooms()
                .stream()
                .map(RoomDTO::fromEntity)
                .toList();

        return AccommodationDTO.builder()
                .id(entity.getId())
                .rooms(rooms)
                .name(entity.getName())
                .type(entity.getType())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .homepage(entity.getHomepage())
                .infoDetail(entity.getInfoDetail())
                .thumbnailUrl(entity.getThumbnailUrl())
                .categoryParking(entity.isCategoryParking())
                .categoryCooking(entity.isCategoryCooking())
                .categoryPickup(entity.isCategoryPickup())
                .categoryAmenities(entity.isCategoryAmenities())
                .categoryDiningArea(entity.isCategoryDiningArea())
                .checkIn(entity.getCheckIn())
                .checkOut(entity.getCheckOut())
                .wishCount(entity.getWishCount())
                .viewCount(entity.getViewCount())
                .build();
    }
}
