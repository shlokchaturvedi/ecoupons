package com.ejoysoft.common.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-10
 * Time: 11:32:14
 * To change this template use Options | File Templates.
 */
public class UserUnitIdException extends ApplicationException {

    public UserUnitIdException(String message) {
        super(message);
        this.advice = "������ѡ���ϼ�";
    }

    public UserUnitIdException(String message, String advice) {
        super(message);
        this.advice = advice;
    }
}
