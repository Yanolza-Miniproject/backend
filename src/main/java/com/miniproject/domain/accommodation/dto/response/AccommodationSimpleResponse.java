package com.miniproject.domain.accommodation.dto.response;

import com.miniproject.domain.accommodation.dto.AccommodationDTO;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.room.dto.RoomDTO;
import com.miniproject.domain.room.entity.Room;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AccommodationSimpleResponse(
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
        Integer likeCount,
        Integer lowest_price,
        Integer viewCount

) {

    public static AccommodationSimpleResponse fromEntity(Accommodation entity, Integer lowestPrice) {

        return AccommodationSimpleResponse.builder()
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
                .likeCount(entity.getLikeCount())
                .viewCount(entity.getViewCount())
                .lowest_price(lowestPrice)
                .build();
    }
}
