package com.corechan.book.service;

import com.corechan.book.common.Status;
import com.corechan.book.common.config.DoubanConfig;
import com.corechan.book.common.exceptions.DoubanIdException;
import com.corechan.book.common.util.RequestSender;
import com.corechan.book.entity.Book;
import com.corechan.book.entity.Collection;
import com.corechan.book.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DoubanService {
    private final UserService userService;
    private final BookService bookService;
    private final DoubanConfig doubanConfig;

    @Autowired
    public DoubanService(UserService userService, BookService bookService, DoubanConfig doubanConfig) {
        this.userService = userService;
        this.bookService = bookService;
        this.doubanConfig = doubanConfig;
    }

    public Status.StatusCode importBooks(String doubanId, String username) {
        //获取书籍
        HashMap map = null;
        try {
            map = (HashMap) RequestSender.SendGet(doubanConfig.getCollectionUrl() + doubanId + "/collections?" + "count=100&start=0", Map.class);
        } catch (HttpClientErrorException e) {
            if (e.getMessage().matches("404 Not Found")) {
                throw new DoubanIdException();
            }
        }
        //新建豆瓣收藏夹
        Collection collection = new Collection();
        collection.setName("豆瓣用户" + doubanId);
        ArrayList content = (ArrayList) map.get("collections");
        for (int i = 0; i < content.size(); i++) {
            String isbn = (String) ((Map) ((HashMap) content.get(i)).get("book")).get("isbn13");
            Book book = bookService.getBook(isbn);
            book.setFromCollection(collection.getName());
            collection.getBooks().put(book.getIsbn(), book);
        }
        User user = userService.findUserById(username);
        user.setDoubanId(doubanId);
        user.getCollections().put(collection.getName(), collection);
        return userService.update(user);
    }

    public Status.StatusCode importUser(String doubanId, String username) {
        User user = userService.findUserById(username);
        HashMap map = RequestSender.SendGet(doubanConfig.getUserUrl() + doubanId, LinkedHashMap.class);
        user.setAvatar((String) map.get("large_avatar"));
        user.setDesc((String) map.get("desc"));
        userService.update(user);
        return Status.StatusCode.success;
    }
}
