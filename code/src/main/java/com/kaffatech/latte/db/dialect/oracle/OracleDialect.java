package com.kaffatech.latte.db.dialect.oracle;

import com.kaffatech.latte.commons.toolkit.base.PagingUtils;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.db.dialect.Dialect;

import javax.annotation.Resource;

/**
 * @author lingzhen on 16/12/8.
 */
public class OracleDialect implements Dialect {

    @Resource
    private DbAccessor dbAccessor;

    @Override
    public String name() {
        return "OracleDialect";
    }

    @Override
    public String getPagedSql(String origSql, int page, int rows) {
        int start = PagingUtils.getOffset(page, rows);
        int end = start + rows;

        StringBuilder sb = new StringBuilder();
        sb.append("select * from (");
        sb.append("select ROWNUM RN, TEMP_PAGED_TABLE.* from (");
        sb.append(origSql);
        sb.append(") TEMP_PAGED_TABLE where ROWNUM <= ");
        sb.append(end);
        sb.append(") where RN > ");
        sb.append(start);

        return sb.toString();
    }

    @Override
    public boolean supportAutoIncrement() {
        return false;
    }

    @Override
    public boolean supportSequence() {
        return true;
    }

    @Override
    public String getNextSequenceSql(String seqName) {
        return "select " + seqName + ".nextval from " + seqName;
    }

    @Override
    public String getCurrSequenceSql(String seqName) {
        return "select " + seqName + ".currval from " + seqName;
    }

    public static void main(String[] args) {
        OracleDialect d = new OracleDialect();
        String origSql = "select * from TB";
        String pagedSql = d.getPagedSql(origSql, 1, 10);
        System.out.println(pagedSql);
    }
}
