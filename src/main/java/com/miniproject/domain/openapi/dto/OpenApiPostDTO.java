package com.miniproject.domain.openapi.dto;

public record OpenApiPostDTO(
        Integer numOfRows,
        Integer numOfStartPage,
        Integer numOfEndPage
) {
}
