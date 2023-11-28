package com.miniproject.domain.accommodation.service;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationRegionType;
import com.miniproject.domain.accommodation.exception.AccommodationNotFoundException;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.wish.service.WishService;
import com.miniproject.global.resolver.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final MemberService memberService;
    private final WishService wishService;

    @Transactional
    public AccommodationDetailResponse getAccommodationWithRoomById(Long accommodationId, LoginInfo loginInfo) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(AccommodationNotFoundException::new);

        accommodation.plusViewCount();

        Integer cheapestRoomPrice = accommodation.getRooms().stream()
                .map(Room::getPrice)
                .min(Integer::compare)
                .orElse(null);

        // 로그인 유저가 좋아요누른 숙소 ID 리스트를 받온다
        List<Long> likedAccommodationIds = wishService.getWishesOnlyAccommodationId(loginInfo);
        boolean isWished = false;
        if (likedAccommodationIds.contains(accommodation.getId())) {
            isWished = true;
        }

        return AccommodationDetailResponse.formEntity(accommodation, cheapestRoomPrice, isWished);
    }

    // 동적 쿼리가 필요한 기능이므로 querydsl 사용이 추천됨
    @Transactional
    public Page<AccommodationSimpleResponse> getAccommodations(Pageable pageable,
                                                               Integer categoryParking,
                                                               Integer categoryCooking,
                                                               Integer categoryPickup,
                                                               Integer wishCount,
                                                               Integer region01,
                                                               LoginInfo loginInfo) {

        String region = null;

        if (region01 != null) {
            region = AccommodationRegionType.findByValue(region01).getDescription();
        }

        Page<Accommodation> result = accommodationRepository
                .findByCategory(pageable, categoryParking, categoryCooking, categoryPickup, wishCount, region);

        // 로그인 유저가 좋아요누른 숙소 ID 리스트를 받온다
        List<Long> likedAccommodationIds = wishService.getWishesOnlyAccommodationId(member);

        return accommodations.map(accommodation -> {
            Integer lowestPrice = accommodation.getRooms().stream()
                    .map(Room::getPrice)
                    .min(Integer::compare)
                    .orElse(null);

            boolean isWished = false;
            if (likedAccommodationIds.contains(accommodation.getId())) {
                isWished = true;
            }

            // AccommodationSimpleResponse 객체를 생성하면서 lowest_price 값을 설정
            return AccommodationSimpleResponse.fromEntity(accommodation, lowestPrice, isWished);
        });


    }


}
