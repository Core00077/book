package com.corechan.book.common;

public class Status {
    private StatusCode status;
    private String msg;
    private Object data;

    public enum StatusCode{
        init,
        unLogin,
        success,
        fail,
        wrongPassword,
        idNotExist,
        idAlreadyExist,
        collectionAlreadyExist,
        collectionNotExist,
        bookAlreadyExist,
        bookNotExist,
        doubanIdNotExist,
        doubanBookNotExist
    }

    public Status() {
        status=StatusCode.init;
        msg="";
        data=null;
    }

    public StatusCode getStatus() {
        return status;
    }

    public void setStatus(StatusCode status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
