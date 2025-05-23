package com.kata.bnppf.calcUtils;

import com.kata.bnppf.model.entity.Basket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class DiscountCalculator {
    // 10 entries max
    private static final Map<Integer, Double> DISCOUNTS = Map.of(
            1, 1.0,
            2, 0.95,
            3, 0.90,
            4, 0.80,
            5, 0.75
    );

    public BigDecimal getTotal(Basket basket) {
        Set<Integer> unique = new HashSet<>(basket.getBooks());
        int size = unique.size();
        return BigDecimal.valueOf(size * 50d * DISCOUNTS.getOrDefault(size, 1.0));
    }
}
