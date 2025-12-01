package com.orange.exception;

public class BaseException extends RuntimeException {
    /**
     * 业务异常
     */
    public BaseException(){}
    public BaseException(String message) {
        super(message);
    }
}
