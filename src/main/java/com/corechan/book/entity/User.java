package com.corechan.book.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String userId;
    private String name="default_value";
    private String password;
    private String doubanId;
    private String avatar;
    private String desc;
    private HashMap<String,Collection> collections=new HashMap<>();

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDoubanId() {
        return doubanId;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }


    public HashMap<String, Collection> getCollections() {
        return collections;
    }

    public void setCollections(HashMap<String, Collection> collections) {
        this.collections = collections;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
