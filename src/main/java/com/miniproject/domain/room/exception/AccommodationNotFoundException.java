package com.miniproject.domain.accommodation.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class AccommodationNotFoundException extends ApplicationException {
    private static final ErrorCode errorCode = ErrorCode.ROOM_NOT_FOUND;

    public AccommodationNotFoundException(){
        super(errorCode);
    }
}
