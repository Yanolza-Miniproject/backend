package com.miniproject.domain.wish.controller;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.wish.controller.WishController;
import com.miniproject.domain.wish.dto.WishResponses.AccommodationWishResDto;
import com.miniproject.domain.wish.entity.Wish;
import com.miniproject.domain.wish.service.WishService;
import com.miniproject.global.config.CustomHttpHeaders;
import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.service.JwtService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(WishController.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
class WishControllerTest {

    @InjectMocks
    private WishController wishController;

    @MockBean
    private WishService wishService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    final Long ACCOMMODATION_ID = 1L;
    private Member member;
    private HttpHeaders testAuthHeaders;

    private Accommodation accommodation1;
    private Accommodation accommodation2;
//    private LoginInfo loginInfo;

    private HttpHeaders createTestAuthHeader(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                CustomHttpHeaders.ACCESS_TOKEN,
                jwtService.createTokenPair(new JwtPayload(email, new Date())).accessToken()
        );
        return headers;
    }

    @BeforeEach
    public void init() {
        Member member = Member.builder()
                .id(1L).nickname("tester").email("tester@gmail.com").password("ffdfda231321@da").build();
        testAuthHeaders = createTestAuthHeader(member.getEmail());

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

//        loginInfo = new LoginInfo("user@example.com");
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(wishController).build();
    }

    @Nested
    @DisplayName("좋아요 등록")
    class AddWish {
        @Test
        @DisplayName("성공")
        @WithMockUser
        void _will_success() throws Exception {

            when(wishService.saveWish(anyLong(), any())).thenReturn(1L);

            mockMvc.perform(post("/api/v1/wish/{accommodation_id}", ACCOMMODATION_ID)
                    .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("좋아요 성공"))
                    .andDo(print());

            verify(wishService, times(1)).saveWish(eq(ACCOMMODATION_ID), any());
        }
    }

    @Nested
    @DisplayName("좋아요 취소")
    class DeleteWish {
        @Test
        @DisplayName("성공")
        public void _will_success() throws Exception {

            ResponseDTO response = wishController.cancelWish(eq(ACCOMMODATION_ID), any());

            mockMvc.perform(delete("/api/v1/wish/{accommodation_id}", ACCOMMODATION_ID).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("좋아요 취소"))
                    .andDo(print());

            verify(wishService, times(1)).deleteWish(eq(ACCOMMODATION_ID), any());
            assertThat("좋아요 취소").isEqualTo(response.getMessage());
        }
    }

    @Nested
    @DisplayName("좋아요한 숙소 정보 조회")
    class getWishAccommodationInfos {
        @Test
        @DisplayName("성공")
        public void _will_success() throws Exception {
            // given
            Wish wish1 = Wish.builder()
                    .member(member)
                    .accommodation(accommodation1).build();
            Wish wish2 = Wish.builder()
                    .member(member)
                    .accommodation(accommodation2).build();
            List<Wish> wishes = Arrays.asList(wish1, wish2);
            AccommodationWishResDto dto1 = AccommodationWishResDto.fromEntity(wish1);
            AccommodationWishResDto dto2 = AccommodationWishResDto.fromEntity(wish2);
            List<AccommodationWishResDto> expectedDtos = Arrays.asList(dto1, dto2);

            // when
            when(wishService.getWishes(any())).thenReturn(expectedDtos);

            ResponseDTO<List<AccommodationWishResDto>> response = wishController.getWishes(any());

            // then
            mockMvc.perform(get("/api/v1/wish").with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("좋아요 리스트 조회 성공"))
                    .andExpect(jsonPath("$.data").value(wishes))
                    .andDo(print());

            verify(wishService).getWishes(any());
            assertThat("좋아요 리스트 조회 성공").isEqualTo(response.getMessage());
            assertThat(expectedDtos).isEqualTo(response.getData());
        }
    }
}