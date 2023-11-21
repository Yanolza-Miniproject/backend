package com.miniproject.domain.like.repository;


import com.miniproject.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

}
