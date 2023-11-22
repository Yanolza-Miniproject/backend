package com.miniproject.domain.room.service;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.entity.BasketStatus;
import com.miniproject.domain.basket.repository.BasketRepository;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.entity.RoomInBasket;
import com.miniproject.domain.room.exception.RoomNotFoundException;
import com.miniproject.domain.room.repository.RoomInBasketRepository;
import com.miniproject.domain.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BasketService basketService;
    @Autowired
    private RoomInBasketRepository roomInBasketRepository;

    public void creatRoomInBasket(Long roomId, RoomRegisterRequestDto dto){
        Room room = roomRepository.findById(roomId).orElseThrow(
                RoomNotFoundException::new);
        Basket activateBasket = basketService.getActivateBasket();
        RoomInBasket roomInBasket = RoomInBasket.builder()
                .checkInAt(dto.getCheckInAt())
                .checkOutAt(dto.getCheckOutAt())
                .numberOfGuests(dto.getNumberOfGuests())
                .room(room)
//                .member(member)
                .basket(activateBasket)
                .build();
        roomInBasketRepository.save(roomInBasket);
        activateBasket.RegisterRoom(roomInBasket);

    }

    public void createSinglePurchase(Long roomId, RoomRegisterRequestDto dto){
        Room room = roomRepository.findById(roomId).orElseThrow(
                RoomNotFoundException::new);
        Basket singlePurchaseBasket = basketService.getSinglePurchase();
        RoomInBasket roomInBasket = RoomInBasket.builder()
                .checkInAt(dto.getCheckInAt())
                .checkOutAt(dto.getCheckOutAt())
                .numberOfGuests(dto.getNumberOfGuests())
                .room(room)
//                .member(member)
                .basket(singlePurchaseBasket)
                .build();
        singlePurchaseBasket.RegisterRoom(roomInBasket);
    }

}
