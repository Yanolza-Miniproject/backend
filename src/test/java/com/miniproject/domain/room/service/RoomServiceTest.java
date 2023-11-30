package com.miniproject.domain.room.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.payment.entity.Payment;
import com.miniproject.domain.room.dto.RoomImageDTO;
import com.miniproject.domain.room.dto.RoomInventoryDTO;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.dto.response.RoomSimpleResponse;
import com.miniproject.domain.room.entity.*;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomInOrdersRepository;
import com.miniproject.domain.room.repository.RoomRepository;
import com.miniproject.domain.room.service.RoomService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private BasketService basketService;
    @Mock
    private RoomInBasketRepository roomInBasketRepository;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private RoomInOrdersRepository roomInOrdersRepository;

    @Test
    @DisplayName("creatRoomInBasket()는 장바구니에 객실 등록을 할 수 있다.")
    @WithMockUser
    public void creatRoomInBasket_willSuccess() {
        //given
        RoomRegisterRequestDto dto = RoomRegisterRequestDto.builder()
            .checkInAt(LocalDate.now())
            .checkOutAt(LocalDate.now().withDayOfMonth(30))
            .numberOfGuests(2).build();
        Accommodation accommodation = Accommodation.builder()
            .id(1L)
            .name("제주 호텔").build();
        Room room = Room.builder().name("스탠다드")
            .price(30000)
            .id(1L)
            .accommodation(accommodation)
            .capacity(2)
            .inventory(10)
            .build();
        given(roomRepository.findById(anyLong())).willReturn(Optional.of(room));
        Member member = Member.builder()
            .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        Basket basket = new Basket(member, 1L);
        given(basketService.getActivateBasket(any())).willReturn(basket);
        RoomInBasket roomInBasket = RoomInBasket.builder()
            .id(1L)
            .checkInAt(LocalDate.now())
            .checkOutAt(LocalDate.now().withDayOfMonth(30))
            .numberOfGuests(2)
            .room(room)
            .member(member)
            .basket(basket)
            .build();
        given(roomInBasketRepository.save(any(RoomInBasket.class))).willReturn(roomInBasket);
        //when
        RoomInBasket savedRoomInBasket = roomService.creatRoomInBasket(1L, dto, member);
        //then
        assertThat(savedRoomInBasket).extracting("id", "numberOfGuests")
            .containsExactly(1L, 2);
    }

    @Test
    @DisplayName("createSingleOrders()는 장바구니에 객실 등록을 할 수 있다.")
    @WithMockUser
    public void createSingleOrders_willSuccess() {
        //given
        RoomRegisterRequestDto dto = RoomRegisterRequestDto.builder()
            .checkInAt(LocalDate.now())
            .checkOutAt(LocalDate.now().withDayOfMonth(30))
            .numberOfGuests(2).build();
        Member member = Member.builder()
            .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();

        Accommodation accommodation = Accommodation.builder()
            .id(1L)
            .name("제주 호텔").build();
        Room room = Room.builder().name("스탠다드")
            .price(30000)
            .id(1L)
            .accommodation(accommodation)
            .capacity(2)
            .inventory(10)
            .build();
        given(roomRepository.findById(anyLong())).willReturn(Optional.of(room));
        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(room.getPrice())
            .totalCount(dto.getNumberOfGuests()).build();
        given(ordersRepository.save(any(Orders.class))).willReturn(orders);
        RoomInOrders roomInOrders = RoomInOrders.builder()
            .checkInAt(dto.getCheckInAt())
            .checkOutAt(dto.getCheckOutAt())
            .numberOfGuests(dto.getNumberOfGuests())
            .room(room)
            .member(member)
            .orders(orders)
            .build();
        given(roomInOrdersRepository.save(any(RoomInOrders.class))).willReturn(roomInOrders);
        //when
        Long singleOrders = roomService.createSingleOrders(room.getId(), dto, member);

        //then
        assertThat(singleOrders).isEqualTo(orders.getId());

    }


    @Test
    @DisplayName("객실 ID를 통해 객실 정보를 가져올 수 있다.")
    @WithMockUser
    public void getRoomById_willSuccess() {

        // given
        Accommodation accommodation = Accommodation.builder()
                .id(1L)
                .name("숙소01")
                .build();

        List<RoomImage> roomImageList = List.of(RoomImage.builder().id(1L).build());
        List<RoomInventory> roomInventoryList = List.of(RoomInventory.builder().id(1L).build());

        Room room01 = Room.builder()
                .id(1L)
                .name("객실01")
                .price(10000)
                .capacity(0)
                .inventory(0)
                .categoryTv(false)
                .categoryPc(false)
                .categoryInternet(false)
                .categoryRefrigerator(false)
                .categoryDryer(false)
                .categoryBathingFacilities(false)
                .roomInventories(roomInventoryList)
                .roomImages(roomImageList)
                .accommodation(accommodation)
                .build();

        List<RoomInventoryDTO> roomInventoryDTOList = List.of(RoomInventoryDTO.builder().id(1L).inventory(0).build());
        List<RoomImageDTO> roomImageDTOList = List.of(RoomImageDTO.builder().id(1L).build());

        RoomDetailResponse roomDetailResponse = RoomDetailResponse.builder()
                .id(1L)
                .accommodationName("숙소01")
                .name("객실01")
                .price(10000)
                .capacity(0)
                .inventory(0)
                .categoryTv(false)
                .categoryPc(false)
                .categoryInternet(false)
                .categoryRefrigerator(false)
                .categoryDryer(false)
                .categoryBathingFacilities(false)
                .roomImages(roomImageDTOList)
                .roomInventories(roomInventoryDTOList)
                .build();

        given(roomRepository.findById(anyLong())).willReturn(Optional.ofNullable(room01));

        // when
        RoomDetailResponse result = roomService.getRoomById(room01.getId());

        // then
        assertThat(result).isEqualTo(roomDetailResponse);

    }

    @Test
    @DisplayName("객실 ID와 필터 내용을 통해 특정 숙소의 객실 정보를 가져올 수 있다.")
    @WithMockUser
    public void getRoomsByAccommodationId_willSuccess() {

        // given
        Accommodation accommodation = Accommodation.builder()
                .id(1L)
                .build();

        List<RoomImage> roomImageList = List.of(RoomImage.builder().id(1L).build());
        List<RoomInventory> roomInventoryList = List.of(RoomInventory.builder().id(1L).date(LocalDate.now()).build());

        Room room01 = Room.builder()
                .id(1L)
                .name("객실01")
                .price(10000)
                .capacity(0)
                .inventory(0)
                .categoryTv(false)
                .categoryPc(false)
                .categoryInternet(false)
                .categoryRefrigerator(false)
                .categoryDryer(false)
                .categoryBathingFacilities(false)
                .roomInventories(roomInventoryList)
                .roomImages(roomImageList)
                .accommodation(accommodation)
                .build();

        List<Room> rooms = List.of(room01);

        RoomSimpleResponse roomSimpleResponse = RoomSimpleResponse.builder()
                .id(1L)
                .name("객실01")
                .price(10000)
                .capacity(0)
                .inventory(0)
                .categoryTv(false)
                .categoryPc(false)
                .categoryInternet(false)
                .categoryRefrigerator(false)
                .categoryDryer(false)
                .categoryBathingFacilities(false)
                .build();

        List<RoomSimpleResponse> responseList = List.of(roomSimpleResponse);

        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Room> roomPage =
                new PageImpl<>(rooms.subList(0, 1), pageRequest, rooms.size());

        given(roomRepository.findByAccommodationIdAndCategory(anyLong(), any(), any(), any(), any(), any(), any(), any())).willReturn(roomPage);

        // when
        List<RoomSimpleResponse> result = roomService.getRoomsByAccommodationId(accommodation.getId(), Pageable.ofSize(20), 0, 0, 0, 0, 0, 0, LocalDate.now(), LocalDate.now());


        // then
        assertThat(result).isEqualTo(responseList);
    }
}
