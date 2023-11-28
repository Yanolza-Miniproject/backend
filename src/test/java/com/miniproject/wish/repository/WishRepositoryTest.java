package com.miniproject.wish.repository;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.wish.entity.Wish;
import com.miniproject.domain.wish.repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;
    private Member member;
    private Accommodation accommodation;

    @BeforeEach
    public void init() {
        member = Member.builder()
                .email("test@test.com")
                .nickname("tester")
                .password("123")
                .password("010-1234-5678").build();

        accommodation = Accommodation.builder()
                .name("신라호텔")
                .type(AccommodationType.HOTEL)
                .address("경기도 용인시 수지구")
                .phoneNumber("02-1234-1234")
                .homepage("www.test.com")
                .infoDetail("테스트용 호텔입니다.")
                .thumbnailUrl(null)
                .categoryParking(true)
                .categoryCooking(true)
                .categoryPickup(false)
                .categoryAmenities("향수")
                .categoryDiningArea("바베큐장")
                .checkIn(LocalTime.parse("T11:00:00"))
                .checkOut(LocalTime.parse("T11:00:00"))
                .wishCount(0)
                .viewCount(0).build();
    }

    @Test
    @DisplayName("좋아요 저장 리포지토리 테스트")
    void saveWishTest() {
        // given
        Wish wish = Wish.builder()
                .accommodation(accommodation)
                .member(member)
                .build();

        // when
        Wish saved = wishRepository.save(wish);

        // then
        assertThat(saved).extracting("accmmodation", "member").containsExactly(
                wish.getAccommodation(), wish.getMember());
    }

    @Test
    @DisplayName("좋아요 삭제 리포지토리 테스트")
    void deleteWish() {
        // given
        Wish wish = Wish.builder()
                .accommodation(accommodation)
                .member(member)
                .build();
        Wish saved = wishRepository.save(wish);

        // when
        wishRepository.delete(wish);

        // then
        boolean isPresent = wishRepository.existsById(saved.getId());
        assertThat(isPresent).isFalse();
    }

    @Test
    @DisplayName("사용자와 숙박 정보로 좋아요 조회 테스트")
    void findMyMemberANdAccommodation() {
        // given
        Wish wish = Wish.builder()
                .accommodation(accommodation)
                .member(member)
                .build();
        Wish saved = wishRepository.save(wish);

        // when
        Wish fined = wishRepository.findByMemberAndAccommodation(member, accommodation).get();

        // then
        assertThat(saved.getAccommodation()).isEqualTo(fined.getAccommodation());
        assertThat(saved.getMember()).isEqualTo(fined.getMember());
    }
}