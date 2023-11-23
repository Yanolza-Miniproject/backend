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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalPrice;
    private int totalCount;

    @OneToMany(mappedBy = "basket")
    private List<RoomInBasket> rooms = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Basket(){
        this.totalPrice = 0;
        this.totalCount = 0;
    }


    public void RegisterRoom(RoomInBasket roomInBasket) {
        this.totalPrice += roomInBasket.getRoom().getPrice();
        this.totalCount +=1;
    }
}
