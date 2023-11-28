package com.miniproject.domain.member.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class MemberNotFoundException extends ApplicationException {
    private static final ErrorCode errorCode = ErrorCode.MEMBER_NOT_FOUND;

    public MemberNotFoundException(){
        super(errorCode);
    }
}
