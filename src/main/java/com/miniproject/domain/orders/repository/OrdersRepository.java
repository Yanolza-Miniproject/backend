package com.miniproject.domain.orders.repository;

import com.miniproject.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

}
