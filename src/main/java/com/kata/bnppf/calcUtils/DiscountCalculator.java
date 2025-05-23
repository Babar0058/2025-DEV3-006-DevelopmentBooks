package com.kata.bnppf.calcUtils;

import com.kata.bnppf.model.entity.Basket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class DiscountCalculator {

    private static final BigDecimal SINGLE_BOOK_PRICE = BigDecimal.valueOf(50);
    // 10 entries max
    private static final Map<Integer, Double> DISCOUNTS = Map.of(
            1, 1.0,
            2, 0.95,
            3, 0.90,
            4, 0.80,
            5, 0.75
    );

    public BigDecimal getTotal(Basket basket) {
        int totalBooks = basket.getBooks().size();
        Set<Integer> uniqueBooks = new HashSet<>(basket.getBooks());
        int distinctBooks = uniqueBooks.size();

        // Apply discount only if we have more than 1 distinct book
        double discountFactor = DISCOUNTS.getOrDefault(distinctBooks, 1.0);

        return SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(totalBooks))
                .multiply(BigDecimal.valueOf(discountFactor));
    }
}
