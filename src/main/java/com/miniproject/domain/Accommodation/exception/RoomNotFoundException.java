package com.miniproject.domain.Accommodation.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class RoomNotFoundException extends ApplicationException {
    private static final ErrorCode errorCode = ErrorCode.ROOM_NOT_FOUND;

    public RoomNotFoundException(){ super(errorCode);}
}
