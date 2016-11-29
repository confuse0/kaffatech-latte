package com.kaffatech.latte.ctx.id.impl;

import com.kaffatech.latte.ctx.id.IdGenerator;
import com.kaffatech.latte.ctx.id.sharding.ShardingIdGetter;
import com.kaffatech.latte.ctx.id.sharding.impl.SimpleShardingIdGetter;
import com.kaffatech.latte.ctx.id.util.IdGeneratorUtils;
import com.kaffatech.latte.commons.toolkit.base.math.ByteUtils;

/**
 * @author lingzhen on 16/11/24.
 */
public class IdGeneratorImpl implements IdGenerator {

    private long lastTimestamp = 0L;

    private long shardingId;

    private long sequence = 0L;

    private ShardingIdGetter shardingIdGetter;

    public void init() {
        shardingId = shardingIdGetter.getShardingId();
    }

    /**
     * 产生ID(41Bit时间戳 + 11Bit分片ID + 11Bit序号)
     *
     * @return
     */
    public synchronized long next() {
        long timestamp = System.currentTimeMillis();

        if (timestamp > lastTimestamp) {
            // 初始化序号
            sequence = 0L;
        } else if (timestamp == lastTimestamp) {
            IdGeneratorUtils.validateSequence(sequence);
            sequence++;
        } else {
            // 时间倒流
            throw new IllegalStateException("timestamp is invalid");
        }

        lastTimestamp = timestamp;

        long nextId = IdGeneratorUtils.generate(timestamp, shardingId, sequence);

        return nextId;
    }

    public void setShardingIdGetter(ShardingIdGetter shardingIdGetter) {
        this.shardingIdGetter = shardingIdGetter;
    }

    public static void main(String[] args) {
        IdGeneratorImpl idGenerator = new IdGeneratorImpl();
        idGenerator.setShardingIdGetter(new SimpleShardingIdGetter());

        for (int i = 0; i < 100; i++) {
            Long id = idGenerator.next();

            System.out.println(id);
            System.out.println(ByteUtils.byteToHexString(ByteUtils.longToBytes(id)));
        }


    }
}
