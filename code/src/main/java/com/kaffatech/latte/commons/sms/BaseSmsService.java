package com.kaffatech.latte.commons.sms;

import com.kaffatech.latte.cache.Cache;
import com.kaffatech.latte.mainframe.model.type.BaseResultCode;
import com.kaffatech.latte.mainframe.model.exception.BizException;
import com.kaffatech.latte.commons.log.Digest;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.log.util.LogUtils;
import com.kaffatech.latte.commons.sms.model.SmsParameter;
import com.kaffatech.latte.commons.sms.model.SmsResult;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import javax.annotation.Resource;

/**
 * @author lingzhen on 16/11/9.
 */
public abstract class BaseSmsService implements SmsService {

    @Resource
    private Cache<String, SmsSendLog> smdSendCache;

    @Override
    public SmsResult sendSms(SmsParameter req) {
        long startTime = System.currentTimeMillis();
        SmsResult res = null;
        try {
            validate(req.getMobile());
            //validate(SessionContextHolder.getSession().getId());

            res = send(req);
        } finally {
            Digest digest = createDigest(req, res, System.currentTimeMillis() - startTime);

            LogUtils.info(Log.SMS_DIGEST_LOGGER, digest == null ? "" : digest);
        }

        return res;
    }

    protected abstract SmsResult send(SmsParameter req);

    protected abstract Digest createDigest(SmsParameter req, SmsResult res, long cost);

    private void validate(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new NullPointerException("key is invalid!");
        }

        SmsSendLog log = smdSendCache.get(key);

        if (log != null) {
            Long time1 = log.getSubTime(1);
            if (time1 != null
                    && time1 >= DateUtils.getBeforeTime(1 * 60 * 1000L)) {
                // 一分钟内最多发送一次
                throw new BizException(BaseResultCode.SEND_CHECK_CODE_SHORT);
            }

            Long time3 = log.getSubTime(3);
            if (time3 != null
                    && time3 >= DateUtils.getBeforeTime(30 * 60 * 1000L)) {
                // 半小时内最多发送三次
                throw new BizException(BaseResultCode.SEND_CHECK_CODE_SHORT);
            }

            Long time10 = log.getSubTime(10);
            if (time10 != null
                    && time10 >= DateUtils.getBeforeTime(24 * 60 * 60 * 1000L)) {
                // 一天内最多发送十次
                throw new BizException(BaseResultCode.SEND_CHECK_CODE_SHORT);
            }

            log.addTime();
            smdSendCache.put(key, log);
        } else {
            smdSendCache.put(key, new SmsSendLog());
        }
    }
}
