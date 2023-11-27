package com.miniproject.domain.accommodation.service;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.wish.service.WishService;
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

    private final WishService wishService;

    Member member = Member.builder()
            .id(1L)
            .email("member1@example.com")
            .password("1234")
            .name("테스트")
            .number("010-000-2222")
            .build();

    @Transactional
    public AccommodationDetailResponse getAccommodationWithRoomById(Long accommodationId) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow();

        // 같은 IP로 반복해서 요청이 들엉오면 제한을 거는 것 고려
        accommodation.plusViewCount();

        Integer cheapestRoomPrice = accommodation.getRooms().stream()
                .map(Room::getPrice)
                .min(Integer::compare)
                .orElse(null);

        // 로그인 유저가 좋아요누른 숙소 ID 리스트를 받온다
        List<Long> likedAccommodationIds = wishService.getWishesOnlyAccommodationId(member);
        boolean isWished = false;
        if(likedAccommodationIds.contains(accommodation.getId())) {
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
                                                               String region01) {

        Page<Accommodation> accommodations = accommodationRepository
                .findByCategory(pageable, categoryParking, categoryCooking, categoryPickup, wishCount, region01);

        // 로그인 유저가 좋아요누른 숙소 ID 리스트를 받온다
        List<Long> likedAccommodationIds = wishService.getWishesOnlyAccommodationId(member);

        return accommodations.map(accommodation -> {
            Integer lowestPrice = accommodation.getRooms().stream()
                    .map(Room::getPrice)
                    .min(Integer::compare)
                    .orElse(null);

            boolean isWished = false;
            if(likedAccommodationIds.contains(accommodation.getId())) {
                isWished = true;
            }

            // AccommodationSimpleResponse 객체를 생성하면서 lowest_price 값을 설정
            return AccommodationSimpleResponse.fromEntity(accommodation, lowestPrice, isWished);
        });


//        return result.map(AccommodationSimpleResponse::fromEntity);

    }



}
