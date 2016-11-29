package com.kaffatech.latte.commons.io.model.exception;

import java.io.IOException;

/**
 * @author lingzhen on 16/9/10.
 */
public class IoRuntimeException extends RuntimeException {

    public IoRuntimeException(IOException e) {
        super(e);
    }
}
