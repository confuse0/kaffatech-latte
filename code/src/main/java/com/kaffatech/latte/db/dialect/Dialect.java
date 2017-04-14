package com.kaffatech.latte.db.dialect;

/**
 * @author lingzhen on 16/12/8.
 */
public interface Dialect {

    String getPagedSql(String origSql, int page, int rows);

    long nextSequence(String seqName);

    long currSequence(String seqName);
}
