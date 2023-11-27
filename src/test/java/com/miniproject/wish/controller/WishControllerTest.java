package com.miniproject.wish.controller;

import com.miniproject.domain.wish.controller.WishController;
import com.miniproject.domain.wish.dto.WishResponses.AccommodationWishResDto;
import com.miniproject.domain.wish.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class WishControllerTest {

    final Long ACCOMMODATION_ID = 1L;

    @InjectMocks
    private WishController wishController;

    @Mock
    private WishService wishService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(wishController).build();
    }

    @Nested
    @DisplayName("좋아요 등록을")
    class AddWish {
        @Test
        @DisplayName("성공한다.")
        @WithMockUser
        void _will_success() throws Exception {

            // WishService.saveWith()의 반환값은 void이다. 따라서 반환값이 없다. 테스트를 위해서라도 wishId를 반환하는게 좋을까?
            when(wishService.saveWish(eq(ACCOMMODATION_ID), any(LoginInfo.class)));

            mockMvc.perform(post("/api/v1/wish/{accommodation_id}", ACCOMMODATION_ID).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("좋아요 성공"))
                    .andDo(print());

            verify(wishService, times(1)).saveWish(eq(ACCOMMODATION_ID), any(LoginInfo.class));
        }
    }

    @Nested
    @DisplayName("좋아요 취소가")
    class DeleteWish {
        @Test
        @DisplayName("성공한다.")
        public void _will_success() throws Exception {

            when(wishService.deleteWish(eq(ACCOMMODATION_ID), any(LoginInfo.class)));

            mockMvc.perform(delete("/api/v1/wish/{accommodation_id}", ACCOMMODATION_ID).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("좋아요 취소"))
                    .andDo(print());

            verify(wishService, times(1)).saveWish(eq(ACCOMMODATION_ID), any(LoginInfo.class));
        }
    }

    @Nested
    @DisplayName("좋아요한 숙소 정보 조회에")
    class getWishAccommodationInfos {
        @Test
        @DisplayName("성공한다.")
        public void _will_success() throws Exception {
            // given
            List<AccommodationWishResDto> wishes = new ArrayList<>();

            // when
            when(wishService.getWishes(any(LoginInfo.class))).thenReturn(wishes);

            // then
            mockMvc.perform(get("/api/v1/wish").with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("좋아요 리스트 조회 성공"))
                    .andExpect(jsonPath("$.data").value(wishes))
                    .andDo(print());
        }
    }
}