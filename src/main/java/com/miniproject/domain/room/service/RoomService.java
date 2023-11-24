package com.miniproject.domain.room.service;

import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomDetailResponse getRoomById(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow();

        return RoomDetailResponse.fromEntity(room);
    }

    @Transactional
    public Page<RoomDetailResponse> getRoomsByAccommodationId(Long accommodationId,
                                                              Pageable pageable,
                                                              Integer categoryTv,
                                                              Integer categoryPc,
                                                              Integer categoryInternet,
                                                              Integer categoryRefrigerator,
                                                              Integer categoryBathingFacilities,
                                                              Integer categoryDryer) {
        Page<Room> result = roomRepository
                .findByAccommodationIdAndCategory(
                        accommodationId,
                        pageable,
                        categoryTv,
                        categoryPc,
                        categoryInternet,
                        categoryRefrigerator,
                        categoryBathingFacilities,
                        categoryDryer);

        return result.map(RoomDetailResponse::fromEntity);

    }
}
