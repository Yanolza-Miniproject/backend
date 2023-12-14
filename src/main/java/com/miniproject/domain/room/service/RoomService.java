package com.miniproject.domain.room.service;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.orders.repository.OrdersRepository;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.dto.request.RoomRequest;
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
import com.miniproject.domain.room.dto.response.RoomSimpleResponse;
import com.miniproject.domain.room.entity.RoomInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


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
        activateBasket.registerRoom(roomInBasket);
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
            .totalCount(dto.getNumberOfGuests())
            .build();
        Orders savedOrders = ordersRepository.save(orders);
        RoomInOrders roomInOrders = RoomInOrders.builder()
            .checkInAt(dto.getCheckInAt())
            .checkOutAt(dto.getCheckOutAt())
            .numberOfGuests(dto.getNumberOfGuests())
            .room(room)
            .member(member)
            .orders(savedOrders)
            .build();
        savedOrders.registerRooms(roomInOrders);
        roomInOrdersRepository.save(roomInOrders);
        return savedOrders.getId();
    }

    @Transactional
    public RoomDetailResponse getRoomById(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        return RoomDetailResponse.fromEntity(room);
    }

    @Transactional
    public List<RoomSimpleResponse> getRoomsByAccommodationId(Long accommodationId,
                                                              Pageable pageable,
                                                              RoomRequest request) {

        Page<Room> result = roomRepository
                .findByAccommodationIdAndCategory(
                        accommodationId,
                        pageable,
                        request);



        return result
                .stream()
                .filter(room -> room.isAvailable(request.checkinDay(), request.checkoutDay()))
                .map(RoomSimpleResponse::fromEntity)
                .collect(Collectors.toList());

    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // 매일 밤 12시에 실행
    public void updateRoomInventories() {
        List<Room> rooms = roomRepository.findAll();

        for (Room room : rooms) {
            List<RoomInventory> roomInventories = room.getRoomInventories();

            // 오늘보다 이전의 RoomInventory 삭제
            roomInventories.removeIf(roomInventory -> roomInventory.getDate().isBefore(LocalDate.now()));

            // 새로운 RoomInventory 생성 및 추가
            RoomInventory newRoomInventory = RoomInventory.builder()
                    .inventory(room.getInventory())
                    .date(LocalDate.now().plusDays(36)) // 37일 후 날짜 설정
                    .room(room)
                    .build();

            roomInventories.add(newRoomInventory);

            room.updateRoomInventory(roomInventories);
        }

    }
}
