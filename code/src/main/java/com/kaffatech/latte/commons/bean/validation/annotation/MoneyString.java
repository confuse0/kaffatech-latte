
package com.kaffatech.latte.commons.bean.validation.annotation;

import com.kaffatech.latte.commons.bean.validation.validator.MoneyStringConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhen.ling
 *
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
		validatedBy = {MoneyStringConstraintValidator.class}
)
public @interface MoneyString {

	String message() default "非法的金额格式";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int max() default 13;
}
