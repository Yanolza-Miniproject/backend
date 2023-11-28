package com.miniproject.domain.member.exception;

import com.miniproject.domain.payment.repository.PaymentRepository;
import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class MemberUnAuthorizedException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.MEMBER_UNAUTHORIZED;
    public MemberUnAuthorizedException(){super(errorCode);}

}
