package com.miniproject.domain.basket.entity;

public enum BasketStatus {
    ACTIVATE("장바구니 활성화"), DEACTIVATE("장바구니 비활성화(주문 조회상태)"), SINGLE_PURCHASE("단일 상품 구매")
    ;

    BasketStatus(String s) {

    }
}
