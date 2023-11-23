package com.miniproject.domain.room.exception;

import com.miniproject.domain.payment.exception.PaymentNotFoundException;
import com.miniproject.global.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RoomControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> roomInBasketNotFoundException(
        RoomInBasketNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(ResponseDTO.res(e.getErrorCode().getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> roomNotFoundException(
        RoomNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(ResponseDTO.res(e.getErrorCode().getMessage()));
    }
}
