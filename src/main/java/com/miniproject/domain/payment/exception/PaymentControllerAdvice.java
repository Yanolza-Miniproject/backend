package com.miniproject.domain.payment.exception;

import com.miniproject.domain.orders.exception.OrdersNotFoundException;
import com.miniproject.global.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class PaymentControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> paymentNotFoundException(
        PaymentNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(ResponseDTO.res(e.getErrorCode().getMessage()));
    }

}
