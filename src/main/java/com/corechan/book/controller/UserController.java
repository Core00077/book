package com.corechan.book.controller;

import com.corechan.book.common.Status;
import com.corechan.book.common.exceptions.UnLoginException;
import com.corechan.book.entity.User;
import com.corechan.book.entity.WxData;
import com.corechan.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Status login(@RequestParam String userId, @RequestParam String password, HttpSession session) {
        Status status = new Status();
        status.setStatus(userService.login(userId, password));
        session.setAttribute("username", userId);
        return status;
    }

    @RequestMapping(value = "/wxLogin",method = RequestMethod.POST)
    public Object wxLogin(@RequestParam("code") String code,HttpSession session) throws IOException {
        Status status = new Status();
        WxData wxData=userService.getWxData(code);
        userService.wxLogin(wxData.getOpenid());
        session.setAttribute("username",wxData.getOpenid());
        status.setStatus(Status.StatusCode.success);
        return status;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    Status register(@RequestBody User user) {
        Status status = new Status();
        status.setStatus(userService.register(user));
        return status;
    }

    @RequestMapping(method = RequestMethod.GET)
    Status userInfo(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }
        Status status = new Status();
        status.setData(userService.findUserById(username));
        if (status.getData() == null) {
            status.setStatus(Status.StatusCode.idNotExist);
            status.setMsg("id不存在，无法找到用户信息");
        } else {
            //去除隐私信息
            ((User) status.getData()).setPassword(null);

            status.setStatus(Status.StatusCode.success);
        }
        return status;
    }
}
