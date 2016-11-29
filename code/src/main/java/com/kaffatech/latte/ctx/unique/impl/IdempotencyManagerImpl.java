package com.kaffatech.latte.ctx.unique.impl;

import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.ctx.id.IdGenerator;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.ctx.unique.IdempotencyManager;
import com.kaffatech.latte.ctx.unique.dmo.Idempotency;
import org.springframework.dao.DuplicateKeyException;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lingzhen on 16/11/7.
 */
public class IdempotencyManagerImpl implements IdempotencyManager {

    private static final String INSERT_SQL = "INSERT INTO IDEMPOTENCY (ID, OWNER, TYPE, UNIQUE_NO, CREATE_TIME, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?)";

    @Resource
    private DbAccessor dbAccessor;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public boolean verify(String owner, String type, String uniqueNo) {
        Date now = new Date();
        Idempotency idempotency = createIdempotency(owner, type, uniqueNo);
        try {
            dbAccessor.update(INSERT_SQL, idempotency.getId(), idempotency.getOwner(), idempotency.getType(), idempotency.getUniqueNo(), now, now);
        } catch (DuplicateKeyException e) {
            Log.WARN_LOGGER.warn("幂等性校验错误:" + idempotency);
            return false;
        }
        return true;
    }

    private Idempotency createIdempotency(String owner, String type, String uniqueNo) {
        Idempotency idempotency = new Idempotency();
        idempotency.setId(idGenerator.next());
        idempotency.setOwner(owner);
        idempotency.setType(type);
        idempotency.setUniqueNo(uniqueNo);

        return idempotency;

    }
}
