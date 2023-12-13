package com.miniproject.domain.basket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.basket.dto.request.CheckBasketRequestDto;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.exception.BasketEmptyException;
import com.miniproject.domain.basket.repository.BasketRepository;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomImage;
import com.miniproject.domain.room.entity.RoomInBasket;
import com.miniproject.domain.room.entity.RoomInOrders;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomInOrdersRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @InjectMocks
    private BasketService basketService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private RoomInBasketRepository roomInBasketRepository;

    @Mock
    private RoomInOrdersRepository roomInOrdersRepository;

    @Mock
    private BasketRepository basketRepository;

    @DisplayName("getBasket()은 장바구니 조회를 할 수 있다.")
    @Test
    @WithMockUser
    public void getBasket_willSuccess() throws Exception {
        //given
        Member member = Member.builder()
            .nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        Basket basket = new Basket(member, 1L);
        List<Basket> basketList = new ArrayList<>();
        basketList.add(basket);
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
        RoomImage roomImage = RoomImage.builder()
            .room(room)
            .imageUrl("323dsfsdf")
            .id(1L).build();
        RoomInBasket roomInBasket = RoomInBasket.builder()
            .member(member)
            .checkInAt(LocalDate.now())
            .checkOutAt(LocalDate.now().withDayOfMonth(30))
            .numberOfGuests(2)
            .room(room)
            .basket(basket)
            .member(member).build();
        basket.registerRoom(roomInBasket);
        given(basketRepository.findByMember(any(Member.class))).willReturn(basketList);

        //when
        BasketResponseDto basketResponseDto = basketService.getBasket(member);

        //then
        assertThat(basketResponseDto).extracting("id", "totalCount", "totalPrice")
            .containsExactly(1L, 1, 30000);
    }

    @DisplayName("deleteRoomInBasket()은 장바구니에 담긴 객실이 없다면 객실을 삭제할 수 없다.")
    @Test
    @WithMockUser
    public void deleteRoomInBasket_willSuccess() throws Exception {
        //given
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        CheckBasketRequestDto dto = CheckBasketRequestDto.builder().ids(ids).build();
        Member member = Member.builder()
            .nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        Basket basket = new Basket(member, 1L);
        List<Basket> basketList = new ArrayList<>();
        basketList.add(basket);

        given(basketRepository.findByMember(any(Member.class))).willReturn(basketList);

        //when, then
        assertThatThrownBy(() -> basketService.deleteRoomInBasket(dto, member))
            .isInstanceOf(BasketEmptyException.class);


    }

    @DisplayName("registerOrder()은 주문을 생성할 수 있다.")
    @Test
    @WithMockUser
    public void registerOrder_willSuccess() throws Exception {
        //given
        Member member = Member.builder()
            .nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        CheckBasketRequestDto dto = CheckBasketRequestDto.builder().ids(ids).build();
        List<Basket> basketList = new ArrayList<>();
        Basket basket = new Basket(member, 1L);
        basketList.add(basket);

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
        RoomInBasket roomInBasket = RoomInBasket.builder()
            .id(1L)
            .member(member)
            .checkInAt(LocalDate.now())
            .checkOutAt(LocalDate.now().withDayOfMonth(30))
            .numberOfGuests(2)
            .room(room)
            .basket(basket)
            .member(member).build();
        basket.registerRoom(roomInBasket);

        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(basket.getTotalPrice())
            .totalCount(basket.getTotalCount()).build();

        List<RoomInOrders> roomInOrdersList = new ArrayList<>();
        RoomInOrders roomInOrders = RoomInOrders.builder()
            .checkInAt(roomInBasket.getCheckInAt())
            .checkOutAt(roomInBasket.getCheckOutAt())
            .numberOfGuests(roomInBasket.getNumberOfGuests())
            .room(roomInBasket.getRoom())
            .member(member)
            .orders(orders)
            .build();
        roomInOrdersList.add(roomInOrders);

        given(basketRepository.findByMember(any(Member.class))).willReturn(basketList);
        given(ordersRepository.save(any(Orders.class))).willReturn(orders);
        given(roomInBasketRepository.findById(anyLong())).willReturn(Optional.of(roomInBasket));
        given(roomInOrdersRepository.saveAll(any())).willReturn(roomInOrdersList);
        //when
        Long aLong = basketService.registerOrder(dto, member);

        //then
        assertThat(aLong).isEqualTo(1L);
    }

    @DisplayName("getActivateBasket()은 활성화된 장바구니를 불러올 수 있다.")
    @Test
    @WithMockUser
    public void getActivateBasket_willSuccess() throws Exception {
        //given
        Member member = Member.builder()
            .nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        List<Basket> nowBasket = new ArrayList<>();
        given(basketRepository.findByMember(any(Member.class))).willReturn(nowBasket);
        given(basketRepository.save(any(Basket.class))).willReturn(new Basket(member, 1L));
        //when
        Basket activateBasket = basketService.getActivateBasket(member);

        //then
        assertThat(activateBasket).extracting("id", "totalPrice", "totalCount")
            .containsExactly(1L, 0, 0);
    }
}
