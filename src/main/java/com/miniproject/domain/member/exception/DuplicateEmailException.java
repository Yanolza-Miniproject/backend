package com.miniproject.domain.member.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class DuplicateEmailException extends ApplicationException {
    private static final ErrorCode errorCode = ErrorCode.DUPLICATE_EMAIL;

    public DuplicateEmailException(){
        super(errorCode);
    }
}
