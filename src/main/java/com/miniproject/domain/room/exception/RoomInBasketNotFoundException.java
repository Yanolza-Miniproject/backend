package com.miniproject.domain.room.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class RoomInBasketNotFoundException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.ROOM_IN_BASKET_NOT_FOUND;

    public RoomInBasketNotFoundException(){super(errorCode);}
}
