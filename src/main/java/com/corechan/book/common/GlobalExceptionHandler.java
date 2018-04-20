package com.corechan.book.common;

import com.corechan.book.common.exceptions.DoubanBookException;
import com.corechan.book.common.exceptions.DoubanException;
import com.corechan.book.common.exceptions.DoubanIdException;
import com.corechan.book.common.exceptions.UnLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW="error";

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Status defaultError(Exception e){
        Status errorStatus=new Status();
        errorStatus.setStatus(Status.StatusCode.fail);
        errorStatus.setMsg(e.getMessage());
        e.printStackTrace();
        return errorStatus;
    }
    @ResponseBody
    @ExceptionHandler(value = DoubanBookException.class)
    public Status doubanBookError(DoubanBookException e){
        Status errorStatus=new Status();
        errorStatus.setStatus(Status.StatusCode.doubanBookNotExist);
        errorStatus.setMsg(e.getMessage());
        return errorStatus;
    }

    @ResponseBody
    @ExceptionHandler(value = DoubanIdException.class)
    public Status doubanIdError(DoubanIdException e){
        Status errorStatus=new Status();
        errorStatus.setStatus(Status.StatusCode.doubanIdNotExist);
        errorStatus.setMsg(e.getMessage());
        return errorStatus;
    }

    @ResponseBody
    @ExceptionHandler(value = UnLoginException.class)
    public Status unLoginError(UnLoginException e){
        Status errorStatus=new Status();
        errorStatus.setStatus(Status.StatusCode.unLogin);
        errorStatus.setMsg(e.getMessage());
        return errorStatus;
    }

}
