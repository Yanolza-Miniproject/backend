package com.miniproject.domain.room.service;

import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.dto.response.RoomSimpleResponse;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInventory;
import com.miniproject.domain.room.exception.RoomNotFoundException;
import com.miniproject.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomDetailResponse getRoomById(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        return RoomDetailResponse.fromEntity(room);
    }

    @Transactional
    public List<RoomSimpleResponse> getRoomsByAccommodationId(Long accommodationId,
                                                              Pageable pageable,
                                                              Integer categoryTv,
                                                              Integer categoryPc,
                                                              Integer categoryInternet,
                                                              Integer categoryRefrigerator,
                                                              Integer categoryBathingFacilities,
                                                              Integer categoryDryer,
                                                              LocalDate checkinDay,
                                                              LocalDate checkoutDay) {

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



        return result
                .stream()
                .filter(room -> room.isAvailable(checkinDay, checkoutDay))
                .map(RoomSimpleResponse::fromEntity)
                .collect(Collectors.toList());

    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // 매일 밤 12시에 실행
    public void updateRoomInventories() {
        List<Room> rooms = roomRepository.findAll();

        for (Room room : rooms) {
            List<RoomInventory> roomInventories = room.getRoomInventories();

            // 오늘보다 이전의 RoomInventory 삭제
            roomInventories.removeIf(roomInventory -> roomInventory.getDate().isBefore(LocalDate.now()));

            // 새로운 RoomInventory 생성 및 추가
            RoomInventory newRoomInventory = RoomInventory.builder()
                    .inventory(room.getInventory())
                    .date(LocalDate.now().plusDays(36)) // 37일 후 날짜 설정
                    .room(room)
                    .build();

            roomInventories.add(newRoomInventory);

            room.updateRoomInventory(roomInventories);
        }

    }



}
