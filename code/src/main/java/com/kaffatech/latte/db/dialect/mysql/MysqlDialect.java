package com.kaffatech.latte.db.dialect.mysql;

import com.kaffatech.latte.commons.bean.model.type.BooleanType;
import com.kaffatech.latte.commons.toolkit.base.PagingUtils;
import com.kaffatech.latte.commons.toolkit.uuid.UuidUtils;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.db.dialect.Dialect;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lingzhen on 16/12/8.
 */
public class MysqlDialect implements Dialect {

    @Resource
    private DbAccessor dbAccessor;

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
    public long nextSequence(String seqName) {
        String uuid = UuidUtils.generate();
        Date now = new Date();
        String insertSql = "insert into " + seqName + " (SEQ_UUID, CREATE_TIME, UPDATE_TIME, DELETE_FLAG) values (?, ?, ?, ?)";
        dbAccessor.update(insertSql, uuid, now, now, BooleanType.NO.getCode());
        String querySql = "select ID from " + seqName + " where SEQ_UUID = ?";
        long id = dbAccessor.queryForObject(querySql, new Object[]{uuid}, Long.class);
        return id;
    }

    @Override
    public long currSequence(String seqName) {
        String querySql = "select ID from " + seqName + " ORDER BY ID DESC LIMIT 1";
        long id = dbAccessor.queryForObject(querySql, Long.class);
        return id;
    }


    public static void main(String[] args) {
        MysqlDialect d = new MysqlDialect();
        String origSql = "select * from TB";
        String pagedSql = d.getPagedSql(origSql, 1, 10);
        System.out.println(pagedSql);
    }
}
