package com.miniproject.domain.refresh.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class RefreshTokenNotFoundException extends ApplicationException {
    private static ErrorCode errorCode = ErrorCode.NOT_FOUND_REFRESH_TOKEN;
    public RefreshTokenNotFoundException() {
        super(errorCode);
    }
}
