package com.miniproject.domain.wish.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class AlreadyWishException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.ALREADY_WISH;

    public AlreadyWishException() {
        super(errorCode);
    }
}
