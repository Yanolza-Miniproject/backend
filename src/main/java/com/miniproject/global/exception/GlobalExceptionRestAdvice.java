package com.miniproject.global.exception;

import com.miniproject.domain.member.exception.MemberUnAuthorizedException;
import com.miniproject.global.jwt.exception.BadTokenException;
import com.miniproject.global.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionRestAdvice {
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> applicationException(ApplicationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
            .status(e.getErrorCode().getHttpStatus())
            .body(ResponseDTO.res(e.getErrorCode().getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> applicationException(BadTokenException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDTO.res("적절치 않은 RefreshToken 입니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> bindException(BindException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ResponseDTO.res(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> dbException(DataAccessException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseDTO.res("서버 에러!"));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> serverException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseDTO.res("서버 에러!"));
    }
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Object>> memberUnAuthorizedException(
        MemberUnAuthorizedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ResponseDTO.res(e.getMessage()));
    }

}
