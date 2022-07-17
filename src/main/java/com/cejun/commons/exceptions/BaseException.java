package com.cejun.commons.exceptions;

public class BaseException extends RuntimeException{

    private int code;

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException() {
        this("server exception");
    }

    public BaseException(String message) {
        this(500, message);
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
}
