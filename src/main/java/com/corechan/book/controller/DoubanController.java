package com.corechan.book.controller;

import com.corechan.book.common.Status;
import com.corechan.book.common.exceptions.UnLoginException;
import com.corechan.book.service.DoubanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/importFromDouban")
public class DoubanController {
    private final DoubanService doubanService;

    @Autowired
    public DoubanController(DoubanService doubanService) {
        this.doubanService = doubanService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Status importBooksFromDoubanId(@RequestParam String doubanId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }

        Status status = new Status();
        doubanService.importBooks(doubanId, username);
        doubanService.importUser(doubanId, username);
        status.setStatus(Status.StatusCode.success);
        return status;
    }
}
