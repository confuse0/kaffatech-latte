package com.kaffatech.latte.ctx.id.util;

import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.ctx.id.IdGenerator;

/**
 * @author lingzhen on 16/11/24.
 */
public class IdGeneratorUtils {

    public final static String DEF_NAME = "idGenerator";

    /**
     * 启始时间
     */
    private final static long START_TIME = 1423846923000L;

    /**
     * 机器分片位数(从1开始)
     */
    private final static long SHARDING_ID_BITS = 11L;

    /**
     * 每台机器的序列号位数(从0开始)
     */
    private final static long SEQ_BITS = 11L;

    public final static long MAX_SEQ = (1L << SEQ_BITS) - 1;

    private final static long SHARDING_ID_LEFT_MOVE_LEN = SEQ_BITS;

    private final static long MAX_SHARDING_ID = (1L << SHARDING_ID_BITS) - 1;

    private final static long TIMESTAMP_LEFT_MOVE_LEN = SHARDING_ID_BITS
            + SEQ_BITS;

    public static long next() {
        IdGenerator idGenerator = (IdGenerator) ApplicationContextHolder.getBean(DEF_NAME);
        return idGenerator.next();
    }

    public static long getTimestamp(long id) {
        long ts = (id >> TIMESTAMP_LEFT_MOVE_LEN) + START_TIME;
        return ts;
    }

    public static long generate(long timestamp, long shardingId, long sequence) {
        long nextId = ((timestamp - START_TIME) << TIMESTAMP_LEFT_MOVE_LEN)
                | (shardingId << SHARDING_ID_LEFT_MOVE_LEN) | sequence;
        return nextId;
    }

    public static void validateShardingId(long shardingId) {
        if (shardingId > MAX_SHARDING_ID || shardingId < 0) {
            throw new IllegalArgumentException(String.format(
                    "shardingId can't be greater than %d or less than 0",
                    MAX_SHARDING_ID));
        }
    }

    public static void validateSequence(long sequence) {
        if (sequence >= MAX_SEQ) {
            // 序号超过最大值产生重复数据
            throw new IllegalStateException("sequence is full");
        }
    }
}
