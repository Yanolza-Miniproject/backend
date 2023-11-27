package com.miniproject.domain.room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRegisterRequestDto {
    LocalDateTime checkInAt;
    LocalDateTime checkOutAt;
    int numberOfGuests;
}
