package com.kata.bnppf.controller;

import com.kata.bnppf.model.entity.Basket;
import com.kata.bnppf.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/basket")
public class BasketController {
    @Autowired
    private BasketService service;

    @PostMapping("/getTotal")
    public BigDecimal getTotal(@RequestBody Basket basket) {
        return service.getTotal(basket);
    }
}
