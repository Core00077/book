package com.corechan.book.controller;

import com.corechan.book.common.Status;
import com.corechan.book.common.exceptions.UnLoginException;
import com.corechan.book.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Status getCollections(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }

        Status status = new Status();
        status.setData(collectionService.getCollections(username));
        status.setStatus(Status.StatusCode.success);
        return status;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Status postCollection(@RequestParam String collectionName, HttpSession session) {
        String username= (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }

        Status status = new Status();
        status.setStatus(collectionService.postCollection(username, collectionName));
        return status;
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public Status deleteCollection(@RequestParam String collectionName, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }

        Status status = new Status();
        status.setStatus(collectionService.deleteCollection(username, collectionName));
        return status;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Status putCollection(@RequestParam String collectionOldName, @RequestParam String collectionName, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }

        Status status = new Status();
        status.setStatus(collectionService.putCollection(username, collectionOldName, collectionName));
        return status;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public Status getBooks(@RequestParam String collectionName, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }

        Status status = new Status();
        status.setData(collectionService.getBooks(username, collectionName));
        status.setStatus(Status.StatusCode.success);
        return status;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public Status postBook(@RequestParam String collectionName, @RequestParam String isbn, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }

        Status status = new Status();
        status.setStatus(collectionService.postBook(username, collectionName, isbn));
        return status;
    }

    @RequestMapping(value = "/books", method = RequestMethod.DELETE)
    public Status deleteBook(@RequestParam String collectionName, @RequestParam String isbn, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new UnLoginException();
        }
        Status status = new Status();
        status.setStatus(collectionService.deleteBook(username, collectionName, isbn));
        return status;
    }
}
