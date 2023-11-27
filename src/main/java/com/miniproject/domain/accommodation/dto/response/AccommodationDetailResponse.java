package com.miniproject.domain.accommodation.dto.response;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.room.dto.RoomDTO;
import com.miniproject.domain.room.entity.Room;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record AccommodationDetailResponse(
        Long id,
        String name,
        Integer type,
        String address,
        String phoneNumber,
        String homepage,
        String infoDetail,
        String thumbnailUrl,
        Boolean categoryParking,
        Boolean categoryCooking,
        Boolean categoryPickup,
        String categoryAmenities,
        String categoryDiningArea,
        String checkIn,
        String checkOut,
        Integer wishCount,
        Integer viewCount,
        Integer lowest_price,
        List<RoomDTO> rooms


) {

    public static AccommodationDetailResponse formEntity(Accommodation entity, Integer lowestPrice) {

        List<Room> rooms = entity.getRooms();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(RoomDTO::fromEntity)
                .toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedCheckIn = entity.getCheckIn().format(formatter);
        String formattedCheckOut = entity.getCheckOut().format(formatter);

        return AccommodationDetailResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType().getIndex())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .homepage(entity.getHomepage())
                .infoDetail(entity.getInfoDetail())
                .thumbnailUrl(entity.getThumbnailUrl())
                .categoryParking(entity.isCategoryParking())
                .categoryCooking(entity.isCategoryCooking())
                .categoryPickup(entity.isCategoryPickup())
                .categoryAmenities(entity.getCategoryAmenities())
                .categoryDiningArea(entity.getCategoryDiningArea())
                .checkIn(formattedCheckIn)
                .checkOut(formattedCheckOut)
                .wishCount(entity.getWishCount())
                .viewCount(entity.getViewCount())
                .lowest_price(lowestPrice)
                .rooms(roomDTOs)
                .build();
    }
}
