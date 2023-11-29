package com.miniproject.domain.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.payment.dto.response.PaymentResponseDto;
import com.miniproject.domain.payment.entity.Payment;
import com.miniproject.domain.payment.repository.PaymentRepository;
import com.miniproject.domain.payment.service.PaymentService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;


    @Test
    @DisplayName("getPaymentForException()는 Payment 객체를 내보낼 수 있다.")
    @WithMockUser
    public void getPaymentForException_willSuccess() {
        //given
        Member member = Member.builder()
            .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();

        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(30000)
            .totalCount(2).build();

        Payment payment = Payment.builder()
            .id(1L)
            .member(member)
            .orders(orders)
            .build();

        given(paymentRepository.findById(anyLong())).willReturn(Optional.of(payment));
        //when
        Payment savedPayment = paymentService.getPaymentForException(1L, member);
        //then
        assertThat(savedPayment.getId()).isEqualTo(payment.getId());
    }

    @Test
    @DisplayName("getPayment()는 결제를 조회할 수 있다.")
    @WithMockUser
    public void getPayment_willSuccess() {
        //given
        Member member = Member.builder()
            .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();

        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(30000)
            .totalCount(2).build();

        Payment payment = Payment.builder()
            .id(1L)
            .member(member)
            .orders(orders)
            .build();

        given(paymentRepository.findById(anyLong())).willReturn(Optional.of(payment));
        //when
        PaymentResponseDto paymentResponseDto = paymentService.getPayment(1L, member);

        //then
        assertThat(paymentResponseDto).extracting("id", "totalPrice", "totalCount")
            .containsExactly(1L, 30000, 2);
    }

    @Test
    @DisplayName("getPayments()는 결제를 전체 조회할 수 있다.")
    @WithMockUser
    public void getPayments_willSuccess() {
        //given
        Member member = Member.builder()
            .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();

        Orders orders = Orders.builder()
            .id(1L)
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(30000)
            .totalCount(2).build();

        Payment payment1 = Payment.builder()
            .id(1L)
            .member(member)
            .orders(orders)
            .build();
        Payment payment2 = Payment.builder()
            .id(2L)
            .member(member)
            .orders(orders)
            .build();

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment1);
        paymentList.add(payment2);

        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Payment> payments =
            new PageImpl<>(paymentList.subList(0, 2), pageRequest, paymentList.size());
        given(paymentRepository.findAllByMember(any(), any())).willReturn(payments);

        //when
        List<PaymentResponseDto> responses = paymentService.getPayments(1, 20, member);

        //then
        assertThat(responses.get(0)).extracting("totalPrice", "totalCount")
            .containsExactly(30000, 2);
        assertThat(responses.get(1)).extracting("totalPrice", "totalCount")
            .containsExactly(30000, 2);

    }

}
