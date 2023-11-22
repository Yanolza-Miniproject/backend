package com.miniproject.domain.room.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoomRegisterRequestDto {
    LocalDateTime checkInAt;
    LocalDateTime checkOutAt;
    int numberOfGuests;
}
