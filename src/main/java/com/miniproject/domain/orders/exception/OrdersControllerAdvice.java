package com.miniproject.domain.orders.exception;

import com.miniproject.domain.basket.exception.BasketDuplicateActivateException;
import com.miniproject.global.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrdersControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> ordersNotFoundException(
        OrdersNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(ResponseDTO.res(e.getErrorCode().getMessage()));
    }
}
