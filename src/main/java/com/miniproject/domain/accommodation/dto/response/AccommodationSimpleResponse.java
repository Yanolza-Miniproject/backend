package com.miniproject.domain.accommodation.dto.response;

import com.miniproject.domain.accommodation.entity.Accommodation;
import lombok.Builder;

import java.time.LocalDateTime;

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
        Integer wishCount,
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
                .wishCount(entity.getWishCount())
                .viewCount(entity.getViewCount())
                .lowest_price(lowestPrice)
                .build();
    }
}
