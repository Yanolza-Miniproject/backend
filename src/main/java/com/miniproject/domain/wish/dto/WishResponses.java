//package com.miniproject.domain.wish.dto;
//
//import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
//import com.miniproject.domain.wish.entity.Wish;
//import lombok.Getter;
//
//public class WishResponses {
//
//    @Getter
//    public static class AccommodationWishResDto {
//
//        private final AccommodationSimpleResponse accommodation;
//
//        private AccommodationWishResDto(AccommodationSimpleResponse accommodation) {
//            this.accommodation = accommodation;
//        }
//
//        public static AccommodationWishResDto fromEntity(Wish wish) {
//            return new AccommodationWishResDto(
//                    AccommodationSimpleResponse.fromEntity(wish.getAccommodation())
//            );
//        }
//
//    }
//}
