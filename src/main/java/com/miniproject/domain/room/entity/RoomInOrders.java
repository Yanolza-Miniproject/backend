package com.miniproject.domain.room.entity;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkInAt;

    private LocalDateTime checkOutAt;

    private int numberOfGuests;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public RoomInOrders(LocalDateTime checkInAt, LocalDateTime checkOutAt,
        int numberOfGuests, Orders orders, Member member,Room room) {
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.orders = orders;
        this.member = member;
    }
}
