package com.miniproject.domain.accommodation.service;

import com.miniproject.domain.accommodation.dto.request.AccommodationRequest;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomImage;
import com.miniproject.domain.room.entity.RoomInventory;
import com.miniproject.domain.wish.service.WishService;
import com.miniproject.global.resolver.LoginInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {


    @InjectMocks
    private AccommodationService accommodationService;

    @Mock
    private WishService wishService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @DisplayName("getAccommodationWithRoomById()는 숙소 단일 조회를 할 수 있다.")
    @Test
    public void getAccommodation_willSuccess(){

        LoginInfo loginInfo = new LoginInfo("anonymousUser");
        Accommodation accommodation = Accommodation.builder().id(1L).name("숙소01")
                .rooms(List.of(
                        Room.builder().name("방01").roomImages(List.of(RoomImage.builder().build())).roomInventories(List.of(RoomInventory.builder().id(1L).build())).build(),
                        Room.builder().name("방02").roomImages(List.of(RoomImage.builder().build())).roomInventories(List.of(RoomInventory.builder().id(2L).build())).build()))
                .checkIn(LocalTime.now())
                .checkOut(LocalTime.now()).type(AccommodationType.CONDOMINIUM)
                .build();

        given(accommodationRepository.findById(anyLong())).willReturn(Optional.of(accommodation));

        var savedAccommodation = accommodationService.getAccommodationWithRoomById(accommodation.getId(), loginInfo);

        assertThat(savedAccommodation).extracting("id","name")
                .containsExactly(accommodation.getId(),accommodation.getName());
    }

    @DisplayName("getAccommodations()는 숙소 전체 조회를 할 수 있다.")
    @Test
    public void getAccommodations_willSuccess() {

        Accommodation accommodation = Accommodation.builder().id(1L).name("숙소01")
                .rooms(List.of(
                        Room.builder().name("방01").build(),
                        Room.builder().name("방02").build()
                )).checkIn(LocalTime.now()).checkOut(LocalTime.now()).type(AccommodationType.CONDOMINIUM).build();

        Accommodation accommodation2 = Accommodation.builder().id(2L).name("숙소02")
                .rooms(List.of(Room.builder().name("방01").build(), Room.builder().name("방02").build()))
                .checkIn(LocalTime.now()).checkOut(LocalTime.now()).type(AccommodationType.CONDOMINIUM).build();

        List<Accommodation> accommodations = List.of(accommodation, accommodation2);

        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Accommodation> accommodationPage =
                new PageImpl<>(accommodations.subList(0, 2), pageRequest, accommodations.size());

        given(accommodationRepository.findByCategory(any(Pageable.class), any(), any(), any(), any(), any(), any())).willReturn(accommodationPage);
        LoginInfo loginInfo = new LoginInfo("anonymousUser");
        Pageable pageable = PageRequest.of(0, 20);

        AccommodationRequest request = new AccommodationRequest(null, null, null, null, null, null);

        var responses =  accommodationService.getAccommodations(pageable, request, loginInfo).getContent();

        //then
        Assertions.assertThat(responses.get(0)).extracting("id", "name")
                .containsExactly(1L, "숙소01");
        Assertions.assertThat(responses.get(1)).extracting("id", "name")
                .containsExactly(2L, "숙소02");
    }

}