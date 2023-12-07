package com.miniproject.domain.orders.dto.response;

import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.room.dto.response.RoomInOrdersGetResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrdersResponseDto {

    private Long id;
    private int totalPrice;
    private int totalCount;
    private LocalDateTime orderAt;
    private List<RoomInOrdersGetResponseDto> rooms;

    @Builder
    public OrdersResponseDto(Long id, int totalPrice, int totalCount
        , List<RoomInOrdersGetResponseDto> rooms) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.rooms = rooms;
    }

    public OrdersResponseDto(Orders orders) {
        this.id = orders.getId();
        this.totalPrice = orders.getTotalPrice();
        this.totalCount = orders.getTotalCount();
        this.orderAt = orders.getOrderAt();
        this.rooms = orders.getRoomInOrders().stream().map(
                RoomInOrdersGetResponseDto::new)
            .toList();
    }


}
