package com.kata.bnppf.calcUtils;

import com.kata.bnppf.model.entity.Basket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
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
        Map<Integer, Integer> count = new HashMap<>();
        // Count each instance of book {ID1=xInt, ID2=yInt}
        for (int book : basket.getBooks()) {
            count.put(book, count.getOrDefault(book, 0) + 1);
        }

        BigDecimal total = BigDecimal.ZERO;

        while (!count.isEmpty()) {
            Set<Integer> group = new HashSet<>();

            // We group different book id into one SET to apply the maximum discount
            for (int book : new HashSet<>(count.keySet())) {
                group.add(book);
                count.put(book, count.get(book) - 1);
                if (count.get(book) == 0) count.remove(book);
            }

            int groupSize = group.size();
            double discountFactor = DISCOUNTS.get(groupSize);
            BigDecimal groupTotal = SINGLE_BOOK_PRICE
                    .multiply(BigDecimal.valueOf(groupSize))
                    .multiply(BigDecimal.valueOf(discountFactor));

            total = total.add(groupTotal);
        }

        return total;
    }
}
