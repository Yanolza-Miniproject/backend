package com.miniproject.domain.Member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Getter
@EqualsAndHashCode(of = "id")
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

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;


    @Builder
    private Member(Long id, String email, String name, String password, String number) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = number;
    }

}
