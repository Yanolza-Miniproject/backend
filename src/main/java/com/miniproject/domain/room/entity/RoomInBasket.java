package com.miniproject.domain.room.entity;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInBasket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInAt;

    private LocalDate checkOutAt;

    private int numberOfGuests;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public void changeBasket(Basket basket) {
        this.basket = basket;
    }

    @Builder
    public RoomInBasket(Long id, LocalDate checkInAt, LocalDate checkOutAt,
        int numberOfGuests, Basket basket, Member member, Room room) {
        this.id = id;
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.basket = basket;
        this.member = member;
    }

}
