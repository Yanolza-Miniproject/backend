package com.miniproject.domain.wish.repository;


import com.miniproject.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish,Long> {

}
