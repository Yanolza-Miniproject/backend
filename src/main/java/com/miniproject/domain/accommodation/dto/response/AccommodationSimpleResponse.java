package com.miniproject.domain.accommodation.dto.response;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Builder
public record AccommodationSimpleResponse(
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
        Boolean isWish,
        Integer lowest_price,
        Integer viewCount

) {

    public static AccommodationSimpleResponse fromEntity(Accommodation entity) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedCheckIn = entity.getCheckIn().format(formatter);
        String formattedCheckOut = entity.getCheckOut().format(formatter);

        return AccommodationSimpleResponse.builder()
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
                .build();
    }

    public static AccommodationSimpleResponse fromEntity(Accommodation entity, Integer lowestPrice, boolean isWish) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedCheckIn = entity.getCheckIn().format(formatter);
        String formattedCheckOut = entity.getCheckOut().format(formatter);

        return AccommodationSimpleResponse.builder()
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
                .isWish(isWish)
                .lowest_price(lowestPrice)
                .build();
    }
}
