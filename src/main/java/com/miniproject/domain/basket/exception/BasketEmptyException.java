package com.miniproject.domain.basket.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class BasketEmptyException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.BASKET_EMPTY;

    public BasketEmptyException(){super(errorCode);}

}
