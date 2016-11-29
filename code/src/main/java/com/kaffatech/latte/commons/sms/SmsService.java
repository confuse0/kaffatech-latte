package com.kaffatech.latte.commons.sms;

import com.kaffatech.latte.commons.sms.model.SmsParameter;
import com.kaffatech.latte.commons.sms.model.SmsResult;

/**
 * Created by lingzhen on 16/4/27.
 */
public interface SmsService {

    SmsResult sendSms(SmsParameter req);
}
