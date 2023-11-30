package com.miniproject.domain.refresh.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class RefreshTokenException extends ApplicationException {
    private static ErrorCode errorCode = ErrorCode.REFRESH_TOKEN_EXCEPTION;
    public RefreshTokenException() {
        super(errorCode);
    }
}
