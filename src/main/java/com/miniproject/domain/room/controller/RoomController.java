package com.miniproject.domain.room.controller;


import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.dto.request.RoomRequest;
import com.miniproject.domain.room.service.RoomService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.dto.response.RoomSimpleResponse;
import com.miniproject.domain.room.service.RoomService;
import com.miniproject.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RoomController {

    private final RoomService roomService;
    private final MemberService memberService;

    @PostMapping("/rooms/{room_id}/baskets")
    public ResponseEntity<ResponseDTO> registerRoom(@PathVariable Long room_id
        , @RequestBody RoomRegisterRequestDto dto, @SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        roomService.creatRoomInBasket(room_id, dto, member);
        return ResponseEntity.ok().body(ResponseDTO.res("장바구니에 객실이 담겼습니다."));
    }

    @PostMapping("/rooms/{room_id}/orders")
    public ResponseEntity<ResponseDTO> orderRoom(@PathVariable Long room_id
        , @RequestBody RoomRegisterRequestDto dto,@SecurityContext LoginInfo loginInfo) {
        Member member = memberService.getMemberByLoginInfo(loginInfo);
        Long orderId = roomService.createSingleOrders(room_id, dto, member);
        return ResponseEntity.created(URI.create("api/v1/orders/" + orderId))
            .body(ResponseDTO.res("객실 구매가 진행됩니다.", orderId));
    }

    // 단일 객실
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ResponseDTO<RoomDetailResponse>> getRoom(@PathVariable Long roomId) {

        ResponseEntity<ResponseDTO<RoomDetailResponse>> response = ResponseEntity.ok(
                ResponseDTO.res("성공", roomService.getRoomById(roomId))
        );

        return response;
    }

    // 숙소의 전체 객실 조회 + 필터링
    @GetMapping("accommodations/{accommodationId}/rooms")
    public ResponseEntity<ResponseDTO<List<RoomSimpleResponse>>> getRooms(
            Pageable pageable,
            @PathVariable Long accommodationId,
            @ModelAttribute RoomRequest request
            ) {


        List<RoomSimpleResponse> roomSimpleResponseList =
                roomService.getRoomsByAccommodationId(
                        accommodationId,
                        pageable,
                        request);

        ResponseEntity<ResponseDTO<List<RoomSimpleResponse>>> response = ResponseEntity.ok(
                ResponseDTO.res("성공", roomSimpleResponseList)
        );

        return response;

    }

}
