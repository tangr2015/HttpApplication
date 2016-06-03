package com.tangr.httputils;

/**
 * Created by tangr on 2016/6/1.
 */
public class AppException extends Exception{
    public enum ErrorType {SERVER,IO,TIMEOUT,JSON,FILE_NOT_FOUND,INVALID,CANCEL,UPLOAD}
    public int code;
    public String message;
    public ErrorType type;

    public AppException(int code, String message) {
        super(message);
        this.type = ErrorType.SERVER;
        this.code = code;
        this.message = message;
    }

    public AppException(ErrorType type,String message) {
        super(message);
        this.type = type;
        this.message = message;
    }
}
