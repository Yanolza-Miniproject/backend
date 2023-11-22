//package com.miniproject.domain.accommodation.dto.response;
//
//import com.miniproject.domain.accommodation.dto.AccommodationDTO;
//import com.miniproject.domain.accommodation.entity.Accommodation;
//import com.miniproject.domain.room.dto.RoomDTO;
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Builder
//public record AccommodationSimpleResponse(
//        Long id,
//        String name,
//        String type,
//        String address,
//        String phoneNumber,
//        String homepage,
//        String infoDetail,
//        String thumbnailUrl,
//        boolean categoryParking,
//        boolean categoryCooking,
//        boolean categoryPickup,
//        boolean categoryAmenities,
//        boolean categoryDiningArea,
//        LocalDateTime checkIn,
//        LocalDateTime checkOut,
//        int likeCount,
//        int viewCount
//
//) {
//
//    public static AccommodationSimpleResponse fromDto(AccommodationDTO dto) {
//        return AccommodationSimpleResponse.builder()
//                .id(dto.id())
//                .name(dto.name())
//                .type(dto.type())
//                .address(dto.address())
//                .phoneNumber(dto.phoneNumber())
//                .homepage(dto.homepage())
//                .infoDetail(dto.())
//                .thumbnailUrl(dto.getThumbnailUrl())
//                .categoryParking(dto.isCategoryParking())
//                .categoryCooking(dto.isCategoryCooking())
//                .categoryPickup(dto.isCategoryPickup())
//                .categoryAmenities(dto.isCategoryAmenities())
//                .categoryDiningArea(dto.isCategoryDiningArea())
//                .checkIn(dto.getCheckIn())
//                .checkOut(dto.getCheckOut())
//                .likeCount(dto.getLikeCount())
//                .viewCount(dto.getViewCount())
//                .build();
//    }
//}
