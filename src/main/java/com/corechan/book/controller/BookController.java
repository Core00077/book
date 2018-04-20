package com.corechan.book.controller;

import com.corechan.book.common.exceptions.UnLoginException;
import com.corechan.book.entity.Book;
import com.corechan.book.common.Status;
import com.corechan.book.service.BookService;
import com.corechan.book.service.CollectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final CollectionService collectionService;
    private final BookService bookService;

    @Autowired
    public BookController(CollectionService collectionService, BookService bookService) {
        this.collectionService = collectionService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
    @ApiOperation(value = "获取Book信息")
    public Status getBook(@PathVariable("isbn") String isbn) {
        Book book=bookService.getBook(isbn);
        Status status = new Status();
        status.setData(book);
        if(book!=null) {
            status.setStatus(Status.StatusCode.success);
            status.setMsg("ok");
        }
        else {
            status.setStatus(Status.StatusCode.bookNotExist);
        }
        return status;
    }

    @RequestMapping(value = "/{isbn}/hasBook", method = RequestMethod.GET)
    public Status hasBook(@PathVariable("isbn") String isbn, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }
        Status status = new Status();
        status.setData(collectionService.hasBook(username, isbn));
        status.setStatus(Status.StatusCode.success);
        return status;
    }
}
