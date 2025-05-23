package com.kata.bnppf.calculator;

import com.kata.bnppf.model.entity.Basket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        return findMinPriceRecursively(count, new HashMap<>());
    }

    /**
     * It’s like a decision tree — for every level, it tries all group sizes (1 to 5), and at each level:
     * 1. Picks a group of distinct books of that size
     * 2. Removes those books from the count
     * 3. Recursively computes the best price from the remaining books
     * 4. Saves the best total in a cache (computedPriceCache)
     *
     * @param count              Map that have the count of each book ({ID1=xInt, ID2=yInt})
     * @param computedPriceCache Map that will keep track of each computation done
     * @return the best discounted price
     */
    private BigDecimal findMinPriceRecursively(Map<Integer, Integer> count, Map<String, BigDecimal> computedPriceCache) {
        // Base case: all books are 0 - Also prevent endless recursion
        if (count.values().stream().allMatch(v -> v == 0)) {
            return BigDecimal.ZERO;
        }

        // Create unique string key (|0:0|1:0|2:0|3:0|4:1), if already computed, skip
        String key = bookCountKey(count);
        if (computedPriceCache.containsKey(key)) return computedPriceCache.get(key);

        BigDecimal min = null;
        int maxGroupSize = DISCOUNTS.keySet().stream().max(Integer::compareTo).orElse(0);

        // Forms a group of size 1 to 5
        // Calculates total cost for that path (current group + recursive result)
        // Compares it to the min value (the best one seen so far)
        for (int size = 1; size <= maxGroupSize; size++) {
            List<Integer> group = pickGroup(count, size);
            if (group.size() < size) continue;

            Map<Integer, Integer> next = new HashMap<>(count);
            for (int book : group) {
                next.put(book, next.get(book) - 1);
            }

            BigDecimal groupPrice = SINGLE_BOOK_PRICE
                    .multiply(BigDecimal.valueOf(size))
                    .multiply(BigDecimal.valueOf(DISCOUNTS.get(size)));

            BigDecimal total = groupPrice.add(findMinPriceRecursively(next, computedPriceCache));
            if (min == null || total.compareTo(min) < 0) {
                min = total;
            }
        }

        computedPriceCache.put(key, min);
        return min;
    }

    /**
     * It builds a list of different book IDs (no duplicates, list of size pass in the param),
     * by looking at the books in the current count map and picking only one of each, until it has enough.
     *
     * @param count What's left in the current book ID ({ID1=xInt, ID2=yInt})
     * @param size  wanted size of the group
     * @return a list of book id picked
     */
    private List<Integer> pickGroup(Map<Integer, Integer> count, int size) {
        List<Integer> group = new ArrayList<>();
        for (int book : count.keySet()) {
            if (count.get(book) > 0) {
                group.add(book);
                if (group.size() == size) break;
            }
        }
        return group;
    }

    // Create unique string key for computedPriceCache
    private String bookCountKey(Map<Integer, Integer> count) {
        return count.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + ":" + e.getValue())
                .reduce("", (a, b) -> a + "|" + b);
    }
}
