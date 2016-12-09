package com.kaffatech.latte.db.dialect.oracle;

import com.kaffatech.latte.commons.toolkit.base.PagingUtils;
import com.kaffatech.latte.db.dialect.Dialect;

/**
 * @author lingzhen on 16/12/8.
 */
public class OracleDialect implements Dialect {

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

    public static void main(String[] args) {
        OracleDialect d = new OracleDialect();
        String origSql = "select * from TB";
        String pagedSql = d.getPagedSql(origSql, 1, 10);
        System.out.println(pagedSql);
    }
}
