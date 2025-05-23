package com.kata.bnppf.calcUtils;

import com.kata.bnppf.model.entity.Basket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DiscountCalculator {
    public BigDecimal getTotal(Basket basket) {
        return BigDecimal.valueOf(basket.getBooks().size() * 50L);
    }
}
