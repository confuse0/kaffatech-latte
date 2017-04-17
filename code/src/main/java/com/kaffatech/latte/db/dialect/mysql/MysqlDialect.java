package com.kaffatech.latte.db.dialect.mysql;

import com.kaffatech.latte.commons.toolkit.base.PagingUtils;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.db.dialect.Dialect;

import javax.annotation.Resource;
import javax.naming.OperationNotSupportedException;

/**
 * @author lingzhen on 16/12/8.
 */
public class MysqlDialect implements Dialect {

    @Resource
    private DbAccessor dbAccessor;

    @Override
    public String name() {
        return "MysqlDialect";
    }

    @Override
    public String getPagedSql(String origSql, int page, int rows) {
        StringBuilder sb = new StringBuilder();
        sb.append(origSql);
        sb.append(" limit ");
        if (page > 1) {
            sb.append(PagingUtils.getOffset(page, rows));
            sb.append(", ");
        }
        sb.append(rows);

        return sb.toString();
    }

    @Override
    public boolean supportAutoIncrement() {
        return true;
    }

    @Override
    public boolean supportSequence() {
        return false;
    }

    @Override
    public String getNextSequenceSql(String seqName) {
        throw new UnsupportedOperationException(name() + " cannot support getNextSequenceSql.");
    }

    @Override
    public String getCurrSequenceSql(String seqName) {
        throw new UnsupportedOperationException(name() + " cannot support getCurrSequenceSql.");
    }

    public static void main(String[] args) {
        MysqlDialect d = new MysqlDialect();
        String origSql = "select * from TB";
        String pagedSql = d.getPagedSql(origSql, 1, 10);
        System.out.println(pagedSql);
    }
}
