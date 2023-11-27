package com.miniproject.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.room.controller.RoomController;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.service.RoomService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("createSingleOrders()은 바로 객실 구매를 진행 할 수 있다.")
    @Test
    @WithMockUser
    public void createSingleOrders_willSuccess() throws Exception {
        //given
        RoomRegisterRequestDto dto = RoomRegisterRequestDto.builder()
            .checkInAt(LocalDateTime.now())
            .checkOutAt(LocalDateTime.now().withDayOfMonth(30))
            .numberOfGuests(2).build();

        String content = objectMapper.writeValueAsString(dto);
        when(roomService.createSingleOrders(anyLong(), any(RoomRegisterRequestDto.class),
            any())).thenReturn(1L);
        //when,then
        mockMvc.perform(post("/api/v1/rooms/{room_id}/orders", 1L)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("객실 구매가 진행됩니다."))
            .andExpect(jsonPath("$.data").value(1L));

    }

}
