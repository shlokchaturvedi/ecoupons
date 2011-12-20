package com.ejoysoft.common.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-11
 * Time: 13:42:02
 * To change this template use Options | File Templates.
 */
public class NoRightException extends NoAuthException {

    public NoRightException(String message) {
        super(message);
        this.advice = "请重新重新登陆系统";
    }

    public NoRightException(String message, String advice) {
        super(message);
        this.advice = advice;
    }
}