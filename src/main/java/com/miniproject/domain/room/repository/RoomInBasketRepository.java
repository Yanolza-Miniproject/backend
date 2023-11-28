package com.miniproject.domain.room.repository;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.room.entity.RoomInBasket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInBasketRepository extends JpaRepository<RoomInBasket,Long> {

    List<RoomInBasket> findByBasket(Basket basket);

}
