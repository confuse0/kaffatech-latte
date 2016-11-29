package com.kaffatech.latte.commons.bean.validation.validator;

import com.kaffatech.latte.commons.bean.validation.annotation.DateString;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lingzhen on 16/11/10.
 */
public class DateStringConstraintValidator implements ConstraintValidator<DateString, String> {

    private String format;

    @Override
    public void initialize(DateString ann) {
        this.format = ann.format();
    }

    @Override
    public boolean isValid(String dateString, ConstraintValidatorContext context) {
        if (dateString == null) {
            return true;
        }

        try {
            DateUtils.parseToDate(dateString, format);
        } catch (Exception e) {
            Log.ERROR_LOGGER.error("日期转换出错:" + dateString);
            return false;
        }

        return true;
    }

}
