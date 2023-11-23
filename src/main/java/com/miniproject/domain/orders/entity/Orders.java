package com.miniproject.domain.orders.entity;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.room.entity.RoomInBasket;
import com.miniproject.domain.room.entity.RoomInOrders;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderAt;

    private int totalPrice;
    private int totalCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "orders")
    private List<RoomInOrders> roomInOrders  = new ArrayList<>();

    @Builder
    public Orders(LocalDateTime orderAt, int totalPrice, int totalCount,
        Member member,List<RoomInOrders> roomInOrders) {
        this.orderAt = orderAt;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.member = member;
        this.roomInOrders = roomInOrders;
        this.orderAt = LocalDateTime.now();
    }
}
