package com.kaffatech.latte.commons.io.model.exception;

/**
 * @author lingzhen on 16/9/10.
 */
public class IoRuntimeException extends RuntimeException {

    public IoRuntimeException(String msg) {
        super(msg);
    }

    public IoRuntimeException(Exception e) {
        super(e);
    }
}
