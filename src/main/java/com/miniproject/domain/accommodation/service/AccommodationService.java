package com.miniproject.domain.accommodation.service;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        return AccommodationDetailResponse.fromEntity(accommodation);
    }



}
