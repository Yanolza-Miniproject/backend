package com.miniproject.domain.payment.dto.response;


import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.payment.entity.Payment;
import com.miniproject.domain.room.dto.response.RoomInOrdersGetResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private Long id;
    private int totalPrice;
    private int totalCount;
    private LocalDateTime paymentAt;

    private List<RoomInOrdersGetResponseDto> rooms;

    public PaymentResponseDto (Payment payment) {
        this.id = payment.getId();
        this.totalPrice = payment.getOrders().getTotalPrice();
        this.totalCount = payment.getOrders().getTotalCount();
        this.paymentAt = payment.getPaymentAt();
        this.rooms = payment.getOrders().getRoomInOrders().stream().map(
                roomInOrders -> new RoomInOrdersGetResponseDto(roomInOrders))
            .collect(Collectors.toList());
    }
}
