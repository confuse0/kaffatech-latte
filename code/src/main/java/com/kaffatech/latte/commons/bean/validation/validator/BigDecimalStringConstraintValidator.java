package com.kaffatech.latte.commons.bean.validation.validator;

import com.kaffatech.latte.commons.bean.validation.annotation.BigDecimalString;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * @author lingzhen on 16/11/10.
 */
public class BigDecimalStringConstraintValidator implements ConstraintValidator<BigDecimalString, String> {

    private int max;

    private int scale;

    @Override
    public void initialize(BigDecimalString ann) {
        this.max = ann.max();
        this.scale = ann.scale();
    }

    @Override
    public boolean isValid(String bigDecimalString, ConstraintValidatorContext context) {
        if (bigDecimalString == null) {
            return true;
        }

        if (StringUtils.startsWith(bigDecimalString, ".") || StringUtils.endsWith(bigDecimalString, ".")) {
            return false;
        }

        try {
            BigDecimal decimal = new BigDecimal(bigDecimalString);

            if (decimal.scale() > scale) {
                return false;
            }

            if (String.valueOf(decimal.longValue()).length() > (max - scale)) {
                return false;
            }
        } catch (Exception e) {
            Log.ERROR_LOGGER.error("数字转换出错:" + bigDecimalString);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("200");
        System.out.println(decimal.longValueExact());
    }

}
