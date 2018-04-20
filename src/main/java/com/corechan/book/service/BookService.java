package com.corechan.book.service;

import com.corechan.book.common.config.DoubanConfig;
import com.corechan.book.common.exceptions.DoubanBookException;
import com.corechan.book.common.exceptions.DoubanException;
import com.corechan.book.common.exceptions.DoubanIdException;
import com.corechan.book.common.util.RequestSender;
import com.corechan.book.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
public class BookService {
    private final DoubanConfig doubanConfig;

    @Autowired
    public BookService(DoubanConfig doubanConfig) {
        this.doubanConfig = doubanConfig;
    }

    public Book getBook(String isbn) {
        Book book = new Book();
        book.setIsbn(isbn);
        try {
            book.setDoubanInfo(RequestSender.SendGet(doubanConfig.getBookUrl() + isbn, Map.class));
        } catch (HttpClientErrorException e) {
            if(e.getMessage().matches("404 Not Found")){
                throw new DoubanBookException();
            }
        }
        return book;
    }
}
