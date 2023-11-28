package com.miniproject.room.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInBasket;
import com.miniproject.domain.room.entity.RoomInOrders;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomInOrdersRepository;
import com.miniproject.domain.room.repository.RoomRepository;
import com.miniproject.domain.room.service.RoomService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
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
            .id(1L).name("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        Basket basket = new Basket(member, 1L);
        given(basketService.getActivateBasket(any())).willReturn(basket);
        RoomInBasket roomInBasket = RoomInBasket.builder()
            .id(1L)
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
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
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
            .numberOfGuests(2).build();
        Member member = Member.builder()
            .id(1L).name("하이").email("kj@gmail.com").password("ffdfda231321@da").build();

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

}
