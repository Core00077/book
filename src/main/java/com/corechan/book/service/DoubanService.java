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
    private final DoubanConfig doubanConfig;

    @Autowired
    public DoubanService(UserService userService, DoubanConfig doubanConfig) {
        this.userService = userService;
        this.doubanConfig = doubanConfig;
    }

    public Status.StatusCode importBooks(String doubanId, String username) {
        User user = userService.findUserById(username);
        user.setDoubanId(doubanId);

        //新建豆瓣收藏夹
        Collection collection = new Collection();
        collection.setName("豆瓣用户" + doubanId);

        //获取书籍加入收藏夹
        HashMap map = null;
        try {
            map = (HashMap) RequestSender.SendGet(doubanConfig.getCollectionUrl() + doubanId + "/collections?" + "count=100&start=0", Map.class);
        } catch (HttpClientErrorException e) {
            if (e.getMessage().matches("404 Not Found")) {
                throw new DoubanIdException();
            }
        }
        ArrayList content = (ArrayList) map.get("collections");
        for (int i = 0; i < content.size(); i++) {
            Book book = new Book();
            book.setDoubanInfo((Map<String, Object>) ((HashMap) content.get(i)).get("book"));
            book.setIsbn(book.getDoubanInfo().get("isbn13").toString());
            book.setFromCollection(collection.getName());

            collection.getBooks().put(book.getIsbn(), book);
        }
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
