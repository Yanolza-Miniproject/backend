package com.miniproject.wish.repository;

import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.member.repository.MemberRepository;
import com.miniproject.domain.wish.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private MemberRepository memberRepository;






}