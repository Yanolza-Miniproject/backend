package com.miniproject.domain.basket.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.basket.controller.BasketController;
import com.miniproject.domain.basket.dto.request.CheckBasketRequestDto;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.room.dto.response.RoomInBasketGetResponseDto;
import com.miniproject.global.config.CustomHttpHeaders;
import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.service.JwtService;
import com.miniproject.global.resolver.LoginInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;
    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    private Member member;
    private HttpHeaders testAuthHeaders;

    private HttpHeaders createTestAuthHeader(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
            CustomHttpHeaders.ACCESS_TOKEN,
            jwtService.createTokenPair(new JwtPayload(email, new Date())).accessToken()
        );
        return headers;
    }

    @BeforeEach
    public void beforeEach() {
        Member member = Member.builder()
            .nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        testAuthHeaders = createTestAuthHeader(member.getEmail());
    }

    @DisplayName("getBasket()은 장바구니 조회를 할 수 있다.")
    @Test
    @WithMockUser
    public void getBasket_willSuccess() throws Exception {
        //given


        List<RoomInBasketGetResponseDto> dtoList = new ArrayList<>();
        RoomInBasketGetResponseDto build = RoomInBasketGetResponseDto.builder()
            .id(1L)
            .accommodationName("제주호텔")
            .roomName("스탠다드")
            .price(50000)
            .numberOfGuests(2)
            .checkInAt(LocalDate.now())
            .checkOutAt(LocalDate.now().withDayOfMonth(30))
            .roomUrl("www.feafafsadf.jpg")
            .build();
        dtoList.add(build);
        BasketResponseDto dto = BasketResponseDto.builder().id(1L).totalPrice(50000).totalCount(2)
            .rooms(dtoList).build();

        when(basketService.getBasket(any())).thenReturn(dto);
        when(memberService.getMemberByLoginInfo(any())).thenReturn(member);
        //when, then
        mockMvc.perform(get("/api/v1/baskets")
                .with(csrf())
                .headers(testAuthHeaders))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("장바구니 조회 성공"))
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.totalPrice").isNumber())
            .andExpect(jsonPath("$.data.totalCount").isNumber())
            .andExpect(jsonPath("$.data.rooms").exists())
            .andDo(print());
    }

    @DisplayName("registerOrder()은 주문을 생성할 수 있다.")
    @Test
    @WithMockUser
    public void registerOrder_willSuccess() throws Exception {
        //given

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        CheckBasketRequestDto dto = CheckBasketRequestDto.builder().ids(ids).build();

        when(basketService.registerOrder(any(CheckBasketRequestDto.class), any()))
            .thenReturn(1L);

        String content = objectMapper.writeValueAsString(dto);
        when(memberService.getMemberByLoginInfo(any())).thenReturn(member);
        //when, then
        mockMvc.perform(post("/api/v1/baskets/orders")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .headers(testAuthHeaders))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("장바구니에서 주문 생성"))
            .andExpect(jsonPath("$.data").value(1L))
            .andDo(print());
    }


}
