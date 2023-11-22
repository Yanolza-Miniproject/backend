package com.miniproject.domain.basket.entity;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.room.entity.RoomInBasket;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalPrice;
    private int totalCount;
    private String status;

    @OneToMany(mappedBy = "basket")
    private List<RoomInBasket> rooms = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}