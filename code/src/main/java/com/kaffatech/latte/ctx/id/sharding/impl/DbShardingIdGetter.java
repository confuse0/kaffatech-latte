package com.kaffatech.latte.ctx.id.sharding.impl;

import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.ctx.id.model.ShardingId;
import com.kaffatech.latte.ctx.id.sharding.ShardingIdGetter;
import com.kaffatech.latte.ctx.id.util.IdGeneratorUtils;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lingzhen on 16/11/24.
 */
public class DbShardingIdGetter implements ShardingIdGetter {

    @Resource
    private DbAccessor dbAccessor;

    private static final String QUERY_SHARDING_ID_SQL = "SELECT * FROM SHARDING_ID WHERE NAME = ?";

    private static final String LOCK_SHARDING_ID_SQL = "SELECT * FROM SHARDING_ID ORDER BY ID DESC FOR UPDATE";

    private static final String INSERT_SHARDING_ID_SQL = "INSERT INTO SHARDING_ID (ID, NAME, CREATE_TIME, UPDATE_TIME) VALUES (?, ?, ?, ?)";

    @Override
    public long getShardingId() {
        String name = IpAddressUtils.getHostAddress();
        ShardingId shardingId = dbAccessor.queryForObject(QUERY_SHARDING_ID_SQL, new Object[]{name}, ShardingId.class);
        if (shardingId == null) {
            // 以前没有过,需要产生新的ID
            shardingId = generate();
        }
        IdGeneratorUtils.validateShardingId(shardingId.getId());

        return shardingId.getId();
    }

    /**
     * 产生新的ID(需要在一个事务里)
     *
     * @return
     */
    private ShardingId generate() {
        ShardingId shardingId = new ShardingId();
        shardingId.setName(IpAddressUtils.getHostAddress());
        List<ShardingId> all = dbAccessor.queryForList(LOCK_SHARDING_ID_SQL, ShardingId.class);
        if (CollectionUtils.isEmpty(all)) {
            // 空表从1开始
            shardingId.setId(1L);
        } else {
            // 增加1
            shardingId.setId(all.get(0).getId() + 1L);
        }
        // 插入数据
        Date now = new Date();
        dbAccessor.update(INSERT_SHARDING_ID_SQL, shardingId.getId(), shardingId.getName(), now, now);

        return shardingId;
    }
}
