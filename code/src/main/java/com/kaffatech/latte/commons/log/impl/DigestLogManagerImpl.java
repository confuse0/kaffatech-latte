package com.kaffatech.latte.commons.log.impl;

import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.ctx.id.IdGenerator;
import com.kaffatech.latte.commons.log.Digest;
import com.kaffatech.latte.commons.log.DigestLogManager;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lingzhen on 16/11/14.
 */
public class DigestLogManagerImpl implements DigestLogManager {

    private static final String INSERT_SQL = "INSERT INTO DIGEST (ID, URL, OP, FROM_INFO, EXECUTOR, REQ, RES, CODE, MESSAGE, STATUS, CREATE_TIME, UPDATE_TIME) VALUES (:id, :url, :op, :fromInfo, :executor, :req, :res, :code, :message, :status, :createTime, :updateTime)";

    @Resource
    private DbAccessor dbAccessor;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public void save(Digest digest) {
        digest.setId(idGenerator.next());
        Date now = new Date();
        digest.setCreateTime(now);
        digest.setUpdateTime(now);

        dbAccessor.update(INSERT_SQL, digest);
    }
}
