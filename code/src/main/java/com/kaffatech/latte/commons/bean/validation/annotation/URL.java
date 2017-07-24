package com.kaffatech.latte.commons.bean.validation.annotation;

import com.kaffatech.latte.commons.bean.validation.validator.URLConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA
 * User: Alan
 * Date: 17/7/24
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {URLConstraintValidator.class}
)
public @interface URL {
    String message() default "非法的URL格式";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}