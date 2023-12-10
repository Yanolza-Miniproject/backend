package com.miniproject.domain.accommodation.service;

import com.miniproject.domain.accommodation.dto.request.AccommodationRequest;
import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationRegionType;
import com.miniproject.domain.accommodation.exception.AccommodationNotFoundException;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.wish.service.WishService;
import com.miniproject.global.resolver.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
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

        boolean isWished = false;

        if(!Objects.equals(loginInfo.username(), "anonymousUser")) {
            List<Long> likedAccommodationIds = wishService.getWishesOnlyAccommodationId(loginInfo);
            isWished = likedAccommodationIds.contains(accommodation.getId());
        }

        return AccommodationDetailResponse.formEntity(accommodation, cheapestRoomPrice, isWished);
    }

    @Transactional
    public Page<AccommodationSimpleResponse> getAccommodations(Pageable pageable,
                                                               AccommodationRequest request,
                                                               LoginInfo loginInfo) {

        String region = null;

        if (request.region() != null) {
            region = AccommodationRegionType.findByValue(request.region()).getDescription();
        }

        Page<Accommodation> result = accommodationRepository
                .findByCategory(pageable,
                        request.categoryParking(),
                        request.categoryCooking(),
                        request.categoryPickup(),
                        request.type(),
                        request.wishCount(),
                        region);

        List<Long> likedAccommodationIds;

        if(!Objects.equals(loginInfo.username(), "anonymousUser")) {
            likedAccommodationIds = wishService.getWishesOnlyAccommodationId(loginInfo);
        } else {
            likedAccommodationIds = new ArrayList<>();
        }


        return result.map(accommodation -> {
            Integer lowestPrice = accommodation.getRooms().stream()
                    .map(Room::getPrice)
                    .min(Integer::compare)
                    .orElse(null);


            boolean isWished = false;

            if (!Objects.equals(loginInfo.username(), "anonymousUser") && likedAccommodationIds.contains(accommodation.getId())) {
                isWished = true;
            }

            // AccommodationSimpleResponse 객체를 생성하면서 lowest_price 값을 설정
            return AccommodationSimpleResponse.fromEntity(accommodation, lowestPrice, isWished);
        });
    }
}
