package com.miniproject.basket.service;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.repository.BasketRepository;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInBasket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomInOrdersRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
            .id(1L).name("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        Basket basket = new Basket(member,1L);

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
        RoomInBasket roomInBasket = RoomInBasket.builder()
            .member(member)
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
            .numberOfGuests(2)
            .room(room)
            .basket(basket)
            .member(member).build();
        basket.RegisterRoom(roomInBasket);
        roomInBasketRepository.save(roomInBasket);
        given(basketRepository.findByMember(any(Member.class))).willReturn(basketList);
        //when

        BasketResponseDto basketResponseDto = basketService.getBasket(member
        );

        //then
        assertThat(basketResponseDto).extracting("id","totalCount","totalPrice")
            .containsExactly(1L,1,30000);
    }
}
