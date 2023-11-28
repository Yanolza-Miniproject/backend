package com.miniproject.domain.orders.repository;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.orders.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByMember(Member member);

}
