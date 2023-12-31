package com.miniproject.domain.wish.service;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.accommodation.exception.AccommodationNotFoundException;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.wish.dto.WishResponses.AccommodationWishResDto;
import com.miniproject.domain.wish.entity.Wish;
import com.miniproject.domain.wish.exception.AlreadyWishException;
import com.miniproject.domain.wish.exception.WishNotFoundException;
import com.miniproject.domain.wish.repository.WishRepository;
import com.miniproject.domain.wish.service.WishService;
import com.miniproject.global.resolver.LoginInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WithMockUser
class WishServiceTest {

    @InjectMocks
    private WishService wishService;

    @Mock
    private WishRepository wishRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private AccommodationRepository accommodationRepository;

    private Member member;
    private Accommodation accommodation1;
    private Accommodation accommodation2;
    private LoginInfo loginInfo;

    @BeforeEach
    public void init() {
        member = Member.builder()
                .email("test@test.com")
                .nickname("tester")
                .password("123")
                .password("010-1234-5678").build();

        accommodation1 = Accommodation.builder()
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
                .checkIn(LocalTime.of(11, 1))
                .checkOut(LocalTime.of(13, 1))
                .wishCount(0)
                .viewCount(0).build();

        accommodation2 = Accommodation.builder()
                .name("고려호텔")
                .type(AccommodationType.HOTEL)
                .address("서울시 종로구")
                .phoneNumber("02-1234-1234")
                .homepage("www.test.com")
                .infoDetail("테스트용 호텔입니다.")
                .thumbnailUrl(null)
                .categoryParking(true)
                .categoryCooking(true)
                .categoryPickup(false)
                .categoryAmenities("향수")
                .categoryDiningArea("바베큐장")
                .checkIn(LocalTime.of(11, 1))
                .checkOut(LocalTime.of(13, 1))
                .wishCount(0)
                .viewCount(0).build();

        loginInfo = new LoginInfo("user@example.com");
    }

    @Nested
    @DisplayName("좋아요 등록을")
    class AddWish {
        @Test
        @DisplayName("성공한다.")
        void will_success() {
            // given
            Long accommodationId = 1L;

            when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation1));
            when(memberService.getMemberByLoginInfo(loginInfo)).thenReturn(member);
            when(wishRepository.findByMemberAndAccommodation(member, accommodation1)).thenReturn(Optional.empty());
            when(wishRepository.save(any(Wish.class))).thenAnswer(invocation -> {
                Wish wish = invocation.getArgument(0);
                ReflectionTestUtils.setField(wish, "id", 1L);  // Set a dummy ID for the saved wish
                return wish;
            });

            // when
            Long savedWishId = wishService.saveWish(accommodationId, loginInfo);

            // then
            assertThat(1L).isEqualTo(savedWishId);
            verify(wishRepository, times(1)).save(any(Wish.class));
        }

        @Test
        @DisplayName("해당 숙소가 존재하지 않아 실패한다.")
        void _will_fail_by_no_accommodation() {
            // given
            Long accommodationId = 1L;

            when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.empty());

            // then
            assertThrows(AccommodationNotFoundException.class, () ->
                    wishService.saveWish(accommodationId, loginInfo));
        }

        @Test
        @DisplayName("이미 좋아요를 등록한 숙소이기 때문에 실패한다.")
        void _will_fail_by_already_wish() {
            // given
            Long accommodationId = 1L;
            Wish wish = Wish.builder()
                    .member(member)
                    .accommodation(accommodation1).build();

            when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation1));
            when(memberService.getMemberByLoginInfo(loginInfo)).thenReturn(member);
            when(wishRepository.findByMemberAndAccommodation(member, accommodation1)).thenReturn(Optional.of(wish));

            assertThrows(AlreadyWishException.class, () -> wishService.saveWish(accommodationId, loginInfo));
        }
    }

    @Nested
    @DisplayName("좋아요 삭제를")
    class DeleteWish {
        @Test
        @DisplayName("성공한다.")
        void _will_success() {
            // given
            Long accommodationId = 1L;
            Wish wish = Wish.builder()
                    .member(member)
                    .accommodation(accommodation1).build();

            when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation1));
            when(memberService.getMemberByLoginInfo(loginInfo)).thenReturn(member);
            when(wishRepository.findByMemberAndAccommodation(member, accommodation1)).thenReturn(Optional.of(wish));

            // when
            wishService.deleteWish(accommodationId, loginInfo);

            // then
            verify(wishRepository).delete(wish);
        }

        @Test
        @DisplayName("해당 숙소가 존재하지 않아 실패한다.")
        void _will_fail_by_no_accommodation() {
            // Given
            Long accommodationId = 1L;

            when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.empty());

            // Then
            assertThrows(AccommodationNotFoundException.class, () -> {
                // When
                wishService.deleteWish(accommodationId, loginInfo);
            });
        }

        @Test
        @DisplayName("해당 좋아요가 존재하지 않아 실패한다.")
        void _will_fail_by_no_wish() {
            // Given
            Long accommodationId = 1L;

            when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation1));
            when(memberService.getMemberByLoginInfo(loginInfo)).thenReturn(member);
            when(wishRepository.findByMemberAndAccommodation(member, accommodation1)).thenReturn(Optional.empty());

            // Then
            assertThrows(WishNotFoundException.class, () -> {
                // When
                wishService.deleteWish(accommodationId, loginInfo);
            });
        }
    }

    @Nested
    @DisplayName("사용자가 좋아요한 숙소 조회에")
    class getWishAccommodationInfos {
        @Test
        @DisplayName("성공한다.")
        public void _will_success() throws Exception {
            Wish wish1 = Wish.builder()
                    .member(member)
                    .accommodation(accommodation1).build();
            Wish wish2 = Wish.builder()
                    .member(member)
                    .accommodation(accommodation2).build();
            List<Wish> wishes = Arrays.asList(wish1, wish2);

            when(memberService.getMemberByLoginInfo(loginInfo)).thenReturn(member);
            when(wishRepository.findAllByMember(member)).thenReturn(wishes);

            AccommodationWishResDto dto1 = AccommodationWishResDto.fromEntity(wish1);
            AccommodationWishResDto dto2 = AccommodationWishResDto.fromEntity(wish2);
            List<AccommodationWishResDto> expectedDtos = Arrays.asList(dto1, dto2);

            // when
            List<AccommodationWishResDto> resultDtos = wishService.getWishes(loginInfo);

            // then
            assertThat(resultDtos).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(expectedDtos);
        }
    }
}