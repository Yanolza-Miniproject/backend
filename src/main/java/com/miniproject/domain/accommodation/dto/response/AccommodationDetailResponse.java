package com.miniproject.domain.accommodation.dto.response;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.room.dto.RoomDTO;
import com.miniproject.domain.room.entity.Room;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AccommodationDetailResponse(
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
        Boolean isWish,
        Integer lowest_price,
        List<RoomDTO> rooms


) {

    public static AccommodationDetailResponse formEntity(Accommodation entity, Integer lowestPrice, boolean isWish) {

        List<Room> rooms = entity.getRooms();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(RoomDTO::fromEntity)
                .toList();

        return AccommodationDetailResponse.builder()
                .id(entity.getId())
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
                .isWish(isWish)
                .lowest_price(lowestPrice)
                .rooms(roomDTOs)
                .build();
    }
}
