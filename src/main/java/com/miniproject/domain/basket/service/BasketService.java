package com.miniproject.domain.basket.service;

import com.miniproject.domain.basket.dto.request.CheckBasketRequestDto;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.entity.Basket;
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
        if (basket.getRooms().isEmpty())
            throw new BasketEmptyException();

        List<RoomInBasket> roomInBasketList = dto.getIds()
            .stream()
            .map(id -> roomInBasketRepository.findById(id)
                .orElseThrow(RoomInBasketNotFoundException::new))
            .toList();
        roomInBasketRepository.deleteAllInBatch(roomInBasketList);
        basket.deleteRoom(roomInBasketList);
    }

    public Long registerOrder(CheckBasketRequestDto dto,Member member) {
        Basket basket = getActivateBasket(member);
        if (basket.getRooms().isEmpty()) {
            throw new BasketEmptyException();
        }
        Orders orders = Orders.builder()
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(basket.getTotalPrice())
            .totalCount(basket.getTotalCount())
            .build();
        Orders savedOrders = ordersRepository.save(orders);

        List<RoomInOrders> roomInOrdersList = dto.getIds()
            .stream()
            .map(id -> roomInBasketRepository.findById(id)
                .orElseThrow(RoomInBasketNotFoundException::new))
            .map(roomInBasket -> RoomInOrders.builder()
                .checkInAt(roomInBasket.getCheckInAt())
                .checkOutAt(roomInBasket.getCheckOutAt())
                .numberOfGuests(roomInBasket.getNumberOfGuests())
                .room(roomInBasket.getRoom())
                .member(member)
                .orders(savedOrders)
                .build()).toList();

        savedOrders.registerRooms(roomInOrdersList);
        roomInOrdersRepository.saveAll(roomInOrdersList);
        return savedOrders.getId();
    }


    public Basket getActivateBasket(Member member) {

        List<Basket> nowBasket = basketRepository.findByMember(member);
        return switch (nowBasket.size()) {
            case 1 -> nowBasket.get(0);
            case 0 -> basketRepository.save(new Basket(member));
            default -> deleteBasket(nowBasket,member);
        };
    }

    public Basket deleteBasket(List<Basket> basketList,Member member){
        List<RoomInBasket> roomInBasketList = new ArrayList<>();
        for (Basket basket : basketList) {
            List<RoomInBasket> byBasket = roomInBasketRepository.findByBasket(basket);
            roomInBasketList.addAll(byBasket);
        }

        Basket savedBasket = basketRepository.save(new Basket(member));

        for (RoomInBasket roomInBasket : roomInBasketList) {
            roomInBasket.changeBasket(savedBasket);
        }
        basketRepository.deleteAllInBatch(basketList);

        return savedBasket;
    }
}
