package com.corechan.book.service;

import com.corechan.book.common.config.DoubanConfig;
import com.corechan.book.common.exceptions.DoubanBookException;
import com.corechan.book.common.util.RequestSender;
import com.corechan.book.dao.BookDao;
import com.corechan.book.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
public class BookService {
    private final DoubanConfig doubanConfig;
    private final BookDao bookDao;

    @Autowired
    public BookService(DoubanConfig doubanConfig, BookDao bookDao) {
        this.doubanConfig = doubanConfig;
        this.bookDao = bookDao;
    }

    public Book getBook(String isbn) {
        if (bookDao.findById(isbn).isPresent()) {
            return bookDao.findById(isbn).get();
        } else {
            Book book = new Book();
            book.setIsbn(isbn);
            try {
                book.setDoubanInfo(RequestSender.SendGet(doubanConfig.getBookUrl() + isbn, Map.class));
            } catch (HttpClientErrorException e) {
                if (e.getMessage().matches("404 Not Found")) {
                    throw new DoubanBookException();
                }
            }
            bookDao.save(book);
            return book;
        }
    }
}
