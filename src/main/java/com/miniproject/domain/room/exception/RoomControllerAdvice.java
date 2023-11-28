package com.miniproject.domain.room.exception;

import com.miniproject.global.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class RoomControllerAdvice {

    public ResponseEntity<ResponseDTO<Object>> roomNotFoundException(
            RoomNotFoundException e
    ) {

        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDTO.res(e.getErrorCode().getMessage()));

    }


}
