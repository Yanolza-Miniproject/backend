package com.miniproject.domain.room.dto.request;

import java.time.LocalDate;

public record RoomRequest(
        Integer categoryTv,
        Integer categoryPc,
        Integer categoryInternet,
        Integer categoryRefrigerator,
        Integer categoryBathingFacilities,
        Integer categoryDryer,
        LocalDate checkinDay,
        LocalDate checkoutDay
) {
}
