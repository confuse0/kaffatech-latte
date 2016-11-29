package com.kaffatech.latte.commons.bean.validation.validator;

import com.kaffatech.latte.commons.bean.validation.annotation.MoneyString;
import com.kaffatech.latte.commons.bean.model.num.Money;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.toolkit.base.math.MoneyUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lingzhen on 16/11/10.
 */
public class MoneyStringConstraintValidator implements ConstraintValidator<MoneyString, String> {

    private int max;

    @Override
    public void initialize(MoneyString ann) {
        this.max = ann.max();
    }

    @Override
    public boolean isValid(String moneyString, ConstraintValidatorContext context) {
        if (moneyString == null) {
            return true;
        }

        if (StringUtils.startsWith(moneyString, ".") || StringUtils.endsWith(moneyString, ".")) {
            return false;
        }

        try {
            Money money = MoneyUtils.createMoney(moneyString);

            if (String.valueOf(money.longValue()).length() > (max - 2)) {
                return false;
            }
        } catch (Exception e) {
            Log.ERROR_LOGGER.error("金额转换出错:" + moneyString);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        MoneyStringConstraintValidator x = new MoneyStringConstraintValidator();
        System.out.println(x.isValid("200", null));
    }

}
