package com.kaffatech.latte.db.dialect.mysql;

import com.kaffatech.latte.commons.toolkit.base.PagingUtils;
import com.kaffatech.latte.db.dialect.Dialect;

/**
 * @author lingzhen on 16/12/8.
 */
public class MysqlDialect implements Dialect {

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

    public static void main(String[] args) {
        MysqlDialect d = new MysqlDialect();
        String origSql = "select * from TB";
        String pagedSql = d.getPagedSql(origSql, 1, 10);
        System.out.println(pagedSql);
    }
}
