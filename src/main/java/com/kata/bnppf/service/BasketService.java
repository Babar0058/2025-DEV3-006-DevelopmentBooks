package com.kata.bnppf.service;

import com.kata.bnppf.calcUtils.DiscountCalculator;
import com.kata.bnppf.model.entity.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BasketService {
    private final DiscountCalculator discountCalculator;

    @Autowired
    public BasketService(DiscountCalculator discountCalculator) {
        this.discountCalculator = discountCalculator;
    }

    public BigDecimal getTotal(Basket basketInput) {
        return discountCalculator.getTotal(basketInput);
    }
}
