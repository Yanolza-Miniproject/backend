package com.miniproject.domain.basket.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CheckBasketRequestDto {
    List<Long> ids = new ArrayList<>();
}
