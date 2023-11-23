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
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BasketService basketService;
    private final RoomInBasketRepository roomInBasketRepository;

    public PaymentResponseDto getPayment(Long paymentId,Member member) {
        Payment payment = getPaymentForException(paymentId, member);
        return new PaymentResponseDto(payment);
    }

    public List<PaymentResponseDto> getPayments(Member member) {
        return paymentRepository.findByMember(member).stream().map(
            payment -> new PaymentResponseDto(payment)).collect(Collectors.toList());
    }

    public void completePayment(Long paymentId, Member member){
        Payment payment = getPaymentForException(paymentId, member);
        payment.completePayment();
        Basket activateBasket = basketService.getActivateBasket(member);

        List<Room> rooms = payment.getOrders().getRoomInOrders().stream().map(
            roomInOrders -> roomInOrders.getRoom()).collect(Collectors.toList());
        List<RoomInBasket> roomInBaskets = new ArrayList<>();

        List<RoomInBasket> byBasket = roomInBasketRepository.findByBasket(activateBasket);
        for (RoomInBasket roomInBasket : byBasket) {
            if (rooms.contains(roomInBasket.getRoom())) {
                roomInBaskets.add(roomInBasket);
            }
        }
        roomInBasketRepository.deleteAll(roomInBaskets);
    }

    public void deletePayment(Long paymentId, Member member) {
        Payment payment = getPaymentForException(paymentId, member);
        paymentRepository.delete(payment);

    }

    private Payment getPaymentForException(Long paymentId, Member member) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(PaymentNotFoundException::new);
        if(!member.equals(payment.getOrders().getMember())){
            throw new MemberUnAuthorizedException();
        }
        return payment;
    }
}
