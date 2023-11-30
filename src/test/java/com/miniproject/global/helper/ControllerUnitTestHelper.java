package com.miniproject.global.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.accommodation.controller.AccommodationController;
import com.miniproject.domain.basket.controller.BasketController;
import com.miniproject.domain.member.controller.MemberController;
import com.miniproject.domain.openapi.controller.OpenApiController;
import com.miniproject.domain.orders.controller.OrdersController;
import com.miniproject.domain.payment.controller.PaymentController;
import com.miniproject.domain.room.controller.RoomController;
import com.miniproject.domain.wish.controller.WishController;
import com.miniproject.global.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {AccommodationController.class, BasketController.class, MemberController.class, OpenApiController.class, OrdersController.class
, PaymentController.class, RoomController.class, WishController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class ControllerUnitTestHelper {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
