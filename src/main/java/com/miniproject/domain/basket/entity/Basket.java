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
@NoArgsConstructor
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
    public Basket(Member member, Long id){
        this.id = id;
        this.totalPrice = 0;
        this.totalCount = 0;
        this.member = member;
    }
    public Basket(Member member){
        this.totalPrice = 0;
        this.totalCount = 0;
        this.member = member;
    }



    public void RegisterRoom(RoomInBasket roomInBasket) {
        this.rooms.add(roomInBasket);
        this.totalPrice += roomInBasket.getRoom().getPrice();
        this.totalCount +=1;
    }
    public void clearBasket(){
        this.totalPrice = 0;
        this.totalCount = 0;
    }
}
