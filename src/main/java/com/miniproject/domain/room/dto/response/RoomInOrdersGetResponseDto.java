package com.miniproject.domain.room.dto.response;

import com.miniproject.domain.room.entity.RoomInOrders;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RoomInOrdersGetResponseDto {
    Long id;
    String accommodationName;
    String roomName;
    int price;
    int numberOfGuests;
    LocalDateTime checkInAt;
    LocalDateTime checkOutAt;
    String roomUrl;

    public RoomInOrdersGetResponseDto(RoomInOrders room) {
        this.id = room.getId();
        this.accommodationName = room.getRoom().getAccommodation().getName();
        this.roomName = room.getRoom().getName();
        this.price = room.getRoom().getPrice();
        this.numberOfGuests = room.getNumberOfGuests();
        this.checkInAt = room.getCheckInAt();
        this.checkOutAt = room.getCheckOutAt();
        // image 가 null 일때, 예외처리 필요
        this.roomUrl = room.getRoom().getRoomImages().get(0).getImageUrl();

    }
}
