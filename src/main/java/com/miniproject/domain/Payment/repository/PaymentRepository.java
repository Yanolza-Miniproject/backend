package com.miniproject.domain.Payment.repository;

import com.miniproject.domain.Payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
