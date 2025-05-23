package com.kata.bnppf.service;

import com.kata.bnppf.calcUtils.DiscountCalculator;
import com.kata.bnppf.model.entity.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketServiceTest {

    private static final BigDecimal SINGLE_BOOK_PRICE = BigDecimal.valueOf(50);

    private Basket basket;

    private BasketService basketService;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        DiscountCalculator discountCalculator = new DiscountCalculator();
        basketService = new BasketService(discountCalculator);
        basket = new Basket(new ArrayList<>());
        System.out.println("====== Running: " + testInfo.getDisplayName() + " ======");
    }

    private void assertTrueBetweenSupposedTotalAndBasketGetTotal(BigDecimal totalShouldBe) {
        BigDecimal resultBasketServiceGetTotal = basketService.getTotal(basket);
        System.out.println("totalShouldBe: " + totalShouldBe);
        System.out.println("basketService.getTotal(): " + resultBasketServiceGetTotal);
        assertEquals(0, totalShouldBe.compareTo(resultBasketServiceGetTotal));
    }

    @Test
    public void noBook() {
        BigDecimal totalShouldBe = new BigDecimal(0);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void oneBookCost50e() {
        BigDecimal totalShouldBe = new BigDecimal(50);
        basket.getBooks().add(0);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void twoDifferentBookFivePercentDiscount() {
        BigDecimal totalShouldBe = new BigDecimal(95);
        Collections.addAll(basket.getBooks(), 0, 1);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void threeDifferentBookTenPercentDiscount() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(3))
                .multiply(BigDecimal.valueOf(0.90));
        Collections.addAll(basket.getBooks(), 0, 1, 2);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fourDifferentBookTwentyPercentDiscount() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(4))
                .multiply(BigDecimal.valueOf(0.80));
        Collections.addAll(basket.getBooks(), 0, 1, 2, 3);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fiveDifferentBookTwentyFivePercentDiscount() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.75));
        Collections.addAll(basket.getBooks(), 0, 1, 2, 3, 4);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void twoSameBookNoDiscount() {
        BigDecimal totalShouldBe = new BigDecimal(100);
        Collections.addAll(basket.getBooks(), 0, 0);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fiveDifferentBookTwentyFivePercentDiscountPlusOneBook() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.75))
                .add(SINGLE_BOOK_PRICE);
        Collections.addAll(basket.getBooks(), 0, 0, 1, 2, 3, 4);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fiveDifferentBookTwentyFivePercentDiscountPlusTwoSameBook() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.75))
                .add(SINGLE_BOOK_PRICE
                        .multiply(BigDecimal.valueOf(2)));
        Collections.addAll(basket.getBooks(), 0, 0, 0, 1, 2, 3, 4);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fiveDifferentBookTwentyFivePercentDiscountPlusTwoDifferentBookFivePercentDiscount() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.75))
                .add(SINGLE_BOOK_PRICE
                        .multiply(BigDecimal.valueOf(2))
                        .multiply(BigDecimal.valueOf(0.95)));
        Collections.addAll(basket.getBooks(), 0, 0, 1, 1, 2, 3, 4);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }
}
