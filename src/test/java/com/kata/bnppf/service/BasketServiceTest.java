package com.kata.bnppf.service;

import com.kata.bnppf.calcUtils.DiscountCalculator;
import com.kata.bnppf.model.entity.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasketServiceTest {
    private Basket basket;

    private BasketService basketService;

    @BeforeEach
    public void setUp() {
        DiscountCalculator discountCalculator = new DiscountCalculator();
        basketService = new BasketService(discountCalculator);
        basket = new Basket(new ArrayList<>());
    }

    @Test
    public void noBook() {
        assertTrue(BigDecimal.valueOf(0).compareTo(basketService.getTotal(basket)) == 0);
    }

}
