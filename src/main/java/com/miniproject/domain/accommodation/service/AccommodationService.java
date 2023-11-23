package com.miniproject.domain.accommodation.service;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.room.entity.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Transactional
    public AccommodationDetailResponse getAccommodationWithRoomById(Long accommodationId) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow();

        Integer cheapestRoomPrice = accommodation.getRooms().stream()
                .map(Room::getPrice)
                .min(Integer::compare)
                .orElse(null);

        return AccommodationDetailResponse.fromEntity(accommodation, cheapestRoomPrice);
    }

    // 동적 쿼리가 필요한 기능이므로 querydsl 사용이 추천됨
    @Transactional
    public Page<AccommodationSimpleResponse> getAccommodations(Pageable pageable,
                                                               Integer categoryParking,
                                                               Integer categoryCooking,
                                                               Integer categoryPickup,
                                                               Integer wishCount,
                                                               String region01) {

        Page<Accommodation> result = accommodationRepository
                .findByCategory(pageable, categoryParking, categoryCooking, categoryPickup, wishCount, region01);

        return result.map(accommodation -> {
            Integer lowestPrice = accommodation.getRooms().stream()
                    .map(Room::getPrice)
                    .min(Integer::compare)
                    .orElse(null);
            // AccommodationSimpleResponse 객체를 생성하면서 lowest_price 값을 설정
            return AccommodationSimpleResponse.fromEntity(accommodation, lowestPrice);
        });


//        return result.map(AccommodationSimpleResponse::fromEntity);

    }



}
