package com.kata.bnppf.service;

import com.kata.bnppf.calculator.DiscountCalculator;
import com.kata.bnppf.model.entity.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        basket.setBooks(new ArrayList<>(List.of(0)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void twoDifferentBookFivePercentDiscount() {
        BigDecimal totalShouldBe = new BigDecimal(95);
        basket.setBooks(new ArrayList<>(List.of(0, 1)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void threeDifferentBookTenPercentDiscount() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(3))
                .multiply(BigDecimal.valueOf(0.90));
        basket.setBooks(new ArrayList<>(List.of(0, 1, 2)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fourDifferentBookTwentyPercentDiscount() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(4))
                .multiply(BigDecimal.valueOf(0.80));
        basket.setBooks(new ArrayList<>(List.of(0, 1, 2, 3)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fiveDifferentBookTwentyFivePercentDiscount() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.75));
        basket.setBooks(new ArrayList<>(List.of(0, 1, 2, 3, 4)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void twoSameBookNoDiscount() {
        BigDecimal totalShouldBe = new BigDecimal(100);
        basket.setBooks(new ArrayList<>(List.of(0, 0)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fiveDifferentBookTwentyFivePercentDiscountPlusOneBook() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.75))
                .add(SINGLE_BOOK_PRICE);
        basket.setBooks(new ArrayList<>(List.of(0, 0, 1, 2, 3, 4)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void fiveDifferentBookTwentyFivePercentDiscountPlusTwoSameBook() {
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(0.75))
                .add(SINGLE_BOOK_PRICE
                        .multiply(BigDecimal.valueOf(2)));
        basket.setBooks(new ArrayList<>(List.of(0, 0, 0, 1, 2, 3, 4)));
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
        basket.setBooks(new ArrayList<>(List.of(0, 0, 1, 1, 2, 3, 4)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

    @Test
    public void twoGroupOfFourDifferentBookTwentyPercentDiscount() {
        // Two groups of 4 are better than [5+3]
        BigDecimal totalShouldBe = SINGLE_BOOK_PRICE
                .multiply(BigDecimal.valueOf(4))
                .multiply(BigDecimal.valueOf(0.80))
                .multiply(BigDecimal.valueOf(2));
        basket.setBooks(new ArrayList<>(List.of(0, 0, 1, 1, 2, 2, 3, 4)));
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }
}
