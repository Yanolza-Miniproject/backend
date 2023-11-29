package com.miniproject.domain.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.MemberUnAuthorizedException;
import com.miniproject.domain.orders.dto.response.OrdersResponseDto;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.orders.service.OrdersService;
import com.miniproject.domain.payment.entity.Payment;
import com.miniproject.domain.payment.repository.PaymentRepository;
import com.miniproject.domain.room.entity.Room;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrdersService ordersService;

    @Mock
    OrdersRepository ordersRepository;

    @Mock
    PaymentRepository paymentRepository;


    @DisplayName("getOrders()는 주문 객체를 내보낼 수 있다.")
    @Test
    public void getOrders_willSuccess() {
        //given
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

        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(room.getPrice())
            .totalCount(2).build();
        given(ordersRepository.findById(anyLong())).willReturn(Optional.of(orders));

        //when
        Orders savedOrders = ordersService.getOrders(orders.getId(), member);
        //then
        assertThat(savedOrders).extracting("id", "totalPrice", "totalCount")
            .containsExactly(orders.getId(), orders.getTotalPrice(), orders.getTotalCount());
    }

    @DisplayName("getOrders()는 인증이 실패하면 주문을 조회할 수 없다.")
    @Test
    public void getOrders_willFail() {
        //given
        Member member1 = Member.builder()
            .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        Member member2 = Member.builder()
            .id(2L).nickname("이하").email("kjf@gmail.com").password("ffdfda231321@da").build();

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

        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member1)
            .totalPrice(room.getPrice())
            .totalCount(2).build();
        given(ordersRepository.findById(anyLong())).willReturn(Optional.of(orders));

        //when, then
        assertThatThrownBy(() -> ordersService.getOrders(orders.getId(), member2)).isInstanceOf(
            MemberUnAuthorizedException.class);
    }

    @DisplayName("getOrder()는 주문 객체를 responseDto로 내보낼 수 있다.")
    @Test
    public void getOrder_willSuccess() {
        //given
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

        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(room.getPrice())
            .totalCount(2).build();
        given(ordersRepository.findById(anyLong())).willReturn(Optional.of(orders));
        //when
        OrdersResponseDto order = ordersService.getOrder(orders.getId(), member);

        assertThat(order).extracting("id", "totalPrice", "totalCount", "orderAt")
            .containsExactly(orders.getId(), orders.getTotalPrice(), orders.getTotalCount(),
                orders.getOrderAt());
    }

    @DisplayName("registerPayment()는 결제 생성을 할 수 있다.")
    @Test
    public void registerPayment_willSuccess() {
        //given
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
        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(room.getPrice())
            .totalCount(2).build();

        Payment payment = Payment.builder()
            .id(1L)
            .orders(orders)
            .member(member)
            .build();

        given(ordersRepository.findById(anyLong())).willReturn(Optional.of(orders));
        given(paymentRepository.save(any(Payment.class))).willReturn(payment);

        //when
        Long paymentId = ordersService.registerPayment(orders.getId(), member);
        //then
        assertThat(paymentId).isEqualTo(payment.getId());
    }

}
