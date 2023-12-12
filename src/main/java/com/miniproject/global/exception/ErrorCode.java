package com.miniproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //사용자
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    MEMBER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "잘못된 접근입니다."),

    //리프레시토큰
    REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 RefreshToken 입니다. "),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "리프레시 토큰이 존재하지 않습니다."),
    //숙소
    ACCOOMMODATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 숙소입니다."),

    //객실
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 객실입니다."),
    //장바구니 상품
    ROOM_IN_BASKET_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니에 담겨있지 않은 객실입니다."),
    //장바구니
    BASKET_EMPTY(HttpStatus.NO_CONTENT, "장바구니가 비었습니다."),

    //주문
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),

    //결제
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 결제입니다."),
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
