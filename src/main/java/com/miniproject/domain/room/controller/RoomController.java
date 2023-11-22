package com.miniproject.domain.room.controller;

import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.service.BasketService;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.service.RoomService;
import com.miniproject.global.util.ResponseDTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/{room_id}/baskets")
    public ResponseEntity<ResponseDTO> registerRoom(@PathVariable Long roomId
            , @RequestBody RoomRegisterRequestDto dto) {
        roomService.creatRoomInBasket(roomId,dto);
//        member 구현 시 , 변경되는 메소드
//        roomService.creatRoomInBasket(roomId,dto,member);
        return ResponseEntity.ok().body(ResponseDTO.res("장바구니에 객실이 담겼습니다."));
    }
    @PostMapping("/{room_id}/orders")
    public ResponseEntity<ResponseDTO> orderRoom(@PathVariable Long roomId
            , @RequestBody RoomRegisterRequestDto dto){
        roomService.createSinglePurchase(roomId,dto);
//        member 구현 시 , 변경되는 메소드
//        roomService.createSinglePurchase(roomId,dto,member);
        return ResponseEntity.ok().body(ResponseDTO.res("객실 구매가 진행됩니다."));
    }
}
