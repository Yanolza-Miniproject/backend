package com.miniproject.domain.wish.repository;


import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish,Long> {

    Optional<Wish> findByMemberAndAccommodation(Member member, Accommodation accommodation);
    List<Wish> findAllByMember(Member member);
}
