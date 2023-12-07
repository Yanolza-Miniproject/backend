package com.miniproject.domain.payment.entity;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime paymentAt;

    private PaymentStatus paymentStatus;
    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Payment(Long id, Orders orders,Member member) {
        this.id = id;
        this.member = member;
        this.orders = orders;
        this.paymentStatus = PaymentStatus.PAYING;
    }
    public void completePayment(){
        this.paymentStatus = PaymentStatus.PAYMENT_COMPLETE;
        this.paymentAt = LocalDateTime.now();
    }
}
