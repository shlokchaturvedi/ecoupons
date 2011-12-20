package com.ejoysoft.common.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-11
 * Time: 14:03:14
 * To change this template use Options | File Templates.
 */
public class IdObjectException extends ApplicationException {

    public IdObjectException(String message) {
        super(message);
        this.advice = "������ѡ���¼��Ϣ";
    }

    public IdObjectException(String message, String advice) {
        super(message);
        this.advice = advice;
    }
}
