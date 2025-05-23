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
        BigDecimal totalShouldBe = new BigDecimal(135);
        Collections.addAll(basket.getBooks(), 0, 1, 2);
        assertTrueBetweenSupposedTotalAndBasketGetTotal(totalShouldBe);
    }

}
