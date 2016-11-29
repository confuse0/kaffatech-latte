package com.kaffatech.latte.commons.bean.validation.validator;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.bean.util.TypeBeanUtils;
import com.kaffatech.latte.commons.bean.validation.annotation.TypeBeanCode;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lingzhen on 16/11/10.
 */
public class TypeBeanConstraintValidator implements ConstraintValidator<TypeBeanCode, String> {

    private Class<? extends TypeBean> type;

    @Override
    public void initialize(TypeBeanCode ann) {
        type = ann.type();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(code)) {
            return true;
        }

        if (TypeBeanUtils.getType(type, code) == null) {
            // 找不到对应的CODE
            return false;
        }

        return true;
    }

}
