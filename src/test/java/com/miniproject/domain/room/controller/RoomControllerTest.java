package com.miniproject.domain.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.room.controller.RoomController;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.dto.response.RoomSimpleResponse;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.repository.RoomRepository;
import com.miniproject.domain.room.service.RoomService;
import com.miniproject.global.config.CustomHttpHeaders;
import com.miniproject.global.jwt.JwtPayload;
import com.miniproject.global.jwt.service.JwtService;
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

//@WebMvcTest(RoomController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @MockBean
    private RoomService roomService;

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
            .id(1L).nickname("하이").email("kj@gmail.com").password("ffdfda231321@da").build();
        testAuthHeaders = createTestAuthHeader(member.getEmail());
    }



    @DisplayName("createSingleOrders()은 바로 객실 구매를 진행 할 수 있다.")
    @Test
    @WithMockUser
    public void createSingleOrders_willSuccess() throws Exception {
        //given
        RoomRegisterRequestDto dto = RoomRegisterRequestDto.builder()
            .checkInAt(LocalDate.now())
            .checkOutAt(LocalDate.now().withDayOfMonth(30))
            .numberOfGuests(2).build();

        String content = objectMapper.writeValueAsString(dto);
        when(roomService.createSingleOrders(anyLong(), any(RoomRegisterRequestDto.class),
            any())).thenReturn(1L);
        //when,then
        mockMvc.perform(post("/api/v1/rooms/{room_id}/orders", 1L)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .headers(testAuthHeaders))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("객실 구매가 진행됩니다."))
            .andExpect(jsonPath("$.data").value(1L));

    }


    @DisplayName("단일 객실 조회")
    @Test
    @WithMockUser
    public void getSingleRoom_willSucess() throws Exception{

        //given
        List<Room> rooms = new ArrayList<>();

        Room room01 = Room.builder()
                .id(1L)
                .name("객실01")
                .price(10000)
                .build();

        roomRepository.save(room01);

        RoomDetailResponse roomDetailResponse = RoomDetailResponse.builder()
                .id(1L)
                .name("객실01")
                .price(10000)
                .build();

        // when
        when(roomService.getRoomById(anyLong())).thenReturn(roomDetailResponse);

        mockMvc.perform(get("/api/v1/rooms/{roomId}", 1)
                        .headers(testAuthHeaders))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data").isNotEmpty());



    }

    @DisplayName("객실 리스트 조회")
    @Test
    @WithMockUser
    public void getRoomList_willSuccess() throws Exception{

        //given
        List<Room> rooms = new ArrayList<>();

        Room room01 = Room.builder()
                .id(1L)
                .name("객실01")
                .price(10000)
                .build();

        Room room02 = Room.builder()
                .id(2L)
                .name("객실02")
                .price(20000)
                .build();

        roomRepository.save(room01);
        roomRepository.save(room02);

        RoomSimpleResponse roomSimpleResponse1 = RoomSimpleResponse.builder()
                .id(1L)
                .name("객실01")
                .price(10000)
                .build();

        RoomSimpleResponse roomSimpleResponse2 = RoomSimpleResponse.builder()
                .id(2L)
                .name("객실02")
                .price(20000)
                .build();

        List<RoomSimpleResponse> responseList = List.of(roomSimpleResponse1, roomSimpleResponse2);

        // when
        when(roomService.getRoomsByAccommodationId(anyLong(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(responseList);

        mockMvc.perform(get("/api/v1/accommodations/{accommodationId}/rooms?page=0&checkin-day=2023-11-30&checkout-day=2023-12-01", 1)
                        .headers(testAuthHeaders))
                .andDo(print())
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.data[0].id").isNumber())
                .andExpect(jsonPath("$.data").isNotEmpty());

    }


}
