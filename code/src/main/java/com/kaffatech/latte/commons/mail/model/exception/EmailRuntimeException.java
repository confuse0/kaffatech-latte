package com.kaffatech.latte.commons.mail.model.exception;

/**
 * @author lingzhen on 16/9/10.
 */
public class EmailRuntimeException extends RuntimeException {

    public EmailRuntimeException(Exception e) {
        super(e);
    }
}
