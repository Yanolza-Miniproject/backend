package com.miniproject.domain.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BasketService basketService;
    private final RoomInBasketRepository roomInBasketRepository;
    private final OrdersRepository ordersRepository;
    private final RoomInOrdersRepository roomInOrdersRepository;

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
}
