package com.miniproject.domain.room.service;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInBasket;
import com.miniproject.domain.room.entity.RoomInOrders;
import com.miniproject.domain.room.exception.RoomNotFoundException;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomInOrdersRepository;
import com.miniproject.domain.room.repository.RoomRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final BasketService basketService;
    private final RoomInBasketRepository roomInBasketRepository;
    private final OrdersRepository ordersRepository;
    private final RoomInOrdersRepository roomInOrdersRepository;

    @Transactional
    public RoomInBasket creatRoomInBasket(Long roomId, RoomRegisterRequestDto dto, Member member){
        Room room = roomRepository.findById(roomId).orElseThrow(
                RoomNotFoundException::new);
        Basket activateBasket = basketService.getActivateBasket(member);
        RoomInBasket roomInBasket = RoomInBasket.builder()
                .checkInAt(dto.getCheckInAt())
                .checkOutAt(dto.getCheckOutAt())
                .numberOfGuests(dto.getNumberOfGuests())
                .room(room)
                .member(member)
                .basket(activateBasket)
                .build();
        activateBasket.RegisterRoom(roomInBasket);
        return roomInBasketRepository.save(roomInBasket);
    }
  
    @Transactional
    public Long createSingleOrders(Long roomId, RoomRegisterRequestDto dto,Member member) {
        Room room = roomRepository.findById(roomId).orElseThrow(
            RoomNotFoundException::new);
        Orders orders = Orders.builder()
            .orderAt(LocalDateTime.now())
            .member(member)
            .totalPrice(room.getPrice())
            .totalCount(dto.getNumberOfGuests()).build();
        Orders save = ordersRepository.save(orders);
        RoomInOrders roomInOrders = RoomInOrders.builder()
            .checkInAt(dto.getCheckInAt())
            .checkOutAt(dto.getCheckOutAt())
            .numberOfGuests(dto.getNumberOfGuests())
            .room(room)
            .member(member)
            .orders(save)
            .build();
        save.registerRooms(roomInOrders);
        roomInOrdersRepository.save(roomInOrders);
        return save.getId();
    }

    @Transactional
    public RoomDetailResponse getRoomById(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow();

        return RoomDetailResponse.fromEntity(room);
    }

    @Transactional
    public Page<RoomDetailResponse> getRoomsByAccommodationId(Long accommodationId,
                                                              Pageable pageable,
                                                              Integer categoryTv,
                                                              Integer categoryPc,
                                                              Integer categoryInternet,
                                                              Integer categoryRefrigerator,
                                                              Integer categoryBathingFacilities,
                                                              Integer categoryDryer) {
        Page<Room> result = roomRepository
                .findByAccommodationIdAndCategory(
                        accommodationId,
                        pageable,
                        categoryTv,
                        categoryPc,
                        categoryInternet,
                        categoryRefrigerator,
                        categoryBathingFacilities,
                        categoryDryer);

        return result.map(RoomDetailResponse::fromEntity);

    }
}
