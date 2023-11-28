package com.miniproject.domain.basket.dto.response;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.room.dto.response.RoomInBasketGetResponseDto;
import com.miniproject.domain.room.entity.RoomInBasket;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BasketResponseDto {
    private Long id;
    private int totalPrice;
    private int totalCount;
    private List<RoomInBasketGetResponseDto> rooms;

    @Builder
    public BasketResponseDto(Long id, int totalPrice, int totalCount
            , List<RoomInBasketGetResponseDto> rooms) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.rooms = rooms;
    }

    public BasketResponseDto (Basket basket) {
        this.id = basket.getId();
        this.totalPrice = basket.getTotalPrice();
        this.totalCount = basket.getTotalCount();
        this.rooms = basket.getRooms().stream().map(
                        RoomInBasketGetResponseDto::new)
                .collect(Collectors.toList());
    }
}
