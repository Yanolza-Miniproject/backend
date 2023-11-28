package com.miniproject.domain.payment.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class PaymentNotFoundException extends ApplicationException {
    private static final ErrorCode errorCode = ErrorCode.PAYMENT_NOT_FOUND;

    public PaymentNotFoundException(){ super(errorCode);}
}
