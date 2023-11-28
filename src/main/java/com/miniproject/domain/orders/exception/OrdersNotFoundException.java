package com.miniproject.domain.orders.exception;

import com.miniproject.global.exception.ApplicationException;
import com.miniproject.global.exception.ErrorCode;

public class OrdersNotFoundException extends ApplicationException {

    private static final ErrorCode errorcode = ErrorCode.ORDER_NOT_FOUND;

    public OrdersNotFoundException(){super(errorcode);}
}
