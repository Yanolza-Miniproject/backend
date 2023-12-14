package com.miniproject.domain.accommodation.dto.request;

public record AccommodationRequest(
        Integer categoryParking,
        Integer categoryCooking,
        Integer categoryPickup,
        Integer type,
        Integer wishCount,
        Integer region
) {
}
