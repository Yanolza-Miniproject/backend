package com.miniproject.domain.member.entity;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.orders.entity.Orders;
import com.miniproject.domain.wish.entity.Wish;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String phoneNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Wish> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Basket> baskets = new ArrayList<>();

    @Builder
    public Member(String email, String nickname, String password, String phoneNumber,
                  List<Wish> likes, List<Orders> orders) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.likes = likes;
        this.orders = orders;
    }
}
