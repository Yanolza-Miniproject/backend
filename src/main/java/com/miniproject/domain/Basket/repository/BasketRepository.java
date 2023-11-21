package com.miniproject.domain.Basket.repository;

import com.miniproject.domain.Basket.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket,Long> {

}
