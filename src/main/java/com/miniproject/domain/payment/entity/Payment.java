package com.miniproject.domain.payment.entity;

import com.miniproject.domain.orders.entity.Orders;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    private String paymentType;

    private PaymentStatus paymentStatus;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Builder
    public Payment(String paymentType, Orders orders) {
        this.paymentType = paymentType;
        this.orders = orders;
        this.paymentStatus = PaymentStatus.PAYING;
    }
    public void completePayment(){
        this.paymentStatus = PaymentStatus.PAYMENT_COMPLETE;
        this.paymentAt = LocalDateTime.now();
    }
}
