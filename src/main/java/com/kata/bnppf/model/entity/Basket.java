package com.kata.bnppf.model.entity;

import java.util.List;

public class Basket {
    // This list represent the ID book in the basket
    private List<Integer> books;

    public Basket(List<Integer> books) {
        this.books = books;
    }

    public List<Integer> getBooks() {
        return books;
    }

    public void setBooks(List<Integer> books) {
        this.books = books;
    }
}

