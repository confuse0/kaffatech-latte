package com.kaffatech.latte.db.dialect;

/**
 * @author lingzhen on 16/12/8.
 */
public interface Dialect {

    String name();

    String getPagedSql(String origSql, int page, int rows);

    boolean supportAutoIncrement();

    boolean supportSequence();

    String getNextSequenceSql(String seqName);

    String getCurrSequenceSql(String seqName);
}
