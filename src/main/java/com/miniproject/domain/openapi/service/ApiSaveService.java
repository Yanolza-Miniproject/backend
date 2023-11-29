package com.miniproject.domain.openapi.service;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.openapi.dto.AccommodationCommon;
import com.miniproject.domain.openapi.dto.AccommodationDetailInfo;
import com.miniproject.domain.openapi.dto.AccommodationIntro;
import com.miniproject.domain.openapi.dto.RoomImageUrlDto;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomImage;
import com.miniproject.domain.room.entity.RoomInventory;
import com.miniproject.domain.room.repository.RoomImageRepository;
import com.miniproject.domain.room.repository.RoomInventoryRepository;
import com.miniproject.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApiSaveService {

    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;
    private final RoomInventoryRepository roomInventoryRepository;

    @Transactional
    public void saveAccommodations(List<AccommodationCommon> accommodationCommons,
                                   List<AccommodationIntro> accommodationIntros,
                                   List<AccommodationDetailInfo> accommodationDetailInfos) {

        Map<String, AccommodationIntro> introMap = accommodationIntros.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(AccommodationIntro::contentid, Function.identity()));

        Map<String, List<AccommodationDetailInfo>> detailInfoMap = accommodationDetailInfos.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(AccommodationDetailInfo::contentid));

        List<Accommodation> accommodations = new ArrayList<>();

        for (AccommodationCommon common : accommodationCommons) {

            if (common == null) {
                continue;
            }

            AccommodationIntro intro = introMap.get(common.contentid());
            List<AccommodationDetailInfo> detailInfos = detailInfoMap.get(common.contentid());

            if (intro != null) {
                Accommodation accommodation = toAccommodationEntity(common, intro);
                accommodation = accommodationRepository.save(accommodation);

                if (detailInfos != null) {
                    saveRooms(detailInfos, accommodation);
                }

            }
        }
        accommodationRepository.saveAll(accommodations);
    }

    private Accommodation toAccommodationEntity(AccommodationCommon common, AccommodationIntro intro) {
        return Accommodation.builder()
                .name(common.title())
                .address(common.addr1())
                .phoneNumber(common.tel())
                .homepage(common.homepage())
                .thumbnailUrl(common.firstimage())
                .infoDetail(common.overview())
                .type(common.accommodationType())
                .categoryParking(intro.parkinglodging())
                .categoryCooking(intro.chkcooking())
                .categoryPickup(intro.pickup())
                .categoryAmenities(intro.foodplace())
                .categoryDiningArea(intro.subfacility())
                .checkIn(intro.checkintime())
                .checkOut(intro.checkouttime())
                .build();
    }

    @Transactional
    public void saveRooms(List<AccommodationDetailInfo> detailInfos, Accommodation accommodation) {
        List<Room> rooms = detailInfos.stream()
                .map(detailInfo -> saveRoomAndImages(detailInfo, accommodation))
                .toList();

    }

    private Room saveRoomAndImages(AccommodationDetailInfo detailInfo, Accommodation accommodation) {
        System.out.println(detailInfo);
        Room room = Room.builder()
                .accommodation(accommodation)
                .name(detailInfo.roomtitle())
                .price(detailInfo.roomoffseasonminfee1())
                .capacity(detailInfo.roombasecount())
                .inventory(detailInfo.roomcount() == 0 ? 1: detailInfo.roomcount())
                .categoryTv(detailInfo.roomtv())
                .categoryPc(detailInfo.roompc())
                .categoryInternet(detailInfo.roominternet())
                .categoryRefrigerator(detailInfo.roomrefrigerator())
                .categoryBathingFacilities(detailInfo.roombathfacility())
                .categoryDryer(detailInfo.roomhairdryer())
                .build();

        Room savedRoom = roomRepository.save(room);

        List<RoomImage> roomImages = detailInfo.roomImages().stream()
                .map(roomImage -> toRoomImageEntity(roomImage, savedRoom))
                .collect(Collectors.toList());

        roomImageRepository.saveAll(roomImages);

        List<RoomInventory> roomInventories = new ArrayList<>();
        for(int i = 0; i < 37; i++){
            RoomInventory roomInventory = RoomInventory.builder()
                    .date(LocalDate.now().plusDays(i))
                    .inventory(detailInfo.roomcount() == 0 ? 1: detailInfo.roomcount())
                    .room(room)
                    .build();
            roomInventories.add(roomInventory);
        }

        roomInventoryRepository.saveAll(roomInventories);

        return room;
    }

    private com.miniproject.domain.room.entity.RoomImage toRoomImageEntity(RoomImageUrlDto roomImage, Room room) {
        return RoomImage.builder()
                .room(room)
                .imageUrl(roomImage.imageUrl())
                .build();
    }


}
