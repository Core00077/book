package com.corechan.book.entity;

import java.util.Map;

public class Book {
    private String isbn;
    private String fromCollection;
    private Map<String,Object> doubanInfo;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Map<String, Object> getDoubanInfo() {
        return doubanInfo;
    }

    public void setDoubanInfo(Map<String, Object> doubanInfo) {
        this.doubanInfo = doubanInfo;
    }

    public String getFromCollection() {
        return fromCollection;
    }

    public void setFromCollection(String fromCollection) {
        this.fromCollection = fromCollection;
    }

}
