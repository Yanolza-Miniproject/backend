package com.miniproject.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.miniproject.domain.orders.controller.OrdersController;
import com.miniproject.domain.orders.dto.response.OrdersResponseDto;
import com.miniproject.domain.orders.service.OrdersService;
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

@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrdersService ordersService;
    @MockBean
    private PaymentService paymentService;

    @DisplayName("getOrder()는 주문 상세 조회를 할 수 있다.")
    @Test
    @WithMockUser
    public void getOrder_willSuccess() throws Exception {
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
        OrdersResponseDto ordersResponseDto =
            OrdersResponseDto.builder().id(1L).totalPrice(50000).totalCount(2)
            .rooms(dtoList).build();
        when(ordersService.getOrder(anyLong(), any())).thenReturn(ordersResponseDto);
        //when, then
        mockMvc.perform(get("/api/v1/orders/{orders_id}", 1L)
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("주문 상세 조회 완료"))
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.totalPrice").isNumber())
            .andExpect(jsonPath("$.data.totalCount").isNumber())
            .andExpect(jsonPath("$.data.rooms").exists())
            .andDo(print());
    }
    @DisplayName("paymentOrder()는 결제를 할 수 있다.")
    @Test
    @WithMockUser
    public void paymentOrder_willSuccess() throws Exception {
        //given
        when(ordersService.registerPayment(anyLong(),any())).thenReturn(1L);
        //when, then
        mockMvc.perform(post("/api/v1/orders/{orders_id}/payment",1L)
                .with(csrf()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("결제 완료"))
            .andExpect(jsonPath("$.data").value(1L))
            .andDo(print());
    }

}
