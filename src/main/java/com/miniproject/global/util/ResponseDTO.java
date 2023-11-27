package com.miniproject.global.util;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDTO<T> {

    private final String message;
    private final T data;

    @Builder
    private ResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static ResponseDTO<Object> res(final String message) {
        return ResponseDTO.<Object>builder()
            .message(message)
            .build();
    }

    public static <T> ResponseDTO<T> res(final String message, final T data) {
        return ResponseDTO.<T>builder()
            .message(message)
            .data(data)
            .build();
    }
}
