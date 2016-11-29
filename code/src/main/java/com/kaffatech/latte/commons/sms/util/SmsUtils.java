package com.kaffatech.latte.commons.sms.util;

import com.kaffatech.latte.commons.sms.model.SmsParameter;
import com.kaffatech.latte.commons.sms.model.SmsResult;

/**
 * @author lingzhen on 16/11/9.
 */
public class SmsUtils {

    public static SmsParameter createRequest(String mobile, String text, String extendInfo) {
        SmsParameter req = new SmsParameter();
        req.setMobile(mobile);
        req.setText(text);
        req.setExtendInfo(extendInfo);

        return req;
    }

    public static SmsResult createResponse(String code, String message) {
        SmsResult res = new SmsResult();
        res.setCode(code);
        res.setMessage(message);

        return res;
    }
}
