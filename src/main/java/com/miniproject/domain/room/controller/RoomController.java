package com.miniproject.domain.room.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.service.RoomService;
import com.miniproject.global.util.ResponseDTO;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {


    private final RoomService roomService;

    @PostMapping("/{room_id}/baskets")
    public ResponseEntity<ResponseDTO> registerRoom(@PathVariable Long room_id
        , @RequestBody RoomRegisterRequestDto dto, Member member) {
        roomService.creatRoomInBasket(room_id, dto, member);

        return ResponseEntity.ok().body(ResponseDTO.res("장바구니에 객실이 담겼습니다."));
    }

    @PostMapping("/{room_id}/orders")
    public ResponseEntity<ResponseDTO> orderRoom(@PathVariable Long room_id
        , @RequestBody RoomRegisterRequestDto dto, Member member) {

        Long orderId = roomService.createSingleOrders(room_id, dto, member);
        return ResponseEntity.created(URI.create("api/v1/orders/" + orderId))
            .body(ResponseDTO.res("객실 구매가 진행됩니다.", orderId));
    }
}
