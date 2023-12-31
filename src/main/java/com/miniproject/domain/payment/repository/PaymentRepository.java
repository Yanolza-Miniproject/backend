package com.miniproject.domain.payment.repository;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.payment.entity.Payment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Page<Payment> findAllByMember(Pageable pageable, Member member);
}
