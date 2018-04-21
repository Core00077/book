package com.corechan.book.service;

import com.corechan.book.common.Status;
import com.corechan.book.common.config.DoubanConfig;
import com.corechan.book.common.util.RequestSender;
import com.corechan.book.entity.Book;
import com.corechan.book.entity.Collection;
import com.corechan.book.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class CollectionService {
    private final UserService userService;
    private final BookService bookService;
    private final DoubanConfig doubanConfig;

    @Autowired
    public CollectionService(UserService userService, BookService bookService, DoubanConfig doubanConfig) {
        this.userService = userService;
        this.bookService = bookService;
        this.doubanConfig = doubanConfig;
    }

    public HashMap<String, Collection> getCollections(String username) {
        return userService.findUserById(username).getCollections();
    }

    public Status.StatusCode postCollection(String username, String collectionName) {
        User user = userService.findUserById(username);
        Collection collection = new Collection();
        collection.setName(collectionName);
        if (user.getCollections().containsKey(collectionName)) {
            return Status.StatusCode.collectionAlreadyExist;
        } else {
            user.getCollections().put(collectionName, collection);
            return userService.update(user);
        }
    }

    public Status.StatusCode deleteCollection(String username, String collectionName) {

        User user = userService.findUserById(username);
        if (user.getCollections().containsKey(collectionName)) {
            user.getCollections().remove(collectionName);
            return userService.update(user);
        } else {
            return Status.StatusCode.collectionNotExist;
        }
    }

    public Status.StatusCode putCollection(String username, String collectionOldName, String collectionName) {

        User user = userService.findUserById(username);
        if (user.getCollections().containsKey(collectionOldName)) {
            Collection collection = user.getCollections().get(collectionOldName);
            collection.setName(collectionName);
            for (String book : collection.getBooks().keySet()) {
                collection.getBooks().get(book).setFromCollection(collectionName);
            }
            user.getCollections().remove(collectionOldName);
            user.getCollections().put(collectionName, collection);
            return userService.update(user);
        } else {
            return Status.StatusCode.collectionNotExist;
        }
    }

    public HashMap<String, Book> getBooks(String username, String collectionName) {
        return userService.findUserById(username).getCollections().get(collectionName).getBooks();
    }

    public Status.StatusCode postBook(String username, String collectionName, String isbn) {
        User user = userService.findUserById(username);
        Book book = bookService.getBook(isbn);

        book.setFromCollection(user.getCollections().get(collectionName).getName());
        if (user.getCollections().containsKey(collectionName)) {
            if (user.getCollections().get(collectionName).getBooks().containsKey(isbn)) {
                return Status.StatusCode.bookAlreadyExist;
            } else {
                user.getCollections().get(collectionName).getBooks().put(isbn, book);
                return userService.update(user);
            }
        } else {
            return Status.StatusCode.collectionNotExist;
        }
    }

    public Status.StatusCode deleteBook(String username, String collectionName, String isbn) {
        User user = userService.findUserById(username);
        if (user.getCollections().containsKey(collectionName)) {
            if (user.getCollections().get(collectionName).getBooks().containsKey(isbn)) {
                user.getCollections().get(collectionName).getBooks().remove(isbn);
                return userService.update(user);
            } else {
                return Status.StatusCode.bookNotExist;
            }
        } else {
            return Status.StatusCode.collectionNotExist;
        }


    }

    public boolean hasBook(String username, String isbn) {
        HashMap<String, Collection> collections = userService.findUserById(username).getCollections();
        for (String collectionName : collections.keySet()) {
            if (collections.get(collectionName).getBooks().containsKey(isbn)) {
                return true;
            }
        }
        return false;
    }
}
