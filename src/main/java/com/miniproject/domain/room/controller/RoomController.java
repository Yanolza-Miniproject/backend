package com.miniproject.domain.room.controller;

import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.room.dto.request.RoomRegisterRequestDto;
import com.miniproject.domain.room.dto.response.RoomDetailResponse;
import com.miniproject.domain.room.service.RoomService;
import com.miniproject.global.resolver.LoginInfo;
import com.miniproject.global.resolver.SecurityContext;
import com.miniproject.global.util.ResponseDTO;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RoomController {

    private final RoomService roomService;
    private final MemberService memberService;

    // 단일 객실
    @GetMapping("rooms/{roomId}")
    public ResponseEntity<ResponseDTO<RoomDetailResponse>> getRoom(@PathVariable Long roomId) {

        return ResponseEntity.ok(
                ResponseDTO.res("성공", roomService.getRoomById(roomId))
        );
    }

    // 숙소의 전체 객실 조회 + 필터링
    @GetMapping("accommodations/{accommodationId}/rooms")
    public ResponseEntity<ResponseDTO<List<RoomDetailResponse>>> getRooms(
            @PathVariable Long accommodationId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, name = "category-tv") Integer categoryTv,
            @RequestParam(required = false, name = "category-pc") Integer categoryPc,
            @RequestParam(required = false, name = "category-internet") Integer categoryInternet,
            @RequestParam(required = false, name = "category-refrigerator") Integer categoryRefrigerator,
            @RequestParam(required = false, name = "category-bathing-facilities") Integer categoryBathingFacilities,
            @RequestParam(required = false, name = "category-dryer") Integer categoryDryer
    ) {
        Pageable pageable = PageRequest.of(page, 20);

        Page<RoomDetailResponse> roomDetailResponsePage =
                roomService.getRoomsByAccommodationId(
                        accommodationId,
                        pageable,
                        categoryTv,
                        categoryPc,
                        categoryInternet,
                        categoryRefrigerator,
                        categoryBathingFacilities,
                        categoryDryer);

        List<RoomDetailResponse> roomDetailResponseList = roomDetailResponsePage.getContent();

        return ResponseEntity.ok(
                ResponseDTO.res("성공", roomDetailResponseList)
        );

    }

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
}
