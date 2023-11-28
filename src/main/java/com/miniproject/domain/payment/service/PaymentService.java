package com.miniproject.domain.payment.service;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.MemberUnAuthorizedException;
import com.miniproject.domain.payment.dto.response.PaymentResponseDto;
import com.miniproject.domain.payment.entity.Payment;
import com.miniproject.domain.payment.exception.PaymentNotFoundException;
import com.miniproject.domain.payment.repository.PaymentRepository;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInBasket;
import com.miniproject.domain.room.entity.RoomInOrders;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RoomInBasketRepository roomInBasketRepository;

    public PaymentResponseDto getPayment(Long paymentId,Member member) {
        Payment payment = getPaymentForException(paymentId, member);
        return new PaymentResponseDto(payment);
    }

    public List<PaymentResponseDto> getPayments(Integer page, Integer pageSize, Member member) {
        Page<Payment> allByMemberContaining = paymentRepository.findAllByMemberContaining(
            PageRequest.of(page - 1, pageSize), member);
        return allByMemberContaining.stream()
            .map(payment -> new PaymentResponseDto(payment))
            .collect(Collectors.toList());


    }

    public void completePayment(Long paymentId, Member member){
        Payment payment = getPaymentForException(paymentId, member);
        payment.completePayment();

        List<RoomInBasket> roomInBaskets = new ArrayList<>();
        List<RoomInOrders> roomInOrders = payment.getOrders().getRoomInOrders();
        for (RoomInOrders roomInOrder : roomInOrders) {
            if (roomInOrder.getRoomInBasket() != null) {
                roomInBaskets.add(roomInOrder.getRoomInBasket());
            }
        }
        roomInBasketRepository.deleteAll(roomInBaskets);
    }

    public Payment getPaymentForException(Long paymentId, Member member) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(PaymentNotFoundException::new);
        if(!member.getId().equals(payment.getOrders().getMember().getId())){
            throw new MemberUnAuthorizedException();
        }
        return payment;
    }
}
