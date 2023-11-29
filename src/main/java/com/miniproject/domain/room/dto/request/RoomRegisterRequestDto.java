package com.miniproject.domain.room.dto.request;

import java.time.LocalDate;
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
    LocalDate checkInAt;
    LocalDate checkOutAt;
    int numberOfGuests;
}
