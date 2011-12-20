package com.ejoysoft.common.exception;


/**
 * Created by IntelliJ IDEA.
 */
public class NoAuthException extends Exception {

    public NoAuthException() {
        super("�û�δ����֤");
    }

    public NoAuthException(String message) {
        super(message);
    }
      protected String advice;
      public String getAdvice() {
        return advice;
    }

}
