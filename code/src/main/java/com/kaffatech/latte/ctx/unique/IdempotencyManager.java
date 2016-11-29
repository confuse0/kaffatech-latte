package com.kaffatech.latte.ctx.unique;

/**
 * @author lingzhen on 16/10/15.
 */
public interface IdempotencyManager {

    boolean verify(String owner, String type, String uniqueNo);
}
