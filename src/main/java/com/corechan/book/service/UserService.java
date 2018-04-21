package com.corechan.book.service;

import com.corechan.book.common.config.WxConfig;
import com.corechan.book.common.util.BCryptUtil;
import com.corechan.book.common.util.Jackson;
import com.corechan.book.common.util.RequestSender;
import com.corechan.book.dao.UserDao;
import com.corechan.book.common.Status;
import com.corechan.book.entity.User;
import com.corechan.book.entity.WxData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    private final UserDao userDao;
    private final WxConfig wxConfig;

    @Autowired
    public UserService(UserDao userDao, WxConfig wxConfig) {
        this.userDao = userDao;
        this.wxConfig = wxConfig;
    }

    public Status.StatusCode register(User user) {
        //清洗user数据
        User newUser = new User();
        newUser.setUserId(user.getUserId());
        newUser.setName(user.getName());
        newUser.setPassword(BCryptUtil.encode(user.getPassword()));
//        newUser.setDoubanId(user.getDoubanId());
        if (userDao.findByUserId(newUser.getUserId()).isPresent()) {
            return Status.StatusCode.idAlreadyExist;
        } else {
            userDao.save(newUser);
            return Status.StatusCode.success;
        }
    }

    public Status.StatusCode login(String userId, String password) {
        if (userDao.findByUserId(userId).isPresent()) {
            if (BCryptUtil.match(password, userDao.findByUserId(userId).get().getPassword())) {
                return Status.StatusCode.success;
            } else {
                return Status.StatusCode.wrongPassword;
            }
        } else {
            return Status.StatusCode.idNotExist;
        }
    }

    public Status.StatusCode wxLogin(String userId) {
        if (userDao.findByUserId(userId).isPresent()) {
            return Status.StatusCode.success;
        } else {
            User newUser = new User();
            newUser.setUserId(userId);
            newUser.setName(userId);
            userDao.save(newUser);
            return Status.StatusCode.success;
        }
    }

    public WxData getWxData(String code) throws IOException {

        String url = wxConfig.getLoginUrl() + "?" +
                "appid=" + wxConfig.getAppid() + "&" +
                "secret=" + wxConfig.getSecret() + "&" +
                "js_code=" + code + "&" +
                "grant_type=authorization_code";
        String s = RequestSender.SendGet(url, String.class);
        WxData wxData = Jackson.fromJson(s, WxData.class);
        return wxData;
    }

    public User findUserById(String userId) {
        if (userDao.findByUserId(userId).isPresent())
            return userDao.findByUserId(userId).get();
        else {
            return null;
        }
    }

    public Status.StatusCode update(User user) {
        userDao.save(user);
        return Status.StatusCode.success;
    }
}
