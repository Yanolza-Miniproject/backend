package com.miniproject.basket.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.basket.controller.BasketController;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.room.dto.response.RoomInBasketGetResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Nested;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@WebMvcTest(BasketController.class)
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("getBasket()은 장바구니 조회를 할 수 있다.")
    @Test
    @WithMockUser
    public void getBasket_willSuccess() throws Exception {
        //given
        Member member = Member.builder()
            .id(1L).name("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        List<RoomInBasketGetResponseDto> dtoList = new ArrayList<>();
        RoomInBasketGetResponseDto build = RoomInBasketGetResponseDto.builder()
            .id(1L)
            .accommodationName("제주호텔")
            .roomName("스탠다드")
            .price(50000)
            .numberOfGuests(2)
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
            .roomUrl("www.feafafsadf.jpg")
            .build();
        dtoList.add(build);
        BasketResponseDto dto = BasketResponseDto.builder().id(1L).totalPrice(50000).totalCount(2)
            .rooms(dtoList).build();

        when(basketService.getBasket(any(Member.class))).thenReturn(dto);

        String content = objectMapper.writeValueAsString(member);

        //when, then
        mockMvc.perform(get("/api/v1/baskets")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
            .andExpect(jsonPath("$.message").value("장바구니 조회 성공"))
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.totalPrice").isNumber())
            .andExpect(jsonPath("$.data.totalCount").isNumber())
            .andExpect(jsonPath("$.data.rooms").exists())

            .andDo(print());

    }

}
