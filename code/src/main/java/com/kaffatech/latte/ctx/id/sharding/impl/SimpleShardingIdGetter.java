package com.kaffatech.latte.ctx.id.sharding.impl;

import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.ctx.id.sharding.ShardingIdGetter;
import com.kaffatech.latte.ctx.id.util.IdGeneratorUtils;

/**
 * @author lingzhen on 16/11/24.
 */
public class SimpleShardingIdGetter implements ShardingIdGetter {

    @Override
    public long getShardingId() {
        long sid = SystemProperties.getShardingId();
        IdGeneratorUtils.validateShardingId(sid);

        return sid;
    }
}
