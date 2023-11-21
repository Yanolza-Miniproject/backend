package com.miniproject.domain.Order.repository;

import com.miniproject.domain.Order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
