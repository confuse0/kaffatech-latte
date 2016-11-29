package com.kaffatech.latte.commons.bean.validation.util;

import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.mainframe.model.type.BaseResultCode;
import com.kaffatech.latte.mainframe.model.exception.BizException;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.log.util.LogUtils;
import com.kaffatech.latte.commons.toolkit.base.ArrayUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author lingzhen on 16/11/9.
 */
public class ValidationUtils {

    private static final String DEF_NAME = "validator";

    public static void validate(Object arg) {
        if (arg == null) {
            throw new BizException(BaseResultCode.ILLEGAL_REQ_PARAMETER);
        }

        Annotation validedAnn = getAnnotaion(arg);
        if (validedAnn != null) {
            // 进行参数校验
            Set<ConstraintViolation<Object>> resultSet = null;
            Class<?>[] groupArrays = ((Validated) validedAnn).value();
            if (ArrayUtils.isEmpty(groupArrays)) {
                resultSet = getValidator().validate(arg);
            } else {
                resultSet = getValidator().validate(arg, groupArrays);
            }

            if (resultSet.size() > 0) {
                LogUtils.warn(Log.WARN_LOGGER, BaseResultCode.ILLEGAL_REQ_PARAMETER.getMessage() + ":" + resultSet);
                // 存在校验不过的数据
                throw new BizException(BaseResultCode.ILLEGAL_REQ_PARAMETER);
            }
        }
    }

    private static Validator getValidator() {
        return (Validator) ApplicationContextHolder.getBean(DEF_NAME);
    }

    private static Annotation getAnnotaion(Object arg) {
        Annotation[] anns = arg.getClass().getAnnotations();
        if (ArrayUtils.isEmpty(anns)) {
            return null;
        }

        for (Annotation each : anns) {
            if (each instanceof Validated) {
                // 如果存在
                return each;
            }
        }
        return null;
    }
}
