package com.miniproject.domain.basket.service;

import com.miniproject.domain.basket.dto.request.CheckBasketRequestDto;
import com.miniproject.domain.basket.dto.response.BasketResponseDto;
import com.miniproject.domain.basket.entity.Basket;
import com.miniproject.domain.basket.entity.BasketStatus;
import com.miniproject.domain.basket.exception.BasketDuplicateActivateException;
import com.miniproject.domain.basket.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    public BasketResponseDto getBasket(){
        return new BasketResponseDto(getActivateBasket());
    }
    public Basket getActivateBasket(){
        List<Basket> nowBasket = basketRepository.findByBasketStatus(BasketStatus.ACTIVATE);
        Basket basket = switch (nowBasket.size()) {
            case 1 -> nowBasket.get(0);
            case 0 -> new Basket();
            default -> throw new BasketDuplicateActivateException();
        };
        return basket;
    }
    public Basket getSinglePurchase(){
        Basket basket = new Basket();
        basket.ChangeStatus(BasketStatus.SINGLE_PURCHASE);
        return basket;
    }

    public void registerOrder(Basket basket, CheckBasketRequestDto dto) {

    }

}
