package com.miniproject.wish.service;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.repository.MemberRepository;
import com.miniproject.domain.wish.dto.WishResponses;
import com.miniproject.domain.wish.entity.Wish;
import com.miniproject.domain.wish.repository.WishRepository;
import com.miniproject.domain.wish.service.WishService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WithMockUser
class WishServiceTest {

    @InjectMocks
    private WishService wishService;

    @Mock
    private WishRepository wishRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    private Member member;
    private Accommodation accommodation;

    @BeforeEach
    public void init() {
        member = Member.builder()
                .email("test@test.com")
                .name("tester")
                .password("123")
                .password("010-1234-5678").build();

        accommodation = Accommodation.builder()
                .name("신라호텔")
                .type("관광호텔")
                .address("경기도 용인시 수지구")
                .phoneNumber("02-1234-1234")
                .homepage("www.test.com")
                .infoDetail("테스트용 호텔입니다.")
                .thumbnailUrl(null)
                .categoryParking(true)
                .categoryCooking(true)
                .categoryPickup(false)
                .categoryAmenities(false)
                .categoryDiningArea(true)
                .checkIn(LocalDateTime.parse("2020-01-01T11:00:00"))
                .checkOut(LocalDateTime.parse("2020-01-01T11:00:00"))
                .wishCount(0)
                .viewCount(0).build();
    }

    @Nested
    @DisplayName("좋아요 등록을")
    class AddWish {
        @Test
        @DisplayName("성공한다.")
        void will_success() {
            Wish wish = Wish.builder()
                    .accommodation(accommodation)
                    .member(member).build();

            when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));
            when(memberRepository.findByEmail(eq(member.getEmail()))).thenReturn(Optional.of(member));

            wishService.saveWish(accommodation, LoginInfo);

            verify(wishService, times(1)).saveWish(any);
        }

        @Test
        @DisplayName("해당 숙소가 존재하지 않아 실패한다.")
        void _will_fail_by_no_accommodation() {

            when(wishService.saveWish(any(Long.class), any(LoginInfo.class)));

        }

        @Test
        @DisplayName("이미 좋아요를 등록한 숙소이기 때문에 실패한다.")
        void _will_fail_by_already_wish() {

        }
    }

    @Nested
    @DisplayName("사용자가 좋아요한 숙소 조회에")
    class getWishAccommodationInfos {
        @Test
        @DisplayName("성공한다.")
        public void _will_success() throws Exception {

        }

        @Test
        @DisplayName("좋아요한 숙소가 없다.")
        public void _will_nothing() {

        }
    }

    @Nested
    @DisplayName("특정 숙소의 좋아요 개수를")
    class WishCounts {
        @Test
        @DisplayName("한 개 증가시킨다.")
        public void _will_plus_one() {

        }

        @Test
        @DisplayName("한 개 감소시킨다.")
        public void _will_minus_one() {

        }
    }
}