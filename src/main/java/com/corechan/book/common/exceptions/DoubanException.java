package com.corechan.book.common.exceptions;

public class DoubanException extends RuntimeException{
    public DoubanException() {
        super("豆瓣404了");
    }
    public DoubanException(String message) {
        super(message);
    }

}
