package com.kaffatech.latte.ctx.id.sharding.impl;

import com.kaffatech.latte.commons.bean.model.type.BooleanType;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.ctx.id.model.ShardingId;
import com.kaffatech.latte.ctx.id.sharding.ShardingIdGetter;
import com.kaffatech.latte.ctx.id.util.IdGeneratorUtils;
import com.kaffatech.latte.db.accessor.DbAccessor;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lingzhen on 16/11/24.
 */
public class DbShardingIdGetter implements ShardingIdGetter {

    @Resource
    private DbAccessor dbAccessor;

    private static final String QUERY_SHARDING_ID_SQL = "SELECT * FROM SHARDING_ID WHERE CLUSTER_NAME = ? AND SERVER_NAME = ?";

    private static final String INSERT_SHARDING_ID_SQL = "INSERT INTO SHARDING_ID (ID, CLUSTER_NAME, SERVER_NAME, CREATE_TIME, UPDATE_TIME, DELETE_FLAG) VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public long getShardingId() {
        Object[] queryParameter = new Object[]{getClusterName(), IpAddressUtils.getHostAddress()};
        ShardingId shardingId = dbAccessor.queryForObject(QUERY_SHARDING_ID_SQL, queryParameter, ShardingId.class);
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
        shardingId.setClusterName(getClusterName());
        shardingId.setServerName(IpAddressUtils.getHostAddress());
        long nextSequence = dbAccessor.nextSequence("SEQ_SHARDING_ID");
        shardingId.setId(nextSequence);

        // 插入数据
        Date now = new Date();
        dbAccessor.update(INSERT_SHARDING_ID_SQL, shardingId.getId(), shardingId.getClusterName(), shardingId.getServerName(), now, now, BooleanType.NO.getCode());

        return shardingId;
    }

    private String getClusterName() {
        String clusterName = SystemProperties.getProperty("clusterName");
        return StringUtils.isEmpty(clusterName) ? "CORE" : clusterName;

    }
}
