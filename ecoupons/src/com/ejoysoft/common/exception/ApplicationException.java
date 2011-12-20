package com.ejoysoft.common.exception;

/**
 * 应用系统异常
 * 它是一个超类，所有的应用系统异常均是它的子类
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-10
 * Time: 11:30:21
 * To change this template use Options | File Templates.
 */
public class ApplicationException extends Exception {
      protected String advice;

    public ApplicationException(String message) {
        super(message);
    }

    public String getAdvice() {
        return advice;
    }

}