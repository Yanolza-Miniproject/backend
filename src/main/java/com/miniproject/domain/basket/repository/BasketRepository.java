package com.miniproject.domain.basket.repository;

import com.miniproject.domain.basket.entity.Basket;

import com.miniproject.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BasketRepository extends JpaRepository<Basket,Long> {

    List<Basket> findByMember(Member member);


}
