package com.miniproject.domain.Order.entity;

import com.miniproject.domain.Basket.entity.Basket;
import com.miniproject.domain.Member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
}
