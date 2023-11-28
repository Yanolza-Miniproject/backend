package com.miniproject.domain.accommodation.service;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationRegionType;
import com.miniproject.domain.accommodation.exception.AccommodationNotFoundException;
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
                .orElseThrow(AccommodationNotFoundException::new);

        accommodation.plusViewCount();

        Integer cheapestRoomPrice = accommodation.getRooms().stream()
                .map(Room::getPrice)
                .min(Integer::compare)
                .orElse(null);

        return AccommodationDetailResponse.formEntity(accommodation, cheapestRoomPrice);
    }

    // 동적 쿼리가 필요한 기능이므로 querydsl 사용이 추천됨
    @Transactional
    public Page<AccommodationSimpleResponse> getAccommodations(Pageable pageable,
                                                               Integer categoryParking,
                                                               Integer categoryCooking,
                                                               Integer categoryPickup,
                                                               Integer wishCount,
                                                               Integer region01) {

        String region = AccommodationRegionType.findByValue(region01).getDescription();

        Page<Accommodation> result = accommodationRepository
                .findByCategory(pageable, categoryParking, categoryCooking, categoryPickup, wishCount, region);

        return result.map(accommodation -> {
            Integer lowestPrice = accommodation.getRooms().stream()
                    .map(Room::getPrice)
                    .min(Integer::compare)
                    .orElse(null);
            return AccommodationSimpleResponse.fromEntity(accommodation, lowestPrice);
        });


    }



}
