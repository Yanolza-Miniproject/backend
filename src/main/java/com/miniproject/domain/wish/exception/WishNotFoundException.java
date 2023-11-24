package com.miniproject.domain.wish.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class WishNotFoundException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.WISH_NOT_FOUND;

    public WishNotFoundException() {
        super(errorCode);
    }
}
