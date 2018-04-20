package com.corechan.book.common.exceptions;

public class UnLoginException extends RuntimeException {
    public UnLoginException() {
        super("尚未登录");
    }

    public UnLoginException(String message) {
        super(message);
    }
}
