package com.miniproject.domain.basket.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class BasketDuplicateActivateException extends ApplicationException {
    private static final ErrorCode errorCode = ErrorCode.BASKET_DUPLICATE_ACTIVATE;

    public BasketDuplicateActivateException(){super(errorCode);}
}
