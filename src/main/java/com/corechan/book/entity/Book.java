package com.corechan.book.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "Book")
public class Book {
    @Id
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
