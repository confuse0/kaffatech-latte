
package com.kaffatech.latte.commons.bean.validation.annotation;

import com.kaffatech.latte.commons.bean.validation.validator.DateStringConstraintValidator;

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
		validatedBy = {DateStringConstraintValidator.class}
)
public @interface DateString {

	String message() default "非法的日期类型";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String format() default "yyyyMMddHHmmss";
}
