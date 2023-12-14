package com.miniproject.domain.room.controller;


import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.dto.request.RoomRequest;
import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.dto.response.RoomSimpleResponse;
import com.miniproject.domain.room.service.RoomService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

        return ResponseEntity.ok(
                ResponseDTO.res("성공", roomService.getRoomById(roomId))
        );
    }

    // 숙소의 전체 객실 조회 + 필터링
    @GetMapping("accommodations/{accommodationId}/rooms")
    public ResponseEntity<ResponseDTO<List<RoomSimpleResponse>>> getRooms(
            @PathVariable Long accommodationId,
            Pageable pageable,
            @ModelAttribute RoomRequest request
//            @RequestParam(defaultValue = "0", name = "page") int page,
//            @RequestParam(required = false, name = "category-tv") Integer categoryTv,
//            @RequestParam(required = false, name = "category-pc") Integer categoryPc,
//            @RequestParam(required = false, name = "category-internet") Integer categoryInternet,
//            @RequestParam(required = false, name = "category-refrigerator") Integer categoryRefrigerator,
//            @RequestParam(required = false, name = "category-bathing-facilities") Integer categoryBathingFacilities,
//            @RequestParam(required = false, name = "category-dryer") Integer categoryDryer,
//            @RequestParam(name = "checkin-day")LocalDate checkinDay,
//            @RequestParam(name = "checkout-day")LocalDate checkoutDay
            ) {

        List<RoomSimpleResponse> roomSimpleResponseList =
                roomService.getRoomsByAccommodationId(
                        accommodationId,
                        pageable,
                        request);

        return ResponseEntity.ok(
                ResponseDTO.res("성공", roomSimpleResponseList)
        );

    }

}
