package com.miniproject.domain.room.dto.response;

import com.miniproject.domain.room.entity.RoomInBasket;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RoomInBasketGetResponseDto {

    Long id;
    String accommodationName;
    String roomName;
    int price;
    int numberOfGuests;
    LocalDate checkInAt;
    LocalDate checkOutAt;
    String roomUrl;

    public RoomInBasketGetResponseDto(RoomInBasket room) {
        this.id = room.getId();
        this.accommodationName = room.getRoom().getAccommodation().getName();
        this.roomName = room.getRoom().getName();
        this.price = room.getRoom().getPrice();
        this.numberOfGuests = room.getNumberOfGuests();
        this.checkInAt = room.getCheckInAt();
        this.checkOutAt = room.getCheckOutAt();
        if (!room.getRoom().getRoomImages().isEmpty()) {
            this.roomUrl = room.getRoom().getRoomImages().get(0).getImageUrl();
        }
    }

    @Builder
    public RoomInBasketGetResponseDto(Long id, String accommodationName,
        String roomName, int price, int numberOfGuests,
        LocalDate checkInAt, LocalDate checkOutAt, String roomUrl) {

        this.id = id;
        this.accommodationName = accommodationName;
        this.roomName = roomName;
        this.price = price;
        this.numberOfGuests = numberOfGuests;
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
        this.roomUrl = roomUrl;
    }
}
