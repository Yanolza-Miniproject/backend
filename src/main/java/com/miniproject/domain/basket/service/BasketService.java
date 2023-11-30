package com.miniproject.domain.basket.service;

import com.miniproject.domain.basket.dto.request.CheckBasketRequestDto;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.exception.BasketDuplicateActivateException;
import com.miniproject.domain.basket.exception.BasketEmptyException;
import com.miniproject.domain.basket.repository.BasketRepository;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.room.entity.RoomInBasket;
import com.miniproject.domain.room.entity.RoomInOrders;
import com.miniproject.domain.room.exception.RoomInBasketNotFoundException;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomInOrdersRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {


    private final BasketRepository basketRepository;

    private final OrdersRepository ordersRepository;

    private final RoomInBasketRepository roomInBasketRepository;

    private final RoomInOrdersRepository roomInOrdersRepository;

    public BasketResponseDto getBasket(Member member) {
        return new BasketResponseDto(getActivateBasket(member));
    }

    public void deleteRoomInBasket(CheckBasketRequestDto dto,Member member) {
        Basket basket = getActivateBasket(member);
        if (basket.getRooms().size() == 0) {
            throw new BasketEmptyException();
        }
        for (Long id : dto.getIds()) {
            RoomInBasket roomInBasket = roomInBasketRepository.findById(id)
                .orElseThrow(RoomInBasketNotFoundException::new);
            roomInBasketRepository.delete(roomInBasket);
            basket.deleteRoom(roomInBasket);
        }

    }


    public Long registerOrder(CheckBasketRequestDto dto,Member member) {
        Basket basket = getActivateBasket(member);
        if (basket.getRooms().size() == 0) {
            throw new BasketEmptyException();
        }
        Orders orders = Orders.builder()
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(basket.getTotalPrice())
            .totalCount(basket.getTotalCount()).build();
        Orders save = ordersRepository.save(orders);
        List<RoomInOrders> roomInOrdersList = new ArrayList<>();
        for (Long id : dto.getIds()) {
            RoomInBasket roomInBasket = roomInBasketRepository.findById(id)
                .orElseThrow(RoomInBasketNotFoundException::new);
            RoomInOrders roomInOrders = RoomInOrders.builder()
                .checkInAt(roomInBasket.getCheckInAt())
                .checkOutAt(roomInBasket.getCheckOutAt())
                .numberOfGuests(roomInBasket.getNumberOfGuests())
                .room(roomInBasket.getRoom())
                .member(member)
                .orders(save)
                .build();
            roomInOrdersList.add(roomInOrders);
        }
        save.registerRooms(roomInOrdersList);
        roomInOrdersRepository.saveAll(roomInOrdersList);
        return save.getId();
    }


    public Basket getActivateBasket(Member member) {

        List<Basket> nowBasket = basketRepository.findByMember(member);
        Basket basket = switch (nowBasket.size()) {
            case 1 -> nowBasket.get(0);
            case 0 -> basketRepository.save(new Basket(member));
            default -> throw new BasketDuplicateActivateException();
        };
        return basket;
    }
}
