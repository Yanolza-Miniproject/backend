package com.miniproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //사용자
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    //숙소
    ACCOOMMODATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 숙소입니다."),

    //객실
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 객실입니다."),
    //장바구니 상품

    //장바구니

    //주문

    //결제

    //좋아요
    ALREADY_WISH(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 상태입니다."),

    WISH_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 좋아요는 존재하지 않습니다.");


    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
