package com.miniproject.payment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.miniproject.domain.payment.controller.PaymentController;
import com.miniproject.domain.payment.dto.response.PaymentResponseDto;
import com.miniproject.domain.payment.service.PaymentService;
import com.miniproject.domain.room.dto.response.RoomInOrdersGetResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @DisplayName("getPayment()는 결제를 불러올 수 있다.")
    @Test
    @WithMockUser
    public void getPayment_willSuccess() throws Exception {
        //given
        List<RoomInOrdersGetResponseDto> dtoList = new ArrayList<>();
        RoomInOrdersGetResponseDto dto = RoomInOrdersGetResponseDto.builder()
            .id(1L)
            .accommodationName("제주호텔")
            .roomName("스탠다드")
            .price(50000)
            .numberOfGuests(2)
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
            .roomUrl("www.feafafsadf.jpg")
            .build();
        dtoList.add(dto);
        PaymentResponseDto paymentResponseDto = PaymentResponseDto.builder()
            .id(1L)
            .totalPrice(50000)
            .totalCount(1)
            .paymentAt(LocalDateTime.now())
            .rooms(dtoList).build();
        when(paymentService.getPayment(anyLong(), any())).thenReturn(paymentResponseDto);

        //when, then
        mockMvc.perform(get("/api/v1/payment/{payment_id}", 1L)
                .with(csrf()))
            .andExpect(jsonPath("$.message").value("결제 불러오기 성공"))
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.totalPrice").isNumber())
            .andExpect(jsonPath("$.data.totalCount").isNumber())
            .andExpect(jsonPath("$.data.rooms").exists())
            .andDo(print());

    }

    @DisplayName("getPayments()는 전체 결제 내역을 불러올 수 있다.")
    @Test
    @WithMockUser
    public void getPayments_willSuccess() throws Exception {
        //given
        List<RoomInOrdersGetResponseDto> dtoList = new ArrayList<>();
        RoomInOrdersGetResponseDto dto = RoomInOrdersGetResponseDto.builder()
            .id(1L)
            .accommodationName("제주호텔")
            .roomName("스탠다드")
            .price(50000)
            .numberOfGuests(2)
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
            .roomUrl("www.feafafsadf.jpg")
            .build();
        dtoList.add(dto);
        List<PaymentResponseDto> response = new ArrayList<>();
        PaymentResponseDto paymentResponseDto1 = PaymentResponseDto.builder()
            .id(1L)
            .totalPrice(50000)
            .totalCount(1)
            .paymentAt(LocalDateTime.now())
            .rooms(dtoList).build();
        PaymentResponseDto paymentResponseDto2 = PaymentResponseDto.builder()
            .id(2L)
            .totalPrice(40000)
            .totalCount(1)
            .paymentAt(LocalDateTime.now())
            .rooms(dtoList).build();
        response.add(paymentResponseDto1);
        response.add(paymentResponseDto2);
        when(paymentService.getPayments(anyInt(), anyInt(), any())).thenReturn(response);

        //when, then
        mockMvc.perform(get("/api/v1/payment")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].id").isNumber())
            .andExpect(jsonPath("$.data[0].totalPrice").isNumber())
            .andExpect(jsonPath("$.data[0].totalCount").isNumber())
            .andExpect(jsonPath("$.data[0].rooms").exists())
            .andExpect(jsonPath("$.data[1].id").isNumber())
            .andExpect(jsonPath("$.data[1].totalPrice").isNumber())
            .andExpect(jsonPath("$.data[1].totalCount").isNumber())
            .andExpect(jsonPath("$.data[1].rooms").exists())
            .andDo(print());
    }


}
