package com.corechan.book.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;

public class Collection {

    private String name;
    private HashMap<String,Book> books=new HashMap<>();
    private Integer size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Book> getBooks() {
        return books;
    }

    public void setBooks(HashMap<String, Book> books) {
        this.books = books;
    }

    public Integer getSize() {
        return books.size();
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
