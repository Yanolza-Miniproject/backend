package com.miniproject.domain.accommodation.controller;

import com.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.miniproject.domain.accommodation.dto.response.AccommodationSimpleResponse;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.accommodation.repository.AccommodationRepositoryCustom;
import com.miniproject.domain.accommodation.repository.AccommodationRepositoryImpl;
import com.miniproject.domain.accommodation.service.AccommodationService;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.DuplicateEmailException;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.orders.dto.response.OrdersResponseDto;
import com.miniproject.domain.orders.service.OrdersService;
import com.miniproject.domain.payment.service.PaymentService;
import com.miniproject.domain.room.dto.response.RoomInOrdersGetResponseDto;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.global.config.CustomHttpHeaders;
import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.service.JwtService;
import com.miniproject.global.resolver.LoginInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.domain.Page;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccommodationService accommodationService;
    @MockBean
    private AccommodationRepository accommodationRepository;

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
                .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        testAuthHeaders = createTestAuthHeader(member.getEmail());
    }

    /*
     * 숙소 단일 조회
     * 숙소를
     * 사용자 정보, 숙소 아이디를 받고
     *
     * 숙소 아이디를 이용 해서 숙소와 엮인 객실 정보를 비교
     * */
    @DisplayName("getAccommodations()는 숙소를 단일 조회할 수 있다.")
    @Test
    @WithMockUser
    public void getAccommodationWithRoomById_willSuccess() throws Exception {

        Accommodation accommodation = Accommodation.builder()
                .id(1L)
                .name("숙소01")
                .build();

        AccommodationDetailResponse accommodationDetailResponse = AccommodationDetailResponse.builder()
                .id(1L)
                .name("숙소01")
                .build();

        when(accommodationService.getAccommodationWithRoomById(anyLong(), any())).thenReturn(accommodationDetailResponse);

        mockMvc.perform(get("/api/v1/accommodations/{accommodation_id}", 1)
                        .headers(testAuthHeaders))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
    }

    @DisplayName("getAccommodations()는 숙소를 전체 조회할 수 있다.")
    @Test
    @WithMockUser
    public void getAccommodations_willSuccess() throws Exception {

        Accommodation accommodation1 = Accommodation.builder()
                .id(1L)
                .name("숙소01")
                .checkIn(LocalTime.now())
                .checkOut(LocalTime.now())
                .type(AccommodationType.CONDOMINIUM)
                .build();
        Accommodation accommodation2 = Accommodation.builder()
                .id(2L)
                .checkIn(LocalTime.now())
                .checkOut(LocalTime.now())
                .type(AccommodationType.CONDOMINIUM)
                .name("숙소02")
                .build();

        List<AccommodationSimpleResponse> responseList = List.of(
                AccommodationSimpleResponse.fromEntity(accommodation1),
                AccommodationSimpleResponse.fromEntity(accommodation2)
        );

        Page<AccommodationSimpleResponse> accommodations = new PageImpl<>(responseList);

        when(accommodationService.getAccommodations(any(Pageable.class), any(), any(), any(), any(), any(), any(), any())).thenReturn(accommodations);

        mockMvc.perform(get("/api/v1/accommodations")
                        .headers(testAuthHeaders))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("숙소01"))
                .andExpect(jsonPath("$.data[1].id").value(2L))
                .andExpect(jsonPath("$.data[1].name").value("숙소02"))
                .andDo(print());
    }

}


