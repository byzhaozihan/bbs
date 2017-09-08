package com.zzh.exception;

/**
 * 用户已经存在异常
 */
public class UserExistException extends Exception{
    public UserExistException(String errorMsg) {
        super(errorMsg);

    }
}
