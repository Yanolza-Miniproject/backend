package com.miniproject.domain.accommodation.exception;

import com.miniproject.global.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class AccommodationControllerAdvice {

    public ResponseEntity<ResponseDTO<Object>> accommodationNotFoundException(
            AccommodationNotFoundException e
    ) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDTO.res(e.getErrorCode().getMessage()));
    }


}
