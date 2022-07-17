package com.cejun.commons.enums;

public enum ExceptionType {

    FAIL(500, "server unknown exception");

    private int code;

    private String message;

    ExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
