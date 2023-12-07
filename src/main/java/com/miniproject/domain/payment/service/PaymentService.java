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
import com.miniproject.domain.room.entity.RoomInventory;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomInventoryRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private final BasketService basketService;
    private final RoomInventoryRepository roomInventoryRepository;

    public PaymentResponseDto getPayment(Long paymentId,Member member) {
        return new PaymentResponseDto(checkGetPayment(paymentId, member));
    }

    public List<PaymentResponseDto> getPayments(Integer page, Integer pageSize, Member member) {
        Page<Payment> allByMemberContaining = paymentRepository.findAllByMember(
            PageRequest.of(page - 1, pageSize), member);
        return allByMemberContaining.stream()
            .map(PaymentResponseDto::new)
            .toList();
    }

    public void completePayment(Long paymentId, Member member){
        Payment payment = checkGetPayment(paymentId, member);
        payment.completePayment();

        extracted(payment);

        List<Room> rooms = payment.getOrders()
            .getRoomInOrders()
            .stream()
            .map(RoomInOrders::getRoom)
            .toList();

        Basket activateBasket = basketService.getActivateBasket(member);
        List<RoomInBasket> roomInBasketList = activateBasket.getRooms()
            .stream()
            .filter(roomInBasket -> rooms.contains(roomInBasket.getRoom()))
            .toList();

        activateBasket.deleteRoom(roomInBasketList);
        roomInBasketRepository.deleteAllInBatch(roomInBasketList);
    }

    /**
     *
     * 리팩토링 필요
     */
    private void extracted(Payment payment) {
        List<RoomInOrders> roomInOrders = payment.getOrders().getRoomInOrders();
        for (RoomInOrders roomInOrder : roomInOrders) {
            LocalDate checkInAt = roomInOrder.getCheckInAt();
            LocalDate checkOutAt = roomInOrder.getCheckOutAt();
            Room room = roomInOrder.getRoom();

            List<RoomInventory> roomInventories = room.getRoomInventories();

            List<RoomInventory> targetInventory1 = roomInventories.stream()
                            .filter(inven -> (inven.getDate().isAfter(checkInAt) && inven.getDate().isBefore(checkOutAt)) || inven.getDate().equals(checkInAt) )
                            .toList();

            List<RoomInventory> targetInventory2 = new ArrayList<>();
            for (RoomInventory inven : targetInventory1) {
                inven.minusInventory();
                targetInventory2.add(inven);
            }

            roomInventoryRepository.saveAll(targetInventory2);
        }
    }

    public Payment checkGetPayment(Long paymentId, Member member) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(PaymentNotFoundException::new);
        if(!member.getId().equals(payment.getOrders().getMember().getId())){
            throw new MemberUnAuthorizedException();
        }
        return payment;
    }
}
