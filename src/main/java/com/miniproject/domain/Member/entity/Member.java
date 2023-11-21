package com.miniproject.domain.Member.entity;

import com.miniproject.domain.Basket.entity.Basket;
import com.miniproject.domain.Like.entity.Like;
import com.miniproject.domain.Order.entity.Order;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

import java.util.ArrayList;

@Getter
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private String phoneNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders = new ArrayList<>();



    @Builder
    private Member(Long id, String email, String name, String password, String number) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = number;
    }

}
