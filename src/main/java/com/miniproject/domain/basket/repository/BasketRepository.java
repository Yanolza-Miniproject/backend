package com.miniproject.domain.basket.repository;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.entity.BasketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket,Long> {

    List<Basket> findByBasketStatus(BasketStatus basketStatus);

}
