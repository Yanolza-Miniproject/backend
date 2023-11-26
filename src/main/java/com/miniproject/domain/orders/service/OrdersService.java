package com.miniproject.domain.orders.service;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.MemberUnAuthorizedException;
import com.miniproject.domain.orders.dto.response.OrdersResponseDto;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.exception.OrdersNotFoundException;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.payment.entity.Payment;
import com.miniproject.domain.payment.repository.PaymentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final PaymentRepository paymentRepository;



    public OrdersResponseDto getOrder(Long orderId, Member member) {
        Orders orders = getOrders(orderId, member);
        return new OrdersResponseDto(orders);
    }

    public Long registerPayment(Long orderId,Member member) {
        Orders orders = getOrders(orderId, member);
        Payment payment = Payment.builder()
            .orders(orders)
            .member(member)
            .build();
        return paymentRepository.save(payment).getId();
    }

    public void deleteOrders(Long ordersId, Member member) {
        Orders orders = getOrders(ordersId, member);
        ordersRepository.delete(orders);

    }

    private Orders getOrders(Long orderId, Member member) {
        Orders orders = ordersRepository.findById(orderId)
            .orElseThrow(OrdersNotFoundException::new);
        if (!member.equals(orders.getMember())) {
            throw new MemberUnAuthorizedException();
        }
        return orders;
    }
}
