package com.miniproject.domain.basket.exception;

import com.miniproject.global.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BasketControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> basketEmptyException(
        BasketEmptyException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(ResponseDTO.res(e.getErrorCode().getMessage()));
    }
}
